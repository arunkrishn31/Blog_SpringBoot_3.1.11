package com.myblogrestapi.Service;

import com.myblogrestapi.Entity.UserInfo;
import com.myblogrestapi.Repository.UserInforepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    @Autowired
    private UserInforepository userInforepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String addUser(UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInforepository.save(userInfo);
        return "User Added Successfully";
    }
}
