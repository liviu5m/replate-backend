package com.replate.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RequestDonation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Request request;

    @ManyToOne
    @JoinColumn(name = "donation_id", referencedColumnName = "id", nullable = false)
    private Donation donation;
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public RequestDonation(Request request, Donation donation) {
        this.request = request;
        this.donation = donation;
    }
}
