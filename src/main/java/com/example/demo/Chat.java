package com.example.demo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Chat")
public class Chat implements Serializable {// 채팅 디비파일

	@Id
	@Column(name = "Chat_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Chat_msg")
	private String msgContext;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "User_id")
	private User user;

	
	
	
	public Chat() {
	}

	public Chat(String msg) {
		this.msgContext = msg;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsgContext() {
		return msgContext;
	}

	public void setMsgContext(String msgContext) {
		this.msgContext = msgContext;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return String.format("User[id='%d',name='%s']Chat[id='%d', msg='%s']", user.getId(),user.getName(),id, msgContext);

	}

}
