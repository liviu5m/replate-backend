package com.replate.backend.service;

import com.replate.backend.dto.RequestDto;
import com.replate.backend.enums.DonationStatus;
import com.replate.backend.enums.RequestStatus;
import com.replate.backend.model.Request;
import com.replate.backend.model.User;
import com.replate.backend.repository.RequestRepository;
import com.replate.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    public RequestService(RequestRepository requestRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }

    public Request createRequest(RequestDto requestDto) {
        Request request = new Request();
        User ngo = userRepository.findById(requestDto.getNgoId()).orElseThrow(() -> new RuntimeException("NGO not found"));
        request.setNgo(ngo);
        request.setStatus(requestDto.getStatus());
        return  requestRepository.save(request);
    }

    public Optional<Request> getRequest(Long id) {
        return requestRepository.findById(id);
    }

    public List<Request> getRequests(String sorting) {
        if (sorting.equals("all")) {
            return requestRepository.findAll();
        }
        return requestRepository.findAllByStatus(RequestStatus.valueOf(sorting));
    }

    public List<Request> getRequestsByNgoId(String sorting, Long ngoId) {
        if (sorting.equals("all")) {
            return requestRepository.findAllByNgoId(ngoId);
        }
        if(ngoId == -1) return requestRepository.findAllByStatus(RequestStatus.valueOf(sorting));
        return requestRepository.findAllByNgoIdAndStatus(ngoId, RequestStatus.valueOf(sorting));
    }

    public List<Request> getRequestsByDriverId(Long driverId, String sorting) {
        if (sorting.equals("all")) {
            return requestRepository.findAllByDriverId(driverId);
        }
        return requestRepository.findAllByDriverIdAndStatus(driverId,RequestStatus.valueOf(sorting));
    }

    public Request updateRequest(RequestDto requestDto, Long id) {
        try {
            Request request = requestRepository.findById(id).orElseThrow(() -> new RuntimeException("Request not found"));
            User ngo = userRepository.findById(requestDto.getNgoId()).orElseThrow(() -> new RuntimeException("NGO not found"));
            if(requestDto.getDriverId() != null) {
                User driver = userRepository.findById(requestDto.getDriverId()).orElseThrow(() -> new RuntimeException("Driver not found"));
                request.setDriver(driver);
            }
            request.setNgo(ngo);
            request.setStatus(requestDto.getStatus());
            request.setPickupDate(requestDto.getPickupDate());
            request.setDeliveryDate(requestDto.getDeliveryDate());
            return requestRepository.save(request);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }

}
