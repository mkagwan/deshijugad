package com.deshijugad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deshijugad.model.Word;
import com.deshijugad.service.WordService;

@Controller
@RequestMapping("/")
public class VController {
	@Autowired
	WordService wordService;

	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String newEmployee(ModelMap model) {
		Word word = new Word();
		model.addAttribute("word", word);
		model.addAttribute("returning", false);
		return "insert";
	}

	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String saveEmployee(Word word, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			return "insert";
		}

		wordService.insertWord(word);
		model.addAttribute("returning", true);
		return "insert";
	}
}
