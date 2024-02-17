package com.learn.dataMapping.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
}
