package org.training.word.counter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.training.word.counter.dto.FormData;
import org.training.word.counter.dto.ValidateData;
import org.training.word.counter.service.WordCounterService;

import java.io.IOException;

@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class WordCounterController {

    private final WordCounterService wordCounterService;


    @RequestMapping("/index")
    public String index(Model model) {

        model.addAttribute("isFilePresent", false);
        model.addAttribute("printableData", false);
        model.addAttribute("errorMessage", false);
        return "index";
    }

    @PostMapping("/analyze")
    public String analyze(@ModelAttribute("formData") FormData formData, Model model) throws IOException {

        if (formData.getMultipartFile() != null && !formData.getMultipartFile().isEmpty() &&
                formData.getText() != null && !formData.getText().isEmpty()) {
            model.addAttribute("isFilePresent", false);
            model.addAttribute("isPrintable", false);
            model.addAttribute("errorMessage", "Please provide either a file or text, not both.");
            return "index";
        }
        ValidateData validateData = wordCounterService.verifyFile(formData);
        model.addAttribute("isFilePresent", validateData.isFilePresent());
        model.addAttribute("printableData", validateData.getPrintableData());
        model.addAttribute("fileValidation", validateData.getFileValidation());
        model.addAttribute("isPrintable", validateData.isPrintable());
        return "/index";
    }
}
