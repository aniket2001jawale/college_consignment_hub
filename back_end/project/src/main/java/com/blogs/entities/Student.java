package com.blogs.entities;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@Table(name="student")
public class Student 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long student_id;
	
	@NotNull(message="student name is not null")
	private String student_name;
	
	@NotNull(message="username is not null")
	private String username;
	
	@Email(message="email should be valid")
	@Column(name="email",unique = true)
	private String email;
	
	@NotBlank(message="password can not blank")
	@Size(min=8,message="password should be at least 8 characters")
	private String password;
	
	@NotNull(message="address is not null")
	private String address;
	
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> item = new ArrayList<>();
	
	 @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	 private List<Donate_Item> donate_Item = new ArrayList<>();
	 
	 @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
	    private Cart cart;
	 //@OneToOne(mappedBy = "student", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	 //private Cart cart;

	 
 
	 @OneToOne(mappedBy = "student", cascade = CascadeType.ALL,orphanRemoval = true)
	    private Watchlist watchlist;
	 
	 @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Rating> ratings = new ArrayList<>();
	
}
