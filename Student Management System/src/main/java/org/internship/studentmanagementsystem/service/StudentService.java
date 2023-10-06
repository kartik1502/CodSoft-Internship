package org.internship.studentmanagementsystem.service;

import org.internship.studentmanagementsystem.model.dto.CreateStudent;
import org.internship.studentmanagementsystem.model.dto.MarksInfo;
import org.internship.studentmanagementsystem.model.dto.StudentDto;
import org.internship.studentmanagementsystem.model.dto.StudentInfo;

import java.util.ArrayList;
import java.util.List;

public interface StudentService {

    StudentDto saveStudent(StudentDto studentDto);

    List<StudentDto> getAllStudents();

    StudentDto getStudentByRollNumber(String rollNumber);

    List<StudentDto> getStudentByStudentRollNo(String rollNumber);

    StudentDto updateStudent(String rollNumber, StudentDto studentDto);

    void deleteStudent(String rollNumber);
    StudentInfo getStudentInfo(String rollNumber);

    void saveMarks(String rollNumber , List<MarksInfo> marksInfo);
}
