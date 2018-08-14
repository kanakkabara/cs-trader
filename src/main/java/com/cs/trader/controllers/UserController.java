package com.cs.trader.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@GetMapping(value="/hello")
	public String getUserById(@PathVariable int id){
		return "hello world!";
	}
}
