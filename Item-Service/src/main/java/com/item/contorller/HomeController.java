package com.item.contorller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@Value("${my.greetings}")
	String welcomeTest;
	
	@GetMapping("/")
	public String welcome() {
		return welcomeTest;
	}
}
