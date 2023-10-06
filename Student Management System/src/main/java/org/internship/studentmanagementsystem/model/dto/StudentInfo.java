package org.internship.studentmanagementsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentInfo {

    private String rollNumber;

    private String studentName;

    private String emailId;

    private List<MarksInfo> marks;

    private double totalMarks;

    private double percentage;

}
