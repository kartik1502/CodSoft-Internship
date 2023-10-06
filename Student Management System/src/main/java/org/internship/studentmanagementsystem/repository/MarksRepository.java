package org.internship.studentmanagementsystem.repository;

import org.internship.studentmanagementsystem.model.entity.Marks;
import org.internship.studentmanagementsystem.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarksRepository extends JpaRepository<Marks, Integer> {

    List<Marks> findMarksByStudent(Student student);

    void deleteAllByStudent(Student student);
}
