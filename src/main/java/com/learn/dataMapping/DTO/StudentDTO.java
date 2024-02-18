package com.learn.dataMapping.DTO;

import com.learn.dataMapping.entity.Guardian;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentDTO {
  private Long studentId;
  private String firstName;
  private String lastName;
  private String emailId;
  private Guardian guardian;
}
