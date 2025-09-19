package com.replate.backend.controller;

import com.replate.backend.dto.DonationDto;
import com.replate.backend.enums.DonationStatus;
import com.replate.backend.model.Donation;
import com.replate.backend.service.DonationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/donation")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping
    public List<Donation> getDonations(@RequestParam String sorting, @RequestParam String search) {
        return donationService.findAllDonation(sorting, search);
    }

    @GetMapping("/donor/{donorId}")
    public List<Donation> getDonations(@PathVariable Long donorId, @RequestParam String sorting, @RequestParam String search) {
        return donationService.findAllDonationByDonorId(donorId, sorting, search);
    }

    @GetMapping("/{id}")
    public Optional<Donation> getDonation(@PathVariable Long id) {
        return donationService.findDonationById(id);
    }

    @PostMapping
    public ResponseEntity<?> createDonation(@Valid @RequestBody DonationDto donationDto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            Donation donation = donationService.createDonation(donationDto);
            return ResponseEntity.ok(donation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Donation updateDonation(@RequestBody DonationDto donationDto, @PathVariable Long id) {
        System.out.println(id);
        return donationService.updateDonation(donationDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteDonation(@PathVariable Long id) {
        donationService.deleteDonation(id);
    }

}
