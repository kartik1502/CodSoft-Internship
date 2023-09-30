package org.training.word.counter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidateData {

    private boolean isFilePresent = false;

    private String fileValidation;

    private boolean isPrintable = true;

    private String printableData;
}
