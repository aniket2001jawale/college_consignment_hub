package com.blogs.entities;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="watchlist")
public class Watchlist 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long watchlist_id;
	
	
	
	 @OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "buyer_id")
	   private Student student;
//	
	 
	 @OneToMany(mappedBy = "watchlist", cascade = { CascadeType.PERSIST, CascadeType.MERGE },orphanRemoval = false)
	   private List<Item> item = new ArrayList<>();
	

	
	


}
