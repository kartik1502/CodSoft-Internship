package org.internship.studentmanagementsystem.utils;

import lombok.RequiredArgsConstructor;
import org.internship.studentmanagementsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RollNumberGenerator {

    private final StudentRepository studentRepository;


    public String generateRollNumber(String name) {

        String prefix = name.substring(0, Math.min(name.length(), 4)).toUpperCase();
        return prefix + String.format("%04d", studentRepository.count() + 1);
    }
}
