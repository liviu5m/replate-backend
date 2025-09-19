package com.replate.backend.repository;

import com.replate.backend.model.RequestDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestDonationRepository extends JpaRepository<RequestDonation,Long> {

}
