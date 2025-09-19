package com.replate.backend.service;

import com.replate.backend.dto.VerificationDto;
import com.replate.backend.enums.VerificationIDType;
import com.replate.backend.model.User;
import com.replate.backend.model.Verification;
import com.replate.backend.repository.UserRepository;
import com.replate.backend.repository.VerificationRepository;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;

    public VerificationService(VerificationRepository verificationRepository, UserRepository userRepository) {
        this.verificationRepository = verificationRepository;
        this.userRepository = userRepository;
    }

    public Verification createVerification(VerificationDto verificationDto) {
        Verification verification = new Verification();
        User user = userRepository.findById(verificationDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        verification.setIdNumber(verificationDto.getIdNumber());
        verification.setIdType(verificationDto.getIdType());
        verification.setDocumentUrl(verificationDto.getDocumentUrl());
        verification.setUser(user);
        return verificationRepository.save(verification);
    }
}
