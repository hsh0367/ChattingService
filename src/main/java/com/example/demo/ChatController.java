package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("User")

public class ChatController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ChatRepository chatRepository;

	private static String getRandomString() {// create random name 
		StringBuffer buffer = new StringBuffer();
		Random ran = new Random();

		String chars[] = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,1,2,3,4,5,6,7,8,9,0".split(",");

		for (int i = 0; i < 10; i++) {
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
	public String index(Model model, HttpSession session, HttpServletRequest request) {

		String userIp = request.getRemoteAddr();
		// client ip adress
		System.out.println("user ip test : " + userIp);
		User user = userRepository.getByIp(userIp);
		if (user != null) {
			session.setAttribute("CurrentUser", user);
		} else { // Data save in to User DB
			User newUser = new User("unknown" + userIp, userIp);
			userRepository.save(newUser);
			session.setAttribute("CurrentUser", newUser);
		}

		model.addAttribute("Chat", ((User)session.getAttribute("CurrentUser")).getChats());
		//model.addAttribute("Chat", userRepository.getByName("test").getChats());
		model.addAttribute("CurrentUser", session.getAttribute("CurrentUser"));
		model.addAttribute("UserList",userRepository.findAll());
		return "index";
	}

	@PostMapping("/")
	public String index(Model model, HttpSession session, @RequestParam(name = "msg", required = false) String msg) {
		User user = ((User) session.getAttribute("CurrentUser"));
		if (!(msg == null)) {// print Chat db data
			Chat chat = new Chat(msg, user);
			user.getChats().add(chat);
		} else {
			System.out.println("empty chat data");
		}
		userRepository.save(user);
		model.addAttribute("Chat", ((User) session.getAttribute("CurrentUser")).getChats());
		model.addAttribute("CurrentUser", session.getAttribute("CurrentUser"));
		
		return "redirect:/";
	}

//	@RequestMapping("/chat")
//	public @ResponseBody ArrayList<Chat> chat() {
//
//		return (ArrayList<Chat>) chatRepository.findAll();
//	}
//
//	@RequestMapping("/chat/{chat_id}")
//	public @ResponseBody ArrayList<Chat> chat(@PathVariable Optional<Long> chat_id) {
//		if (chat_id.isPresent()) {
//
//			return (ArrayList) chatRepository.findByIdGreaterThan(chat_id.get());
//		} else {
//			return (ArrayList<Chat>) chatRepository.findAll();
//		}
//	}
}
