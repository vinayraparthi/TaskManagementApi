package com.vinay.api.service;

import java.util.List;

import com.vinay.api.payload.TaskDto;

public interface TaskService {
	
	public TaskDto saveTask(long userid, TaskDto taskDto);
	

	public List<TaskDto> getAllTasks(long userid);
	
	public TaskDto getTask(long userid,long taskid);
	
	public void deleteTask(long userid,long taskid);
	
	TaskDto updateTask(long userid, long taskid,TaskDto taskDto);

}
