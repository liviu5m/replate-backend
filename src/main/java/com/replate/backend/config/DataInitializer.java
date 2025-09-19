package com.replate.backend.config;


import com.github.javafaker.Faker;
import com.replate.backend.enums.DonationStatus;
import com.replate.backend.enums.QuantityType;
import com.replate.backend.model.Donation;
import com.replate.backend.repository.DonationRepository;
import com.replate.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final Faker faker;

    public DataInitializer(DonationRepository donationRepository, UserRepository userRepository, Faker faker) {
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
        this.faker = faker;
    }

    @Override
    public void run(String... args) throws Exception {

//        QuantityType[] quantityTypes = QuantityType.values();
//        for (int i = 0; i < 50; i++) {
//            Donation donation = new Donation();
//            donation.setName(faker.commerce().productName());
//            donation.setQuantity(faker.number().numberBetween(5, 50));
//            donation.setUnit(quantityTypes[(int)(Math.random()*quantityTypes.length)]);
//            donation.setExpiryDate(faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
//            donation.setStatus(DonationStatus.AVAILABLE);
//            donation.setDonor(userRepository.findById(1L).get());
//            donation.setNotes(faker.commerce().department());
//            donationRepository.save(donation);
//        }
//
//        System.out.println("Generated 50 fake donations");
    }
}