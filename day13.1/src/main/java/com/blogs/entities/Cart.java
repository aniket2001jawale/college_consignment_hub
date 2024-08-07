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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cart")
public class Cart 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cart_id;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> item = new ArrayList<>();
	
	// student 1---->1 cart  -uni dir one to one asso between entities
	@OneToOne(cascade =CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id")
	private Student student;
	
	
	

}
