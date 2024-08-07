package com.blogs.entities;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="item")
public class Item 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long item_id;
	
	@NotNull(message="title is not null")
	private String title;
	
	@NotNull(message="description is not null")
	private String description;
	
	@NotNull(message="price can not be null")
	private double price;
	
	// add a property to store the bin contents in DB
	@Lob //large object
	private byte[] image;
	
	//item duration field means the item used till we have to sell it
	@NotNull(message="item duration can not be null")
	private String Item_duration;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message="status field is not null")
	private Status status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	//many item *->1 student
	@JoinColumn(name="seller_id",nullable = false)
	private Student student;
	

	@ManyToOne(fetch = FetchType.LAZY)
	//may item *->1 category
	@JoinColumn(name="category_id",nullable = false)
	private Category category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	//many item *-> 1 cart
	@JoinColumn(name="cart_id",nullable = false)
	private Cart cart;
	
	 @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
	 private List<Donate_Item> donate_Item = new ArrayList<>();
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "watchlist_id")
	    private Watchlist watchlist;
	 
	 @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Rating> ratings = new ArrayList<>();
	

}
