package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.ERole;
import com.example.demo.common.JwtUtils;
import com.example.demo.entity.Cart;
import com.example.demo.entity.PasswordResetToken;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.Verification;
import com.example.demo.payload.JwtResponse;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.ResetPasswordDto;
import com.example.demo.payload.SignUpDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerificationRepository;
import com.example.demo.repository.PasswordResetTokenRepository;
import com.example.demo.repository.CartRepository;

import net.bytebuddy.utility.RandomString;

import com.example.demo.repository.RoleRepository;

@Service
@Transactional
public class AuthService {
	@Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
    private AuthenticationManager authenticationManager;

	@Autowired
    private UserRepository userRepository;

	@Autowired
    private VerificationRepository verificationRepository;

	@Autowired
    private RoleRepository roleRepository;

	@Autowired
    private CartRepository cartRepository;

	@Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
    private JwtUtils jwtUtils;

	public boolean existsByEmail(String email) {
		if(userRepository.existsByEmail(email)){
	        return true;
	    }
		return false;
	}

	public boolean existsByUsername(String username) {
		if(userRepository.existsByUsername(username)){
	        return true;
	    }
		return false;
	}

	public User getUserByEmail(String email) {
		return userRepository.findByUsernameOrEmail(email, email).get();
	}

	public void setLogoutStatus(Authentication authentication) throws NullPointerException {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		User user = userRepository.findByUsername(userDetails.getUsername()).get();
		user.setIs_active(false);
		userRepository.save(user);
	}

	public JwtResponse loginUser (LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail()).get();

        if(!user.getVerification().isEnabled())
        	return null;

//        ResponseCookie  jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        user.setIs_active(true);
        user.setLast_login(Calendar.getInstance().getTime());

        userRepository.save(user);

        List<String> roles = userDetails.getAuthorities().stream()
        		.map(item -> item.getAuthority())
        		.collect(Collectors.toList());

        String token = jwtUtils.generateToken(user, roles);

        return new JwtResponse(token,
				 userDetails.getId(),
				 userDetails.getUsername(),
				 userDetails.getEmail(),
				 roles);
	}

	public void registerUser (SignUpDto signUpDto, HttpServletRequest req) throws UnsupportedEncodingException, MessagingException {
		User user = new User();
        user.setFirst_name(signUpDto.getFirst_name());
        user.setLast_name(signUpDto.getLast_name());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setPhone_number(signUpDto.getPhone_number());
        user.setBirthday(signUpDto.getBirthday());
        user.setGender_id(signUpDto.getGender_id());
        user.setDate_joined(Calendar.getInstance().getTime());

        Set<String> strRoles = signUpDto.getRoles();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null) {
        	Role userRole = roleRepository.findByName(ERole.ROLE_USER).get();
        	roles.add(userRole);
        } else {
        	strRoles.forEach(role -> {
        		switch (role) {
        		case "admin":
        			Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).get();
        			roles.add(adminRole);
        			break;
        		case "staff":
        			Role staffRole = roleRepository.findByName(ERole.ROLE_STAFF).get();
        			roles.add(staffRole);
        			break;
        		default:
        			Role userRole = roleRepository.findByName(ERole.ROLE_USER).get();
        			roles.add(userRole);
        		}
        	});
        }

        user.setRoles(roles);

        Verification verification = new Verification();
        verification.setEnabled(false);
        verification.setVerificationCode(RandomString.make(64));
        verification.setUser(user);

        user.setVerification(verification);
        verificationRepository.save(verification);

        userRepository.save(user);

        PasswordResetToken passwordResetToken = new PasswordResetToken();
		passwordResetToken.setUser(user);
		user.setPasswordResetToken(passwordResetToken);
		passwordResetTokenRepository.save(passwordResetToken);

        userRepository.save(user);

        sendVerificationEmail(user,req, 0);
	}



	public void sendVerificationEmail(User user, HttpServletRequest req, int type) throws UnsupportedEncodingException, MessagingException {
		String subject;
		String senderName;
		String mailContent;
		String verifyURL;
		if (type == 0) {
			subject = "Xác nhận tài khoản";
			senderName = "Spotlight On Style";
			mailContent = "<p>Xin chào " + user.getLast_name() + " " + user.getFirst_name() + ",</p>";
			mailContent += "<p>Cảm ơn bạn đã đăng ký tài khoản tại Spotlight On Style</p>";
			mailContent += "<p>Vui lòng nhấn vào đường link bên dưới để xác thực tài khoản của bạn:</p>";

			verifyURL = req.getRequestURL().toString() + "/verify?code=" + user.getVerification().getVerificationCode();

			mailContent += "<h3><a href=\"" + verifyURL + "\">XÁC THỰC</a></h3>";

			mailContent += "<p>Trân trọng,<br>Spotlight On Style</p>";
		}
		else {
			subject = "Đặt lại mật khẩu của bạn";
			senderName = "Spotlight On Style";
			mailContent = "<p>Xin chào " + user.getLast_name() + " " + user.getFirst_name() + ",</p>";
			mailContent += "<p>Bạn đã yêu cầu đặt lại mật khẩu cho tài khoản của bạn.</p>";
			mailContent += "<p>Điền mã xác thực bên dưới để đặt lại mật khẩu, mã sẽ hết hiệu lực sau 5 phút:</p>";

			mailContent += "<h3>" + user.getPasswordResetToken().getToken() + "</h3>";

			mailContent += "<p>Vui lòng bỏ qua email này nếu yêu cầu đổi mật khẩu này không phải từ bạn.</p>";

			mailContent += "<p>Trân trọng,<br>Spotlight On Style</p>";
		}

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("vociri24@gmail.com", senderName);
		helper.setTo(user.getEmail());
		helper.setSubject(subject);

		helper.setText(mailContent, true);

		mailSender.send(message);
	}

	public boolean verify(String code) {
		Verification verification = verificationRepository.findByVerificationCode(code).get();
		if (verification == null || verification.isEnabled())
			return false;
		else {
			verification.setEnabled(true);
			verificationRepository.save(verification);
			Cart cart = new Cart();
			cart.setUser(verification.getUser());
			cartRepository.saveAndFlush(cart);
			return true;
		}
	}

	public void updateResetPasswordToken(String email, HttpServletRequest req) throws UnsupportedEncodingException, MessagingException {
		User user = userRepository.findByUsernameOrEmail(email, email).get();
		PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUser(user);

		Random random = new Random();
		String token;
		do {
		token = String.valueOf(random.nextInt(10)) + String.valueOf(random.nextInt(10)) + String.valueOf(random.nextInt(10)) + String.valueOf(random.nextInt(10)) + String.valueOf(random.nextInt(10)) + String.valueOf(random.nextInt(10));
		} while (passwordResetTokenRepository.existsByToken(token));
	    passwordResetToken.setToken(token);
	    Calendar currentTimeNow = Calendar.getInstance();

	    currentTimeNow.add(Calendar.MINUTE, 5);
	    Date fiveMinsFromNow = currentTimeNow.getTime();
	    passwordResetToken.setExpiredDate(fiveMinsFromNow);
		passwordResetTokenRepository.saveAndFlush(passwordResetToken);

		sendVerificationEmail(passwordResetToken.getUser(),req, 1);
	}

	public Boolean validatePasswordResetToken(String token, String email) {
        if (passwordResetTokenRepository.existsByToken(token) && passwordResetTokenRepository.findByToken(token).getUser().getEmail().equals(email)) {
            return true;
        }
        return false;
	}

	public void resetPassword(ResetPasswordDto resetPasswordDto) {
		PasswordResetToken passToken = passwordResetTokenRepository.findByToken(resetPasswordDto.getToken());
		User user = passToken.getUser();
		user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
		userRepository.save(user);

		passToken.setToken(null);
		passwordResetTokenRepository.saveAndFlush(passToken);
	}

	public boolean isExpired (String token) {
		PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);

		Date currentTimeNow = Calendar.getInstance().getTime();
		return currentTimeNow.after(passToken.getExpiredDate()) ? true : false;
	}



}
