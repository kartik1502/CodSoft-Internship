package org.internship.studentmanagementsystem.repository;

import org.internship.studentmanagementsystem.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    void deleteStudentByStudentRollNo(String rollNumber);

    Student getStudentByStudentRollNo(String rollNumber);

    List<Student> getStudentByStudentRollNoStartingWith(String rollNumber);

    @Query("SELECT count (s) FROM Student s")
    int countAll();
}
