package com.replate.backend.service;

import com.replate.backend.dto.DonationDto;
import com.replate.backend.enums.DonationStatus;
import com.replate.backend.model.Donation;
import com.replate.backend.model.User;
import com.replate.backend.repository.DonationRepository;
import com.replate.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;

    public DonationService(DonationRepository donationRepository, UserRepository userRepository) {
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
    }

    public List<Donation> findAllDonation(String sorting, String search) {
        if (search == null || search.trim().isEmpty() || search.equals("all")) {
            if (sorting.equals("all")) {
                return donationRepository.findAll();
            }
            return donationRepository.findAllByStatus(DonationStatus.valueOf(sorting));
        } else {
            if (sorting.equals("all")) {
                return donationRepository.findAllByNameContainingIgnoreCase(search.trim());
            }
            return donationRepository.findAllByStatusAndNameContainingIgnoreCase(DonationStatus.valueOf(sorting), search.trim());
        }
    }

    public List<Donation> findAllDonationByDonorId(Long donorId, String sorting, String search) {
        if (search == null || search.trim().isEmpty() || search.equals("all")) {
            if (sorting.equals("all")) {
                return donationRepository.findAllByDonorId(donorId);
            }
            return donationRepository.findAllByDonorIdAndStatus(donorId, DonationStatus.valueOf(sorting));
        } else {
            if (sorting.equals("all")) {
                return donationRepository.findAllByDonorIdAndNameContainingIgnoreCase(donorId, search.trim());
            }
            return donationRepository.findAllByDonorIdAndStatusAndNameContainingIgnoreCase(donorId, DonationStatus.valueOf(sorting), search.trim());
        }
    }

    public Optional<Donation> findDonationById(Long id) {
        return donationRepository.findById(id);
    }

    public Donation createDonation(DonationDto donationDto) {
        try {
            User donor = userRepository.findById(donationDto.getDonorId()).orElseThrow(() -> new RuntimeException("User not found"));
            Donation donation = new Donation(donationDto.getName(), donationDto.getQuantity(), donationDto.getUnit(), donationDto.getExpiryDate(), donationDto.getNotes());
            donation.setDonor(donor);
            return donationRepository.save(donation);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Donation updateDonation(DonationDto donationDto, Long id) {
        Donation donation = donationRepository.findById(id).orElseThrow(() -> new RuntimeException("Donation not found"));
        donation.setName(donationDto.getName());
        donation.setQuantity(donationDto.getQuantity());
        donation.setUnit(donationDto.getUnit());
        donation.setExpiryDate(donationDto.getExpiryDate());
        donation.setNotes(donationDto.getNotes());
        donation.setStatus(donationDto.getStatus());
        return donationRepository.save(donation);
    }

    public void deleteDonation(Long id) {
        donationRepository.deleteById(id);
    }
}
