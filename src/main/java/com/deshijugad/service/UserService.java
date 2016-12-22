package com.deshijugad.service;

import com.deshijugad.model.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public interface UserService {
	User getUser(String email, String password);
	boolean setUser(User user) throws MySQLIntegrityConstraintViolationException;
}