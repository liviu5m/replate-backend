package com.replate.backend.repository;

import com.replate.backend.enums.DonationStatus;
import com.replate.backend.enums.RequestStatus;
import com.replate.backend.model.Donation;
import com.replate.backend.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request,Long> {
    List<Request> findAllByStatus(RequestStatus status);
    List<Request> findAllByDriverId(Long driverId);
    List<Request> findAllByNgoId(Long ngoId);
    List<Request> findAllByDriverIdAndStatus(Long driverId, RequestStatus sorting);
    List<Request> findAllByNgoIdAndStatus(Long ngoId, RequestStatus sorting);
}
