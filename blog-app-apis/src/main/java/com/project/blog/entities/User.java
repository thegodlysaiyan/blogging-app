package com.project.blog.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@NoArgsConstructor
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "user_name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "email", nullable = false, length = 100)
	private String email;
	
	@Column(name = "password", nullable = false, length = 100)
	private String password;
	
	@Column(name = "about", nullable = true, length = 200)
	private String about;
}
