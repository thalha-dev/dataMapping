package com.learn.dataMapping.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.dataMapping.entity.Student;
import com.learn.dataMapping.repository.StudentRepository;

@RestController
public class StudentController {

  @Autowired
  private StudentRepository studentRepository;

  @PostMapping("/student/enroll")
  public ResponseEntity<Student> studentEnroll(@RequestBody Student newStudent) {
    Student savedStudent = studentRepository.save(newStudent);
    return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
  }

  @GetMapping("/student")
  public ResponseEntity<?> getAllStudents(
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size,
      @RequestParam(required = false, defaultValue = "studentId") String sortBy) {
    try {
      Pageable paging = PageRequest.of(page, size, Sort.by(sortBy));
      Page<Student> pageStudents = studentRepository.findAll(paging);
      List<Student> students = pageStudents.getContent();

      if (students.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(students, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
