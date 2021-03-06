package com.example.demo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat, Long> {
	List<Chat> findByIdGreaterThan(Long id);
}
