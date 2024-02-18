package com.learn.dataMapping.DTO;

import java.util.List;

import com.learn.dataMapping.entity.CourseMaterial;
import com.learn.dataMapping.entity.Teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourseDTO {
  private Long courseId;
  private String title;
  private Integer credit;
  private CourseMaterial courseMaterial;
  private Teacher teacher;
  private List<StudentWithoutGuardianDTO> students;
}
