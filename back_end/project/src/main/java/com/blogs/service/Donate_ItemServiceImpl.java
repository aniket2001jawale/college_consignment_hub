package com.blogs.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blogs.custom_exceptions.Donate_ItemNotFoundException;
import com.blogs.custom_exceptions.RatingNotFoundException;
import com.blogs.dto.Donate_ItemDTO;
import com.blogs.dto.RatingDTO;
import com.blogs.entities.Donate_Item;
import com.blogs.entities.Rating;
import com.blogs.repository.Donate_ItemRepository;
import com.blogs.repository.ItemRepository;
import com.blogs.repository.StudentRepository;

@Service
@Transactional
public class Donate_ItemServiceImpl implements Donate_ItemService 
{
	@Autowired
    private Donate_ItemRepository donateItemRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private StudentRepository studentRepository;
	
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addDonation(Donate_ItemDTO donateItemDTO) {
    	
    	if (donateItemDTO.getDonor_id() == null) {
        throw new IllegalArgumentException("Donor ID must not be null");
    }
    if (donateItemDTO.getItem_id() == null) {
        throw new IllegalArgumentException("Item ID must not be null");
    }

    // Map DonateItemDTO to DonateItem entity
    Donate_Item donateItem = modelMapper.map(donateItemDTO, Donate_Item.class);

    // Find Donor by ID and set it in the DonateItem entity
    donateItem.setStudent(studentRepository.findById(donateItemDTO.getDonor_id())
        .orElseThrow(() -> new RuntimeException("Donor not found")));

    // Find Item by ID and set it in the DonateItem entity
    donateItem.setItem(itemRepository.findById(donateItemDTO.getItem_id())
        .orElseThrow(() -> new RuntimeException("Item not found")));

    // Save the DonateItem entity to the repository
    donateItemRepository.save(donateItem);
    }

    @Override
    public Donate_ItemDTO getDonationById(Long id) 
    {
        Donate_Item donateItem = donateItemRepository.findById(id)
                .orElseThrow(() -> new Donate_ItemNotFoundException("Donation with ID " + id + " not found"));
        Donate_ItemDTO donate_ItemDTO= modelMapper.map(donateItem, Donate_ItemDTO.class);
        donate_ItemDTO.setItem_id(donateItem.getItem().getItem_id());
        donate_ItemDTO.setDonor_id(donateItem.getStudent().getStudent_id());
        return donate_ItemDTO;
    }
    

    @Override
    public List<Donate_ItemDTO> getAllDonations() {
        List<Donate_Item> donations = donateItemRepository.findAll();
        return donations.stream()
                .map(donation-> {
                	Donate_ItemDTO donate_itemDTO=modelMapper.map(donation, Donate_ItemDTO.class);
                	donate_itemDTO.setDonor_id(donation.getStudent().getStudent_id());
                	donate_itemDTO.setItem_id(donation.getItem().getItem_id());
                	return donate_itemDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void updateDonation(Long id, Donate_ItemDTO donateItemDTO) {
        Donate_Item existingDonation = donateItemRepository.findById(id)
                .orElseThrow(() -> new Donate_ItemNotFoundException("Donation with ID " + id + " not found"));

        // Update fields
        existingDonation.setDonation_date(donateItemDTO.getDonation_date());
        // You may also want to update the student and item relationships

        donateItemRepository.save(existingDonation);
    }

    @Override
    public void deleteDonation(Long id) {
        if (donateItemRepository.existsById(id)) {
            donateItemRepository.deleteById(id);
        } else {
            throw new Donate_ItemNotFoundException("Donation with ID " + id + " not found");
        }
    }
	

}
