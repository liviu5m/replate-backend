package com.replate.backend.repository;

import com.replate.backend.model.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<Verification,Long> {
}
