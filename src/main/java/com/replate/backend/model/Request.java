package com.replate.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.replate.backend.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ngo_id", referencedColumnName = "id", nullable = false)
    private User ngo;

    private RequestStatus status;
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "request")
    @JsonManagedReference
    private List<RequestDonation> requestDonations;

    private Date pickupDate;
    private Date deliveryDate;
    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private User driver;


}
