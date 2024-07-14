package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Tutorial;
import com.example.repository.Tutorialrepository;

//CRUD operations with Tutorial entity
@RestController
public class TutorialController {

	@Autowired
	private Tutorialrepository tutorialRepository;

	@GetMapping("/tutorials")
	public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
		try {

			List<Tutorial> _tut = new ArrayList<>();

			if (title == null)
				tutorialRepository.findAll().forEach(_tut::add);
			else
				tutorialRepository.findByTitleContaining(title).forEach(_tut::add);
			if (_tut.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(_tut, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> getTutorialsById(@PathVariable("id") long id) {

		Optional<Tutorial> op = tutorialRepository.findById(id);
		if (op.isPresent()) {
			return new ResponseEntity<>(op.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

	}

	@PostMapping("/tutorials")
	public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial t) {

		try {
			Tutorial _t = tutorialRepository.save(new Tutorial(t.getTitle(), t.getDescription(), t.isPublished()));
			return new ResponseEntity<>(_t, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PutMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> updateATutorial(@PathVariable("id") long id, @RequestBody Tutorial t) {
		Optional<Tutorial> _t = tutorialRepository.findById(id);

		if (_t.isPresent()) {
			Tutorial obtained = _t.get();
			obtained.setTitle(t.getTitle());
			obtained.setDescription(t.getDescription());
			obtained.setPublished(t.isPublished());
			return new ResponseEntity<>(tutorialRepository.save(obtained), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> deleteATutorial(@PathVariable("id") long id) {
		try {
			tutorialRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/tutorials")
	public ResponseEntity<Tutorial> deleteAllTutorials() {
		try {
			tutorialRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/tutorials/published")
	public ResponseEntity<List<Tutorial>> getTutorialsByPublished() {

		try {
			List<Tutorial> published = tutorialRepository.findByPublished(true);
			if (published.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(published, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
