package com.blogs.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="donate_item")
public class Donate_Item 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long donate_id;
	
	@NotNull(message="donation date not be null")
	private LocalDate donation_date;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "donor_id",nullable = false)
	private Student student;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

}
