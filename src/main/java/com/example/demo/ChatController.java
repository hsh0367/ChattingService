package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ChatRepository chatRepository;

	@RequestMapping("/")
	public String index() {

		String userIp = "";// client ip adress
		boolean userCheck = true;
		for (User userDb : userRepository.findAll()) { // user check in User DB
			if (userDb.getName().equals(userIp)) {
				userCheck = false;
				System.out.println("이미 존재하는 사용자입니다.");
				break;
			}
		}
		if (userCheck) { // Data save in to User DB
			User user = new User();
			user.setName(userIp);
			userRepository.save(user);
		}

		return "index";

		/*
		 * ArrayList userList = (ArrayList) userRepository.findAll();
		 * 
		 * if (userList.isEmpty()) System.out.println("없음"); else
		 * System.out.println("있음");
		 * 
		 * Chat b = new Chat(); User a = new User();
		 * 
		 * a.setName("test"); b.setMsgContext("테스트여"); userRepository.save(a);
		 * 
		 * chatRepository.save(b);
		 * 
		 * userList = (ArrayList) userRepository.findAll();
		 * 
		 * for (Chat chat : chatRepository.findAll())
		 * System.out.println(chat.toString());
		 * 
		 * 
		 * 
		 * User update = userRepository.findById((long) 1).get();
		 * update.setName("test2");
		 * 
		 * userRepository.save(update); if (!userList.isEmpty()) { for (User user :
		 * userRepository.findAll()) System.out.println(user.toString());
		 * 
		 * } userRepository.deleteById((long) 1); userList = (ArrayList)
		 * userRepository.findAll();
		 * 
		 * if (userList.isEmpty()) System.out.println("없음"); else
		 * System.out.println("있음");
		 */
	}

	public void printChatLog() {
		ArrayList chatList = (ArrayList) chatRepository.findAll();

		if (!chatList.isEmpty()) {
			for (User user : userRepository.findAll())
				System.out.println(user.toString());
		} else {
			System.out.println("empty chat log");
		}
	}

	@PostMapping("/")
	public String index(@RequestParam(name = "msg", required = false) String msg) {
		ArrayList chatList = (ArrayList) chatRepository.findAll();

		if (!msg.isEmpty()) { // insert chat data to Chat
			Chat chat = new Chat(msg);
			chatRepository.save(chat);
		}

		if (!chatList.isEmpty()) {// print Chat db data
			System.out.println("출력: ");
			for (Chat chat : chatRepository.findAll())
				System.out.println(chat.toString());
		} else {
			System.out.println("empty chat data");
		}

		return "index";
	}

}
