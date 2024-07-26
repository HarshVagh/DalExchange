package com.asdc.dalexchange.repository;

import com.asdc.dalexchange.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByEmailAndCode(String email, String code);
}
