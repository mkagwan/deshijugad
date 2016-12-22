package com.deshijugad.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deshijugad.model.Category;
import com.deshijugad.model.User;
import com.deshijugad.model.Word;
import com.deshijugad.service.UserService;
import com.deshijugad.service.WordService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping(value = "/apis")
public class RController {

	@Autowired
	UserService userService;

	@Autowired
	WordService wordService;

	public static final String URL = "url";
	public static final String TOKEN = "token";
	public static final String STATUS = "status";
	public static final String MESSAGE = "message";

	//Initializing GSON
	private static Gson gson; static {
		gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@RequestParam(required = true) String email, @RequestParam(required = true) String password) {
		User user = new User();
		JsonObject object = new JsonObject();
		object.addProperty(STATUS, false);
		if (email == null || email.trim().equals("") || password == null || password.trim().equals("")) {			
			object.addProperty(MESSAGE, "Email address or password can not be null or empty");
			return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
		} else {
			user = userService.getUser(email, password);
			if (user != null && user.getUserId() > 0) {
				String token = UUID.randomUUID().toString();
				object = gson.toJsonTree(user).getAsJsonObject();
				JsonObject userJson = (JsonObject) gson.toJsonTree(user);
				userJson.addProperty("token", token);
				return new ResponseEntity<String>(gson.toJson(userJson), HttpStatus.OK);
			} else {
				object.addProperty(MESSAGE, "Either email address or password is incorrect");
				return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);	
			}			
		}
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> list(@RequestParam int userId, @RequestParam int categoryId, 
			@RequestParam int offset, @RequestParam int limit) {
		JsonObject object = new JsonObject();
		object.addProperty(STATUS, false);
		if (categoryId < 0) {
			object.addProperty(MESSAGE, "Category should be more than zero");
		} else if (limit < 0) {
			object.addProperty(MESSAGE, "Limit should be more than zero");
		} else {
			List<Word> words = wordService.getWords(userId, categoryId, offset, limit);
			if (!words.isEmpty()) {
				object.addProperty(STATUS, true);
				object.addProperty(MESSAGE, "Request Granted");
				object.add("words", gson.toJsonTree(words, new TypeToken<List<Word>>() {}.getType()));
			} else {
				object.addProperty(MESSAGE, "No words found for this user or category");
			}
		}
		return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/categories", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> list() {
		JsonObject object = new JsonObject();
		object.addProperty(STATUS, false);
		List<Category> categories = wordService.getCategories();
		if (!categories.isEmpty()) {
			object.addProperty(STATUS, true);
			object.addProperty(MESSAGE, "Request Granted");
			object.add("categories", gson.toJsonTree(categories, new TypeToken<List<Word>>() {}.getType()));
		} else {
			object.addProperty(MESSAGE, "No category available at the moment");
		}
		return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
	}
}