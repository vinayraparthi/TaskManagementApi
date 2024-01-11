package com.vinay.api.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinay.api.entity.Task;
import com.vinay.api.entity.Users;
import com.vinay.api.exception.APIException;
import com.vinay.api.exception.TaskNotFound;
import com.vinay.api.exception.UserNotFound;
import com.vinay.api.payload.TaskDto;
import com.vinay.api.repository.TaskRepository;
import com.vinay.api.repository.UserRepository;
import com.vinay.api.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TaskRepository taskRepo;
	
	
	@Override
	public TaskDto saveTask(long userid, TaskDto taskDto) {
		Users user = userRepo.findById(userid).orElseThrow(
				()-> new UserNotFound(String.format("User Id %d not found", userid))
				);
		Task task = modelMapper.map(taskDto, Task.class);
		task.setUsers(user);
		// After Setting the user we are storing the data in db
		Task savedTask =taskRepo.save(task);
		return modelMapper.map(savedTask, TaskDto.class);
	}

	@Override
	public List<TaskDto> getAllTasks(long userid) {
		userRepo.findById(userid).orElseThrow(
				()-> new UserNotFound(String.format("User Id %d not found", userid))
				);
		List<Task> tasks = taskRepo.findAllByUsersId(userid);
		
		return tasks.stream().map(
				task -> modelMapper.map(task, TaskDto.class)
				).collect(Collectors.toList());
		
		
	}

	@Override
	public TaskDto getTask(long userid, long taskid) {
		Users users = userRepo.findById(userid).orElseThrow(
				()-> new UserNotFound(String.format("User Id %d not found", userid))
				);
		Task task = taskRepo.findById(taskid).orElseThrow(
				()-> new TaskNotFound(String.format("Task id %d not found ", taskid))
				);
		if(users.getId() != task.getUsers().getId()) {
			throw new APIException(String.format("Task Id %d id not belongs to user id %d", taskid,userid));
		}
		return modelMapper.map(task, TaskDto.class);
	}

	@Override
	public void deleteTask(long userid, long taskid) {
		Users users = userRepo.findById(userid).orElseThrow(
				()-> new UserNotFound(String.format("User Id %d not found", userid))
				);
		Task task = taskRepo.findById(taskid).orElseThrow(
				()-> new TaskNotFound(String.format("Task id %d not found ", taskid))
				);
		if(users.getId() != task.getUsers().getId()) {
			throw new APIException(String.format("Task Id %d id not belongs to user id %d", taskid,userid));
		}
		
		taskRepo.deleteById(taskid); // delete the task
	}
	
	public TaskDto updateTask(long userid, long taskid,TaskDto taskDto) {
		Users users = userRepo.findById(userid).orElseThrow(
				()-> new UserNotFound(String.format("User Id %d not found", userid))
				);
		Task task = taskRepo.findById(taskid).orElseThrow(
				()-> new TaskNotFound(String.format("Task id %d not found ", taskid))
				);
		if(users.getId() != task.getUsers().getId()) {
			throw new APIException(String.format("Task Id %d id not belongs to user id %d", taskid,userid));
		}
		task.setTaskname(taskDto.getTaskname());
		Task updated = taskRepo.save(task);
		return modelMapper.map(updated, TaskDto.class);
		
	}
}
