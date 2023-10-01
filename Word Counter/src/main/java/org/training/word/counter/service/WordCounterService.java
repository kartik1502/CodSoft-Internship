package org.training.word.counter.service;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.training.word.counter.dto.AnalysisReport;
import org.training.word.counter.dto.FormData;
import org.training.word.counter.dto.ValidateData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class WordCounterService {

    @Value("${spring.application.file_present}")
    private String filePresent;

    @Value("${spring.application.file_not_found}")
    private String fileNotFound;

    @Value("${spring.application.file_type_not_supported}")
    private String fileNotSupported;

    private static String extractFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public ValidateData verifyFile(FormData formData) throws IOException {

        String printableData = null;
        boolean isFilePresent = true;
        String fileValidation = null;
        boolean isPrintable = false;
        boolean isAnalyzed = false;
        AnalysisReport analysisReport = null;
        List<String> fileTypes = List.of("doc", "docx", "txt");
        if (formData.getMultipartFile() != null && !formData.getMultipartFile().isEmpty()) {

            fileValidation = filePresent;

            String fileType = extractFileType(Objects.requireNonNull(formData.getMultipartFile().getOriginalFilename()));
            if (fileTypes.contains(fileType)){
                isPrintable = true;
                isAnalyzed = true;
                switch (fileType) {
                    case "txt" -> {
                        printableData = readTxtFile(formData.getMultipartFile());
                    }
                    case "doc" -> {
                        printableData = readDocFile(formData.getMultipartFile());
                    }
                    case "docx" -> {
                        printableData = readDocxFile(formData.getMultipartFile());
                    }
                }
                if (printableData != null) {
                    analysisReport = analyze(printableData);
                }
            }
            else {
                fileValidation = filePresent+", "+fileNotSupported;
            }
        }
        else if (formData.getText() != null && !formData.getText().isEmpty()) {
            fileValidation = "Text is provided as input";
            isAnalyzed = true;
            analysisReport = analyze(formData.getText());
        }
        else {
            fileValidation = fileNotFound+" or no input found";
        }
        return ValidateData.builder()
                .isFilePresent(isFilePresent)
                .isPrintable(isPrintable)
                .printableData(printableData)
                .fileValidation(fileValidation)
                .isAnalyzed(isAnalyzed)
                .analysisReport(analysisReport).build();
    }

    private String readTxtFile(MultipartFile multipartFile) throws IOException {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {

            Pattern printablePattern = Pattern.compile("[ -~]+");

            List<String> fileContents = reader.lines()
                    .filter(line -> printablePattern.matcher(line).matches())
                    .toList();

            return String.join("\n", fileContents);
        }
    }

    private String readDocFile(MultipartFile multipartFile) throws IOException {

        try (HWPFDocument document = new HWPFDocument(multipartFile.getInputStream())) {

            WordExtractor extractor = new WordExtractor(document);
            return String.join("\n", extractor.getParagraphText());
        }
    }

    private String readDocxFile(MultipartFile multipartFile) throws IOException {

        try(XWPFDocument document = new XWPFDocument(multipartFile.getInputStream())) {

            List<String> paragraphs = document.getParagraphs().stream()
                    .map(XWPFParagraph::getText)
                    .toList();

            return String.join("\n", paragraphs);
        }
    }

    private AnalysisReport analyze(String printableData) {

        List<String> words = List.of(printableData.split("[\\s,.]+"));
        Map<Character, Long> characterFrequency = printableData.chars()
                .filter(Character::isLetterOrDigit)
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        HashSet<String> uniqueWords = new HashSet<>(words);

        return AnalysisReport.builder()
                .wordsCount(words.size())
                .charactersCount(characterFrequency.values().stream().mapToLong(l -> l).sum())
                .characterFrequency(characterFrequency)
                .uniqueWords(uniqueWords.stream().toList())
                .uniqueWordsCount(uniqueWords.size())
                .build();
    }
}