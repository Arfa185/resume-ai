package com.example.demo.controller;


import com.example.demo.models.ResumeRequest;
import com.example.demo.service.AISuggestionService;
import com.example.demo.service.MatchService;
import com.example.demo.service.PdfService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ResumeController {

    private final AISuggestionService aiService;
    private final MatchService matchService;
    private final PdfService pdfService;

    public ResumeController(AISuggestionService aiService, MatchService matchService, PdfService pdfService) {
        this.aiService = aiService;
        this.matchService = matchService;
        this.pdfService = pdfService;
    }

    @PostMapping("/suggest")
    public String suggest(@RequestBody ResumeRequest req) {
        return aiService.generateSuggestion(
                req.getRole(),
                req.getSkills(),
                req.getJobSkills()
        );
    }

    @PostMapping("/match")
    public int match(@RequestParam String resumeSkills, @RequestParam String jobSkills) {
        return matchService.calculateMatch(resumeSkills, jobSkills);
    }

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> pdf(@RequestBody String content) {

        byte[] pdf = pdfService.generatePdf(content);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resume.pdf")
                .body(pdf);
    }
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return "File uploaded: " + file.getOriginalFilename();
    }
}
