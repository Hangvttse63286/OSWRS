package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.RoleChangeDto;
import com.example.demo.payload.UserDto;
import com.example.demo.service.AccountService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUser() {
        List<UserDto> userList = accountService.findAll();
    	if (!userList.isEmpty())
        	return new ResponseEntity<>(userList, HttpStatus.OK);
        else
            return new ResponseEntity<>("No user found!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        UserDto user = accountService.findById(id);
    	if (accountService.findById(id) != null)
        	return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>("No user found!", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/change_role/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeRoles(@PathVariable Long id, @RequestBody RoleChangeDto roleChangeDto) {
    	String result = accountService.changeRole(id, roleChangeDto);
    	if (result != null)
    		return new ResponseEntity<>(result, HttpStatus.OK);
    	else
            return new ResponseEntity<>("No user found!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
    	if (accountService.findById(id) != null)
    		return new ResponseEntity<>(accountService.deleteAcc(id), HttpStatus.OK);
    	else
            return new ResponseEntity<>("No user found!", HttpStatus.NOT_FOUND);
    }
}
