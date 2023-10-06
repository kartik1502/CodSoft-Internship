package org.internship.studentmanagementsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link org.internship.studentmanagementsystem.model.entity.Student}
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto  {

    private String studentRollNo;

    private String studentName;

    private String emailId;

    private String contactNo;
}