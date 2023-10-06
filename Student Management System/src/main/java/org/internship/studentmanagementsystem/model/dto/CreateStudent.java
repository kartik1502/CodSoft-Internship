package org.internship.studentmanagementsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStudent {

    private String studentName;

    private String emailId;

    private String contactNo;
}
