package org.internship.studentmanagementsystem.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Marks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer marksId;

    private String subjectName;

    private double marks;

    @ManyToOne(cascade = CascadeType.ALL)
    private Student student;
}
