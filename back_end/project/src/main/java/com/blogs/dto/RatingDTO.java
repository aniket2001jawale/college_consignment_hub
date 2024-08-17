package com.blogs.dto;

import java.time.LocalDate;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO 
{
	private Long rating_id;
	private int rating;
	private LocalDate ratingDate;
	private String comment;
	
	private Long buyer_id;
	
	private Long item_id;
	
	

}
