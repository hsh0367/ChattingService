package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChatController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ChatRepository chatRepository;

	private static String getRandomString() {
		StringBuffer buffer = new StringBuffer();
		Random ran = new Random();
		
		String chars[] = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,1,2,3,4,5,6,7,8,9,0".split(",");
		
		
		for(int i=0;i<10;i++) {
			buffer.append(chars[ran.nextInt(chars.length)]);
		}
		
		return buffer.toString();
	}

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
		User user1 = new User();
		User user2 = new User();

		user1.setName(getRandomString());

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

		for (Chat test : chatRepository.findAll()) {

			System.out.println(test.toString());
		}

//		System.out.println("object: "+user.getChats());
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

	@RequestMapping("/chat")
	public @ResponseBody ArrayList<Chat> chat() {

		return (ArrayList<Chat>) chatRepository.findAll();
	}

	@RequestMapping("/chat/{chat_id}")
	public @ResponseBody ArrayList<Chat> chat(@PathVariable Optional<Long> chat_id) {
		if (chat_id.isPresent()) {

			return (ArrayList) chatRepository.findByIdGreaterThan(chat_id.get());
		} else {
			return (ArrayList<Chat>) chatRepository.findAll();
		}
	}
}
