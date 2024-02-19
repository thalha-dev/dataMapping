package com.learn.dataMapping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.learn.dataMapping.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

  Page<Course> findByTitleContaining(
      String title,
      Pageable pageable);

  @Modifying
  @Transactional
  @Query(value = "update course set credit = ?1 where course_id = ?2", nativeQuery = true)
  int updateCourseCreditByCourseId(int credit, long courseId);

  Course findById(long courseId);
}
