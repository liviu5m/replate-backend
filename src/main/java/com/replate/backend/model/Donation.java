package com.replate.backend.model;

import com.replate.backend.enums.DonationStatus;
import com.replate.backend.enums.QuantityType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double quantity;
    private QuantityType unit;
    private LocalDate expiryDate;
    private String notes;
    private DonationStatus status = DonationStatus.AVAILABLE;
    @ManyToOne
    @JoinColumn(name = "donor_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User donor;
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public Donation(String name, double quantity, QuantityType unit, LocalDate expiryDate, String notes) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
        this.notes = notes;
    }
}
