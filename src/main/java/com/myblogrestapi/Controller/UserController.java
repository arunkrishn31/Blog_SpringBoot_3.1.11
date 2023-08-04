package com.myblogrestapi.Controller;

import com.myblogrestapi.Entity.AuthRequest;
import com.myblogrestapi.Entity.UserInfo;
import com.myblogrestapi.Entity.UserInfoUserDetails;
import com.myblogrestapi.Repository.UserInforepository;
import com.myblogrestapi.Service.JWTTokenService;
import com.myblogrestapi.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private JWTTokenService jwtTokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/adduser")
    public String adduser(@RequestBody UserInfo userInfo){
       return userInfoService.addUser(userInfo);
    }

    @PostMapping("/authenticate")
    public String getAuthtoken(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            return jwtTokenService.ganerateToken(authRequest.getUsername());
        }
        else {
          throw  new UsernameNotFoundException("invalid user request..!");
        }
    }
}
