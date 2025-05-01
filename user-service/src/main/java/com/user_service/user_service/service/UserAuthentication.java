package com.user_service.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user_service.user_service.components.SessionTokenService;
import com.user_service.user_service.entity.Users;
import com.user_service.user_service.interfaces.IUserAuthentication;
import com.user_service.user_service.models.GetSessionToken;
import com.user_service.user_service.models.Response;
import com.user_service.user_service.models.userLoginParams;
import com.user_service.user_service.models.userRegister;
import com.user_service.user_service.repository.UserRepository;

import ch.qos.logback.core.subst.Token;

@Service
public class UserAuthentication implements IUserAuthentication {
    private UserRepository userRepo;
    @Autowired private SessionTokenService _tokenService;
    public UserAuthentication(UserRepository userRepository) {
        this.userRepo = userRepository;
    }

    @Override
    public Response<Integer> registerUser(userRegister userRegister) {
        try {
            Users user = new Users();
            user.setEmail(userRegister.getEmail());
            user.setFirstName(userRegister.getFirstName());
            user.setLastName(userRegister.getLastName());
            user.setPassword(userRegister.getPassword());
            user.setPhone(userRegister.getPhone());
            user.setUsername(userRegister.getUsername());
            userRepo.save(user);
            return Response.success(1, "User Registered Successfully");
        } catch (Exception e) {
            return Response.error("User Registration Failed" + e.getMessage());
        }

    }

    @Override
    public Response<Integer> loginUser(userLoginParams userLogin) {
        try{
            Users user = userRepo.findAll().stream().filter(u -> u.getUsername().equals(userLogin.getUserName())).findFirst().orElse(null);
            if(user == null){
                return Response.error("User Not Found");
            }
            if(user.getPassword().equals(userLogin.getPassword())){
                return Response.success(1, "User Login Successful");
            }
        }catch(Exception e){
            return Response.error("User Login Failed" + e.getMessage());
        }
        return null;
    }
    @Override
    public Response<String> getSessionToken(GetSessionToken getSessionToken) {
        try {
            Users user = userRepo.findAll().stream().filter(u -> u.getEmail().equals(getSessionToken.getUserEmail())).findFirst().orElse(null);
            if (user == null) {
                return Response.error("User Not Found");
            }
            if (user.getPassword().equals(getSessionToken.getUserPassword())) {
                String token = _tokenService.generateSessionToken(user.getEmail());
                return Response.success(token, "Session Token Generated Successfully");
            }
        } catch (Exception e) {
            return Response.error("Session Token Generation Failed" + e.getMessage());
        }
        return null;
    }
}
