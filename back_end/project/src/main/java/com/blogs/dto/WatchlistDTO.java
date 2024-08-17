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
public class WatchlistDTO 
{
	private Long watchlist_id;
//	private LocalDate dateAdded; 
	private Long buyer_id;
	

}
