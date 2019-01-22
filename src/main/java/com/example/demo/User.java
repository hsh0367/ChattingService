package com.example.demo;

import java.io.Serializable;
import java.util.ArrayList;
import  javax.persistence.*;

@Entity
@Table(name = "User")
public class User implements Serializable {
	@Id
	@Column(name = "User_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "User_name")
	private String name;

//	@OneToMany(cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY,mappedBy = "User")
	
	private ArrayList<Chat> chats = new ArrayList<Chat>();
	
	
	public User() {
	}

	public User(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public ArrayList getChats() {
		return chats;
	}

	@Override
	public String toString() {
		return String.format("User[id='%d', name='%s']", id, name);
	}
}