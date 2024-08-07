package com.blogs.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="rating")
public class Rating 
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rating_id;
	
	@NotNull(message="rating not be null")
	private int rating;
	
	@NotNull(message="rating_date not be null")
	private LocalDate ratingDate;
	
	@NotNull(message="comment not be null")
	private String comment;
	
	 @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "buyer_id")
	    private Student student;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "item_id")
	    private Item item;

}
