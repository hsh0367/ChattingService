package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ChatRepository chatRepository;

	// user proxy check
	public static String getClientIpAddr(HttpServletRequest request) {
		// if user use proxy ip, service user block
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
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

	@RequestMapping("/")
	public String index(HttpServletRequest request) {
		User user = new User();
		user.setName("test");
		
		Chat chat1 = new Chat("sdasd");
		Chat chat2 = new Chat("qweqw");
		chat1.setUser(user);
		chat2.setUser(user);
			
		user.getChats().add(chat1);
		user.getChats().add(chat2);
		
		userRepository.save(user);
		
		
		System.out.println(userRepository.findById((long) 0));
//		
//		String userIp = request.getRemoteAddr();
//		// client ip adress
//		System.out.println("user ip test : " + userIp);
//
//		boolean userCheck = true;// 같은유저일경우 등록하지 않기위하여 사용
//		System.out.println("user List");
//		for (User userPrint : userRepository.findAll()) {
//			System.out.println(userPrint);
//		}
//
//		for (User userDb : userRepository.findAll()) { // user check in User DB
//			if (userDb.getName().equals(userIp)) {
//				userCheck = false;
//				System.out.println("이미 존재하는 사용자입니다.");
//				break;
//			}
//		}
//		if (userCheck) { // Data save in to User DB
//			User user = new User();
//			user.setName(userIp);
//			userRepository.save(user);
//		}
		return "index";
	}

	@PostMapping("/")
	public String index(@PathVariable String userId, @RequestParam(name = "msg", required = false) String msg) {
		ArrayList chatList = (ArrayList) chatRepository.findAll();
		User user = new User();

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
