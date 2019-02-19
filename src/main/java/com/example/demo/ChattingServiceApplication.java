package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChattingServiceApplication implements CommandLineRunner{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ChatRepository chatRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChattingServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		User user1 = new User();
		User user2 = new User();

		user1.setName("test");
		user2.setName("test2");

		Chat chat1 = new Chat("sdasd");
		Chat chat2 = new Chat("qweqw");
		chat1.setUser(user1);
		chat2.setUser(user1);

		Chat chat3 = new Chat("xzcq");
		Chat chat4 = new Chat("ceqsc");
		chat3.setUser(user2);
		chat4.setUser(user2);

		user1.getChats().add(chat1);
		user1.getChats().add(chat2);
		user2.getChats().add(chat3);
		user2.getChats().add(chat4);

		userRepository.save(user1);
		userRepository.save(user2);
	
		System.out.println(userRepository.findAll());
		for (Chat test : chatRepository.findAll()) {

			System.out.println(test.toString());
		}
	}

}

