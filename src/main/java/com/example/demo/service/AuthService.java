package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
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
import com.example.demo.entity.Role;
import com.example.demo.entity.Token;
import com.example.demo.entity.User;
import com.example.demo.entity.Verification;
import com.example.demo.payload.JwtResponse;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.SignUpDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerificationRepository;

import net.bytebuddy.utility.RandomString;

import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.TokenRepository;

@Service
@Transactional
public class AuthService {
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private VerificationRepository verificationRepository;
	
	@Autowired
    private RoleRepository roleRepository;
	
	@Autowired
    private TokenRepository tokenRepository;
	
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
	
	public void setLogoutStatus(Authentication authentication) throws NullPointerException {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		User user = userRepository.findByUsername(userDetails.getUsername()).get();
		user.setIs_activate(false);
		userRepository.save(user);
	}
	
	public JwtResponse loginUser (LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
		

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if(!userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail()).get().getVerification().isEnabled())
        	return null;
        ResponseCookie  jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        
        Token newToken = new Token();
        newToken.setToken(jwtCookie.getValue().toString());
        newToken.setUser(userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail()).get());
        newToken.setExpired_at(jwtUtils.getExpiredDateFromToken(jwtCookie.getValue().toString()));
        
        tokenRepository.save(newToken);
        
        User user = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail()).get();
        user.setIs_activate(true);
        user.setLast_login(Calendar.getInstance().getTime());
        
        userRepository.save(user);
        
        List<String> roles = userDetails.getAuthorities().stream()
        		.map(item -> item.getAuthority())
        		.collect(Collectors.toList());
        
        return new JwtResponse(jwtCookie.getValue().toString(), 
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
        	Role userRole = roleRepository.findByName(ERole.ROLE_USER)
        			.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        	roles.add(userRole);
        } else {
        	strRoles.forEach(role -> {
        		switch (role) {
        		case "admin":
        			Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
        				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        			roles.add(adminRole);
        			break;
        		case "staff":
        			Role staffRole = roleRepository.findByName(ERole.ROLE_STAFF)
        				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        			roles.add(staffRole);
        			break;
        		default:
        			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
        				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
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
        
        sendVerificationEmail(user,req);
	}

	private void sendVerificationEmail(User user, HttpServletRequest req) throws UnsupportedEncodingException, MessagingException {
		String subject = "Please verify your registration";
		String senderName = "OSWRS Store";
		String mailContent = "<p>Dear " + user.getFirst_name() + " " + user.getLast_name() + ",</p>";
		mailContent += "<p>Please click the link below to verify to your registration:</p>";
		
		String verifyURL = req.getRequestURL().toString() + "/verify?code=" + user.getVerification().getVerificationCode();
		
		mailContent += "<h3><a href=\"" + verifyURL + "\">VERIFY</a></h3>";
		
		mailContent += "<p>PThank you<br>OSWRS Store</p>";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("oswrs.store@gmail.com", senderName);
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
			return true;
		}
	}
}
