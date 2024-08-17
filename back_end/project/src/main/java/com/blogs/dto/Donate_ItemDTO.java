package com.blogs.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Donate_ItemDTO 
{
	private Long donate_id;
	private LocalDate donation_date;
	private Long donor_id;
	private Long item_id;
	
	

}
