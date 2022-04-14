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

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> getAllUser() {
        List<UserDto> userList = accountService.findAll();
    	if (!userList.isEmpty())
        	return new ResponseEntity<>(userList, HttpStatus.OK);
        else
            return new ResponseEntity<>("No user found!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        try {
    		UserDto user = accountService.findByUsername(username);
        	return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    	}
    }

    @PutMapping("/change_role/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeRoles(@PathVariable String username, @RequestBody RoleChangeDto roleChangeDto) {
    	try {
    		String result = accountService.changeRole(username, roleChangeDto);
    		return new ResponseEntity<>(result, HttpStatus.OK);
    	}	catch (NullPointerException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.NOT_FOUND);
    	} catch (RuntimeException e2) {
    		return new ResponseEntity<>(e2.getMessage(), HttpStatus.BAD_REQUEST);
    	}
    }

    @DeleteMapping("/delete/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAccount(@PathVariable String username) {
    	try	{
    		return new ResponseEntity<>(accountService.deleteAcc(username), HttpStatus.OK);
    	} catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    	}
    }
}
