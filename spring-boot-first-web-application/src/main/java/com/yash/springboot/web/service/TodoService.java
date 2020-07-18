package com.yash.springboot.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yash.springboot.web.model.Todo;

@Service
public class TodoService {
	private static List<Todo> todos = new ArrayList<Todo>();
	private static int todoCount = 3;

	static {
		todos.add(new Todo(1, "yash", "Learn Spring MVC - Error on Delete", new Date(), false));
		todos.add(new Todo(2, "yash", "Learn Struts", new Date(), false));
		todos.add(new Todo(3, "yash", "Learn Hibernate", new Date(), false));
	}

	public List<Todo> retrieveTodos(String user) {
		List<Todo> filteredTodos = new ArrayList<Todo>();
		for (Todo todo : todos) {
			if (todo.getUser().equalsIgnoreCase(user)) {
				filteredTodos.add(todo);
			}
		}
		return filteredTodos;
	}

	public void addTodo(String name, String desc, Date targetDate, boolean isDone) {
		todos.add(new Todo(++todoCount, name, desc, targetDate, isDone));
	}

	public void deleteTodo(int id) {
		Iterator<Todo> iterator = todos.iterator();
		while (iterator.hasNext()) {
			Todo todo = iterator.next();
			if (todo.getId() == id) {
				iterator.remove();
			}
		}
	}

	public void updateTodo(Todo utodo) {
//	(int id, String desc, Date date, Boolean isDone) {
		Iterator<Todo> iterator = todos.iterator();
		while (iterator.hasNext()) {
			Todo todo = iterator.next();
			if (todo.getId() == utodo.getId()) {
				todo.setDesc(utodo.getDesc());
				todo.setTargetDate(utodo.getTargetDate());
				todo.setDone(utodo.isDone());
			}
			
			System.out.println("from update method" + utodo.getTargetDate() + utodo.isDone());
		}
	}
	
	public Todo retrieveTodo(int id) {
		for (Todo todo : todos) {
			if (todo.getId()== id) {
				return todo;				
			}
		}
		return null;
	}
}