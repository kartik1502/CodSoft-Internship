package org.training.word.counter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnalysisReport {

    private int wordsCount;

    private long charactersCount;

    private int uniqueWordsCount;

    private List<String> uniqueWords;

    private Map<Character, Long> characterFrequency;
}