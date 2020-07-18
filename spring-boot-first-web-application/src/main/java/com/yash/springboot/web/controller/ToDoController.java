package com.yash.springboot.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yash.springboot.web.model.Todo;
import com.yash.springboot.web.service.TodoService;

@Controller
public class ToDoController {

	@Autowired
	TodoService todoService;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@RequestMapping(value = "/todolist", method = RequestMethod.GET)
	public String getTodoList(ModelMap model) {
		String name = getLoggedInUserName();
		System.out.println(name);
		model.put("todos", todoService.retrieveTodos(name));
		return "list-todos";

	}

	private String getLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}

		return principal.toString();
	}

	@RequestMapping(value = "/addtodo", method = RequestMethod.GET)
	public String showAddTodoList(ModelMap model) {
		model.addAttribute("todo", new Todo(0, getLoggedInUserName(), "", new Date(), false));
		return "add-todos";

	}

	@RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam int id) {
		if(id == 1)
			throw new RuntimeException("Something went wrong");
		todoService.deleteTodo(id);
		return "redirect:/todolist";

	}

	@RequestMapping(value = "/addtodo", method = RequestMethod.POST)
	public String addTodoList(ModelMap model, @Valid Todo todo, BindingResult error) {
		if (error.hasErrors()) {
			return "add-todos";
		}
		todoService.addTodo(getLoggedInUserName(), todo.getDesc(), todo.getTargetDate(), false);
		return "redirect:/todolist";

	}

	@RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String showUpdateTodoList(ModelMap model, @RequestParam int id) {
		Todo todo = todoService.retrieveTodo(id);
		model.put("todo", todo);
		System.out.println(todo.getTargetDate());
		System.out.println(todo.isDone());
		return "update-todos";

	}

	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
	public String updateTodoList(ModelMap model, @Valid Todo todo, BindingResult err) {

		if (err.hasErrors()) {
			return "update-todos";
		}
		System.out.println("After");
		System.out.println(todo.getId());
		System.out.println(todo.getTargetDate());
		System.out.println(todo.isDone());

		todo.setUser(getLoggedInUserName());
		todoService.updateTodo(todo);

		return "redirect:/todolist";

	}
}
