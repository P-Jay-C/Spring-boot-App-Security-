package com.pjay.springbootsecurity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pjay.springbootsecurity.Model.PasswordResetToken;

public interface PassswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{

    PasswordResetToken findByToken(String token);
     
}
 