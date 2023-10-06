package org.internship.studentmanagementsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CreateMarks {

    private List<MarksInfo> marks;

    public CreateMarks() {
        marks = new ArrayList<>();
    }

    public void addMarks(MarksInfo marksInfo) {
        marks.add(marksInfo);
    }
}
