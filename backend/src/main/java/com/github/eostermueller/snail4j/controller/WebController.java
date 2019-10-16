/**
 * 
 */
package com.github.eostermueller.snail4j.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author eostermueller
 *
 */
@Controller
@RequestMapping("/home")
public class WebController {
	
	
	@GetMapping
	public String home() {
		return "forward:/index.html";
	}
}
