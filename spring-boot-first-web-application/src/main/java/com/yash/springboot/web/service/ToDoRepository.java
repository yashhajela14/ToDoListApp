package com.yash.springboot.web.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.springboot.web.model.Todo;

public interface ToDoRepository extends JpaRepository<Todo, Integer>{
	
	List<Todo> findByUser(String user);
	
}
