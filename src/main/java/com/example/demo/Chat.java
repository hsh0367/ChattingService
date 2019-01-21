package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Chat")
public class Chat {//채팅 디비파일
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String msgContext;
	private Long userId;
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
	
	@Override
	public String toString(){
		return String.format("Chat[id='%d', msg='%s']", id,msgContext);
	}
		
}
