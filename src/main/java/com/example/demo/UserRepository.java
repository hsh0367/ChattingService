package com.example.demo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	User getById(Long id);
	User getByIp(String ip);
	User getByName(String name);

}
