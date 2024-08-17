package com.blogs.dto;

import com.blogs.entities.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO 
{
	private Long item_id;
	private String title;
	private String description;
	private double price;
	private String image;
	private String item_duration;
	private Status status;
	private Long seller_id;
	private Long cart_id;
	private Long category_id;
	private Long watchlist_id;
}
