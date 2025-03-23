package com.user_service.user_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user_service.user_service.interfaces.IUserAuthentication;
import com.user_service.user_service.models.Response;
import com.user_service.user_service.models.userLoginParams;
import com.user_service.user_service.models.userRegister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/userauth")
public class UserAuthenticationController {
    @Autowired
    private IUserAuthentication User;
    @PostMapping("registerUser")
    public ResponseEntity<Response<Integer>> userRegister(@RequestBody userRegister userRegister) {
        return ResponseEntity.ok(User.registerUser(userRegister));
    }
    @PostMapping("loginUser")
    public ResponseEntity<Response<Integer>> userLogin(@RequestBody userLoginParams userLoginParams) {
        return ResponseEntity.ok(User.loginUser(userLoginParams));
    }
    
}
