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

import com.learn.dataMapping.entity.Teacher;
import com.learn.dataMapping.repository.TeacherRepository;

@RestController
public class TeacherController {

  @Autowired
  private TeacherRepository teacherRepository;

  @PostMapping("/teacher/newTeacher")
  public ResponseEntity<Teacher> newTeacher(@RequestBody Teacher body) {
    Teacher newTeacher = teacherRepository.save(body);
    return new ResponseEntity<>(newTeacher, HttpStatus.CREATED);
  }

  @GetMapping("/teacher")
  public ResponseEntity<?> getAllTeachers(
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size,
      @RequestParam(required = false, defaultValue = "teacherId") String sortBy,
      @RequestParam(required = false, defaultValue = "true") boolean paginate

  ) {
    try {
      if (paginate) {
        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Teacher> pageTeachers = teacherRepository.findAll(paging);
        List<Teacher> teachers = pageTeachers.getContent();

        if (teachers.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(teachers, HttpStatus.OK);

      }

      List<Teacher> teachers = teacherRepository.findAll();

      if (teachers.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(teachers, HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

}
