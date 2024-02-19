package com.learn.dataMapping.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.dataMapping.DTO.CourseDTO;
import com.learn.dataMapping.entity.Course;
import com.learn.dataMapping.entity.CourseMaterial;
import com.learn.dataMapping.entity.Student;
import com.learn.dataMapping.entity.Teacher;
import com.learn.dataMapping.repository.CourseMaterialRepository;
import com.learn.dataMapping.repository.CourseRepository;
import com.learn.dataMapping.repository.StudentRepository;
import com.learn.dataMapping.repository.TeacherRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private TeacherRepository teacherRepository;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private CourseMaterialRepository courseMaterialRepository;

  @PostMapping("/course/newCourse")
  public ResponseEntity<CourseDTO> createCourse(@RequestBody Course newCourse) {
    try {
      if (newCourse.getCourseMaterial() != null && newCourse.getCourseMaterial().getUrl() != null) {
        handleCourseWithMaterial(newCourse);
      } else {
        handleCourseWithoutMaterial(newCourse);
      }

      Course savedCourse = courseRepository.save(newCourse);
      CourseDTO courseDTO = modelMapper.map(savedCourse, CourseDTO.class);
      return new ResponseEntity<CourseDTO>(courseDTO, HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private void handleCourseWithMaterial(Course newCourse) {
    try {
      handleTeacherAndStudents(newCourse);

      newCourse.getCourseMaterial().setCourse(newCourse);
      CourseMaterial savedCourseMaterial = courseMaterialRepository.save(newCourse.getCourseMaterial());
      newCourse.setCourseMaterial(savedCourseMaterial);
      savedCourseMaterial.setCourse(newCourse);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Error", e);
    }
  }

  private void handleCourseWithoutMaterial(Course newCourse) {
    try {
      handleTeacherAndStudents(newCourse);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Error", e);
    }
  }

  private void handleTeacherAndStudents(Course newCourse) {
    try {
      if (newCourse.getTeacher() != null && newCourse.getTeacher().getTeacherId() != null) {
        Optional<Teacher> teacherOpt = teacherRepository.findById(newCourse.getTeacher().getTeacherId());
        teacherOpt.ifPresent(newCourse::setTeacher);
      }

      if (newCourse.getStudents() != null && !newCourse.getStudents().isEmpty()) {
        List<Student> managedStudents = new ArrayList<>();
        for (Student student : newCourse.getStudents()) {
          Optional<Student> studentOpt = studentRepository.findById(student.getStudentId());
          studentOpt.ifPresent(managedStudents::add);
        }
        newCourse.setStudents(managedStudents);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Error", e);
    }
  }

  @PutMapping("/course/updateCourseCredit")
  public ResponseEntity<CourseDTO> updateCourseCredit(@RequestParam int credit, @RequestParam int courseId) {
    try {

      courseRepository.updateCourseCreditByCourseId(credit, courseId);
      Course savedCourse = courseRepository.findById(courseId);
      CourseDTO courseDTO = modelMapper.map(savedCourse, CourseDTO.class);
      return new ResponseEntity<CourseDTO>(courseDTO, HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
