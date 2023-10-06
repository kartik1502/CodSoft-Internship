package org.internship.studentmanagementsystem.service.implementation;

import lombok.RequiredArgsConstructor;
import org.internship.studentmanagementsystem.model.dto.CreateStudent;
import org.internship.studentmanagementsystem.model.dto.MarksInfo;
import org.internship.studentmanagementsystem.model.dto.StudentDto;
import org.internship.studentmanagementsystem.model.dto.StudentInfo;
import org.internship.studentmanagementsystem.model.entity.Marks;
import org.internship.studentmanagementsystem.model.entity.Student;
import org.internship.studentmanagementsystem.model.mapper.MarksMapper;
import org.internship.studentmanagementsystem.model.mapper.StudentMapper;
import org.internship.studentmanagementsystem.repository.MarksRepository;
import org.internship.studentmanagementsystem.repository.StudentRepository;
import org.internship.studentmanagementsystem.service.StudentService;
import org.internship.studentmanagementsystem.utils.RollNumberGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper = new StudentMapper();
    private final MarksMapper marksMapper = new MarksMapper();

    private final MarksRepository marksRepository;

    private final StudentRepository studentRepository;

    private final RollNumberGenerator rollNumberGenerator;


    @Override
    public StudentDto saveStudent(StudentDto studentDto) {

        studentDto.setStudentRollNo(rollNumberGenerator.generateRollNumber(studentDto.getStudentName()));
        return studentMapper
                .convertToDto(studentRepository
                        .save(studentMapper.convertToEntity(studentDto)));
    }

    @Override
    public List<StudentDto> getAllStudents() {

        return studentMapper.convertToDtoList(studentRepository.findAll());
    }

    @Override
    public StudentDto updateStudent(String rollNumber, StudentDto studentDto) {

        Student student = studentRepository.getStudentByStudentRollNo(rollNumber);
        BeanUtils.copyProperties(studentDto, student);
        return studentMapper
                .convertToDto(studentRepository
                        .save(student));
    }

    @Transactional
    @Override
    public void deleteStudent(String rollNumber) {

        marksRepository.deleteAllByStudent(studentRepository.getStudentByStudentRollNo(rollNumber));
        studentRepository.deleteStudentByStudentRollNo(rollNumber);
    }

    @Override
    public List<StudentDto> getStudentByStudentRollNo(String rollNumber) {

        return studentMapper
                .convertToDtoList(studentRepository
                        .getStudentByStudentRollNoStartingWith(rollNumber));
    }

    @Override
    public StudentDto getStudentByRollNumber(String rollNumber) {

        return studentMapper.convertToDto(studentRepository
                .getStudentByStudentRollNo(rollNumber));
    }

    @Override
    public StudentInfo getStudentInfo(String rollNumber) {

        Student student = studentRepository.getStudentByStudentRollNo(rollNumber);
        List<MarksInfo> marksInfos = marksMapper.convertToDtoList(marksRepository.findMarksByStudent(student));

        StudentInfo studentInfo = new StudentInfo();
        BeanUtils.copyProperties(student, studentInfo);
        studentInfo.setRollNumber(student.getStudentRollNo());
        studentInfo.setMarks(marksInfos);
        double totalMarks = marksInfos.stream()
                .mapToDouble(MarksInfo::getMarks)
                .sum();
        double percentage = (totalMarks / (studentInfo.getMarks().size() * 100)) * 100;
        studentInfo.setPercentage(percentage);
        studentInfo.setTotalMarks(totalMarks);
        return studentInfo;
    }

    @Override
    public void saveMarks(String rollNumber, List<MarksInfo> marksInfo) {

        Student student = studentRepository.getStudentByStudentRollNo(rollNumber);
        List<MarksInfo> filterdList = marksInfo.stream()
                .filter(marks -> marks.getSubjectName() != null && !marks.getSubjectName().isEmpty())
                .toList();
        List<Marks> marks = marksMapper.convertToEntityList(filterdList);
        marks.forEach(marks1 -> marks1.setStudent(student));
        System.out.println(marks);
        marksRepository.saveAll(marks);
    }
}
