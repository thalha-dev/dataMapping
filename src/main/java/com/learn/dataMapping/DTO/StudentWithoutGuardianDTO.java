package com.learn.dataMapping.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentWithoutGuardianDTO {
  private Long studentId;
  private String firstName;
  private String lastName;
  private String emailId;
}
