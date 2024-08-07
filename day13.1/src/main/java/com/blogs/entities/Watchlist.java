package com.blogs.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
@Entity
@Table(name="watchlist")
public class Watchlist 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long watchlist_id;
	
	//The date the item was added to the watchlist.
	@NotNull(message="date not be null")
	private LocalDate dateAdded;
	
	@OneToOne(mappedBy = "watchlist", cascade = CascadeType.ALL, orphanRemoval = true)
	    private Student student;
	
	@OneToMany(mappedBy = "watchlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();


}
