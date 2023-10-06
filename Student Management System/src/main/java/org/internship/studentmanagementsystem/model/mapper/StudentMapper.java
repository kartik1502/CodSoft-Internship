package org.internship.studentmanagementsystem.model.mapper;

import org.internship.studentmanagementsystem.model.dto.StudentDto;
import org.internship.studentmanagementsystem.model.entity.Student;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

public class StudentMapper extends BaseMapper<Student, StudentDto> {

    @Override
    public StudentDto convertToDto(Student entity, Object... args) {

        StudentDto studentDto = new StudentDto();
        if(!Objects.isNull(entity)){
            BeanUtils.copyProperties(entity, studentDto);
        }
        return studentDto;
    }

    @Override
    public Student convertToEntity(StudentDto dto, Object... args) {

        Student student = new Student();
        if(!Objects.isNull(dto)){
            BeanUtils.copyProperties(dto, student);
        }
        return student;
    }
}
