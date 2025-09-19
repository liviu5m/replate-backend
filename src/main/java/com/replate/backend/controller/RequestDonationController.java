package com.replate.backend.controller;

import com.replate.backend.dto.RequestDonationDto;
import com.replate.backend.model.RequestDonation;
import com.replate.backend.service.RequestDonationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/request-donation")
public class RequestDonationController {

    private final RequestDonationService requestDonationService;

    public RequestDonationController(RequestDonationService requestDonationService) {
        this.requestDonationService = requestDonationService;
    }

    @GetMapping
    public List<RequestDonation> getRequestDonations() {
        return requestDonationService.getRequestDonations();
    }

    @GetMapping("/{id}")
    public Optional<RequestDonation> getRequestDonationById(@PathVariable Long id) {
        return requestDonationService.getRequestDonationById(id);
    }

    @PostMapping
    public RequestDonation createRequestDonation(@RequestBody RequestDonationDto requestDonationDto) {
        return requestDonationService.saveRequestDonation(requestDonationDto);
    }

    @PutMapping("/{id}")
    public RequestDonation updateRequestDonation(@PathVariable Long id, @RequestBody RequestDonationDto requestDonationDto) {
        return requestDonationService.updateRequestDonation(requestDonationDto, id);
    }

}
