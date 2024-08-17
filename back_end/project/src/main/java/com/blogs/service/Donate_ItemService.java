package com.blogs.service;

import java.util.List;

import com.blogs.dto.Donate_ItemDTO;

public interface Donate_ItemService 
{
	 // Add a new donation
    void addDonation(Donate_ItemDTO donateItemDTO);

    // Get a donation by ID
    Donate_ItemDTO getDonationById(Long id);

    // Get all donations
    List<Donate_ItemDTO> getAllDonations();

    // Update a donation
    void updateDonation(Long id, Donate_ItemDTO donateItemDTO);

    // Delete a donation by ID
    void deleteDonation(Long id);
}
