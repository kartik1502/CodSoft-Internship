package org.internship.studentmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.internship.studentmanagementsystem.model.dto.CreateMarks;
import org.internship.studentmanagementsystem.model.dto.CreateStudent;
import org.internship.studentmanagementsystem.model.dto.MarksInfo;
import org.internship.studentmanagementsystem.model.dto.StudentDto;
import org.internship.studentmanagementsystem.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/new")
    public String newStudentForm(Model model) {

        CreateStudent studentDto = new CreateStudent();
        model.addAttribute("student", studentDto);
        return "new_student";
    }

    @PostMapping
    public String saveStudent(StudentDto studentDto) {
        studentService.saveStudent(studentDto);
        return "redirect:/students";
    }

    @GetMapping("/update/{rollNumber}")
    public String updateStudentForm(@PathVariable String rollNumber, Model model) {
        model.addAttribute("student", studentService.getStudentByRollNumber(rollNumber));
        return "update_student";
    }

    @PostMapping("/{rollNumber}")
    public String updateStudent(@PathVariable String rollNumber, StudentDto studentDto) {
        studentService.updateStudent(rollNumber, studentDto);
        return "redirect:/students";
    }

    @GetMapping("/{rollNumber}")
    public String deleteStudent(@PathVariable String rollNumber) {
        studentService.deleteStudent(rollNumber);
        return "redirect:/students";
    }

    @GetMapping
    public String searchStudent(@RequestParam(required = false) String rollNumber, Model model) {
        List<StudentDto> students;

        if(rollNumber != null && !rollNumber.isEmpty()) {
            students = studentService.getStudentByStudentRollNo(rollNumber);
        }
        else {
            students = studentService.getAllStudents();
        }

        model.addAttribute("students", students);
        return "students";
    }

    @GetMapping("/marks/{rollNumber}")
    public String getStudentInfo(@PathVariable String rollNumber, Model model) {
        model.addAttribute("studentInfo", studentService.getStudentInfo(rollNumber));
        return "marks_info";
    }

    @GetMapping("/marks/new/{rollNumber}")
    public String showMarksPage(@PathVariable String rollNumber, Model model) {

        CreateMarks createMarks = new CreateMarks();
        for (int i = 0; i < 6; i++){
            createMarks.addMarks(new MarksInfo());
        }
        model.addAttribute("rollNumber", rollNumber);
        model.addAttribute("marks", createMarks);
        return "new_marks";
    }

    @PostMapping("/marks/{rollNumber}")
    public String saveMarks(@PathVariable String rollNumber, @ModelAttribute("marks") CreateMarks createMarks) {

        studentService.saveMarks(rollNumber, createMarks.getMarks());
        return "redirect:/students/marks/{rollNumber}";

    }

}
