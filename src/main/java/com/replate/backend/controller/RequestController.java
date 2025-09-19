package com.replate.backend.controller;

import com.replate.backend.dto.RequestDto;
import com.replate.backend.model.Request;
import com.replate.backend.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/request")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public List<Request> getRequests(@RequestParam String sorting, @RequestParam Long ngoId) {
        return requestService.getRequestsByNgoId(sorting, ngoId);
    }

    @GetMapping("/driver/{driverId}")
    public List<Request> getRequestsByDriverId(@PathVariable Long driverId, @RequestParam String sorting) {
        return requestService.getRequestsByDriverId(driverId, sorting);
    }

    @GetMapping("/{id}")
    public Optional<Request> getRequest(@PathVariable Long id) {
        return requestService.getRequest(id);
    }

    @PostMapping
    public Request createRequest(@RequestBody RequestDto requestDto) {
        return requestService.createRequest(requestDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRequest(@PathVariable Long id, @RequestBody RequestDto requestDto) {
        try {
            Request request = requestService.updateRequest(requestDto, id);
            return ResponseEntity.ok(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);
    }
}
