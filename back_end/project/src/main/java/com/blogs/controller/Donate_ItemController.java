package com.blogs.controller;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogs.dto.ApiResponse;
import com.blogs.dto.Donate_ItemDTO;
import com.blogs.service.Donate_ItemService;
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/donate_item")

public class Donate_ItemController 
{
	@Autowired
    private Donate_ItemService donateItemService;

    // Add a new donation
    @PostMapping
    public ResponseEntity<ApiResponse> addDonation(@RequestBody Donate_ItemDTO donateItemDTO) {
        donateItemService.addDonation(donateItemDTO);
        ApiResponse response = new ApiResponse();
        response.setMessage("Donation added successfully");
        response.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get a donation by ID
    @GetMapping("/{id}")
    public ResponseEntity<Donate_ItemDTO> getDonationById(@PathVariable Long id) {
        Donate_ItemDTO donateItemDTO = donateItemService.getDonationById(id);
        return ResponseEntity.ok(donateItemDTO);
    }

    // Get all donations
    @GetMapping
    public ResponseEntity<List<Donate_ItemDTO>> getAllDonations() {
        List<Donate_ItemDTO> donations = donateItemService.getAllDonations();
        return ResponseEntity.ok(donations);
    }

    // Update an existing donation
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateDonation(@PathVariable Long id, @RequestBody Donate_ItemDTO donateItemDTO) {
        donateItemService.updateDonation(id, donateItemDTO);
        ApiResponse response = new ApiResponse();
        response.setMessage("Donation updated successfully");
        response.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete a donation by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDonation(@PathVariable Long id) {
        donateItemService.deleteDonation(id);
        ApiResponse response = new ApiResponse();
        response.setMessage("Donation deleted successfully");
        response.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
