package com.vivid.web.rest;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {

	@GetMapping(path = { "/", "index" })
	public String indexPage(Model model) {
		return "index";
	}
	
}
