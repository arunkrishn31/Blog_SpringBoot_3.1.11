package com.myblogrestapi.Repository;

import com.myblogrestapi.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInforepository extends JpaRepository<UserInfo,Integer> {
    Optional<UserInfo> findByName(String username);
}
