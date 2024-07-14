package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Tutorial;

public interface Tutorialrepository extends JpaRepository<Tutorial, Long> {

	// custom method
	List<Tutorial> findByPublished(boolean published);

	List<Tutorial> findByTitleContaining(String title);

}
