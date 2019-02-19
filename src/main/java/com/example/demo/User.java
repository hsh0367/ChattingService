package com.example.demo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.*;

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

	@Column(name = "User_ip")
	private String ip;

	
	 @OneToMany(cascade = CascadeType.ALL,
	            fetch = FetchType.EAGER,
	            mappedBy = "user")
	private Set<Chat> chats = new HashSet<>();
	  
	public User() {
	}

	public User(String name) {
		this.name = name;
	}
	public User(String name,String ip) {
		this.name = name;
		this.ip = ip;
	}

	public Long getId() {
		return id;
	}
	public String getIp() {
		return ip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Set getChats() {
		return chats;
	}
	public void setChats(Set chats) {
		this.chats = chats;
	}

	@Override
	public String toString() {
		return String.format("User[id='%d', name='%s']", id, name);
	}
	public void viewChats() {
		
		for(Chat chatData : chats) {
			System.out.println(chatData.toString());
		}
	}

}