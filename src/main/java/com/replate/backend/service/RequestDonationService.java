package com.replate.backend.service;

import com.replate.backend.dto.RequestDonationDto;
import com.replate.backend.model.Donation;
import com.replate.backend.model.Request;
import com.replate.backend.model.RequestDonation;
import com.replate.backend.repository.DonationRepository;
import com.replate.backend.repository.RequestDonationRepository;
import com.replate.backend.repository.RequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestDonationService {

    private final RequestDonationRepository requestDonationRepository;
    private final RequestRepository requestRepository;
    private final DonationRepository donationRepository;

    public RequestDonationService(RequestDonationRepository requestDonationRepository, RequestRepository requestRepository, DonationRepository donationRepository) {
        this.requestDonationRepository = requestDonationRepository;
        this.requestRepository = requestRepository;
        this.donationRepository = donationRepository;
    }

    public List<RequestDonation> getRequestDonations() {
        return requestDonationRepository.findAll();
    }

    public Optional<RequestDonation> getRequestDonationById(Long id) {
        return requestDonationRepository.findById(id);
    }

    public RequestDonation saveRequestDonation(RequestDonationDto requestDonationDto) {
        RequestDonation requestDonation = new RequestDonation();
        Request request = requestRepository.findById(requestDonationDto.getRequestId()).orElseThrow(() -> new RuntimeException("Request not found"));
        Donation donation = donationRepository.findById(requestDonationDto.getDonationId()).orElseThrow(() -> new RuntimeException("Donation not found"));
        requestDonation.setRequest(request);
        requestDonation.setDonation(donation);
        return requestDonationRepository.save(requestDonation);
    }

    public RequestDonation updateRequestDonation(RequestDonationDto requestDonationDto, Long id) {
        RequestDonation requestDonation = requestDonationRepository.findById(id).orElseThrow(() -> new RuntimeException("Request Donation not found"));
        Request request = requestRepository.findById(requestDonationDto.getRequestId()).orElseThrow(() -> new RuntimeException("Request not found"));
        Donation donation = donationRepository.findById(requestDonationDto.getDonationId()).orElseThrow(() -> new RuntimeException("Donation not found"));
        requestDonation.setRequest(request);
        requestDonation.setDonation(donation);
        return requestDonationRepository.save(requestDonation);
    }



}
