package com.replate.backend.repository;

import com.replate.backend.enums.DonationStatus;
import com.replate.backend.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findAllByStatus(DonationStatus status);
    List<Donation> findAllByDonorId(Long donorId);
    List<Donation> findAllByNameContainingIgnoreCase(String name);
    List<Donation> findAllByStatusAndNameContainingIgnoreCase(DonationStatus status, String name);
    List<Donation> findAllByDonorIdAndStatus(Long donorId, DonationStatus status);
    List<Donation> findAllByDonorIdAndNameContainingIgnoreCase(Long donorId, String name);
    List<Donation> findAllByDonorIdAndStatusAndNameContainingIgnoreCase(Long donorId, DonationStatus status, String name);
}
