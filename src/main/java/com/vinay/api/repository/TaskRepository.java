package com.vinay.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinay.api.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
	
	List<Task> findAllByUsersId(long userid);

}
