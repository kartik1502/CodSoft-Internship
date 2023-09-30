package org.training.word.counter.service;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.training.word.counter.dto.FormData;
import org.training.word.counter.dto.ValidateData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

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
        String fileValidation = fileNotFound;
        boolean isPrintable = false;
        List<String> fileTypes = List.of("doc", "docx", "txt");
        if (formData.getMultipartFile() != null && !formData.getMultipartFile().isEmpty()) {

            fileValidation = filePresent;

            String fileType = extractFileType(Objects.requireNonNull(formData.getMultipartFile().getOriginalFilename()));
            if (fileTypes.contains(fileType)){
                isPrintable = true;
                switch (fileType) {
                    case "txt" -> printableData = readTxtFile(formData.getMultipartFile());
                    case "doc" -> printableData = readDocFile(formData.getMultipartFile());
                    case "docx" -> printableData = readDocxFile(formData.getMultipartFile());
                    default -> fileValidation = fileNotFound;
                }
            }
            else {
                fileValidation = filePresent+", "+fileNotSupported;
            }
        }
        return ValidateData.builder()
                .isFilePresent(isFilePresent)
                .isPrintable(isPrintable)
                .printableData(printableData)
                .fileValidation(fileValidation).build();
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
}