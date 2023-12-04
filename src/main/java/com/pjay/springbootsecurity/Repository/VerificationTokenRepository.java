package com.pjay.springbootsecurity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pjay.springbootsecurity.Model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{
    
}
