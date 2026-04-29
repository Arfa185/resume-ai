package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AISuggestionService {

//        public String generateSuggestion(String role, String resumeSkills, String jobSkills) {
//
//            Set<String> resumeSet = new HashSet<>();
//            for(String skill : resumeSkills.toLowerCase().split(",")) {
//                resumeSet.add(skill.trim());
//            }
//
//            Set<String> jobSet = new HashSet<>();
//            for(String skill : jobSkills.toLowerCase().split(",")) {
//                jobSet.add(skill.trim());
//            }
//
//            List<String> missingSkills = new ArrayList<>();
//
//            for(String skill : jobSet) {
//                if(!resumeSet.contains(skill)) {
//                    missingSkills.add(skill);
//                }
//            }
//
//            if(missingSkills.isEmpty()) {
//                return "Great! Your resume matches the job requirements well.";
//            }
//
//            return "You are missing these important skills: " + String.join(", ", missingSkills)
//                    + ". Consider adding them to improve your resume.";
//        }
//public String generateSuggestion(String role, String resumeSkills, String jobSkills) {
//
//    if (resumeSkills == null || jobSkills == null) {
//        return "Please provide both resume skills and job skills.";
//    }
//
//    Set<String> resumeSet = new HashSet<>();
//    for(String skill : resumeSkills.toLowerCase().split(",")) {
//        resumeSet.add(skill.trim());
//    }
//
//    Set<String> jobSet = new HashSet<>();
//    for(String skill : jobSkills.toLowerCase().split(",")) {
//        jobSet.add(skill.trim());
//    }
//
//    List<String> missingSkills = new ArrayList<>();
//
//    for(String skill : jobSet) {
//        if(!resumeSet.contains(skill)) {
//            missingSkills.add(skill);
//        }
//    }
//
//    if(missingSkills.isEmpty()) {
//        return "Great! Your resume matches the job requirements well.";
//    }
//
//    return "You are missing: " + String.join(", ", missingSkills);
//}

    public String generateSuggestion(String role, String resumeSkills, String jobSkills) {

        if (resumeSkills == null || jobSkills == null) {
            return "Please provide both resume skills and job skills.";
        }

        Set<String> resumeSet = new HashSet<>();
        for (String skill : resumeSkills.toLowerCase().split(",")) {
            resumeSet.add(skill.trim());
        }

        Set<String> jobSet = new HashSet<>();
        for (String skill : jobSkills.toLowerCase().split(",")) {
            jobSet.add(skill.trim());
        }

        List<String> missing = new ArrayList<>();

        for (String skill : jobSet) {
            if (!resumeSet.contains(skill)) {
                missing.add(skill);
            }
        }

        if (missing.isEmpty()) {
            return "✅ Excellent! Your profile strongly matches the job requirements.";
        }

        // Categorize (simple logic)
        List<String> core = new ArrayList<>();
        List<String> secondary = new ArrayList<>();

        for (String skill : missing) {
            if (skill.contains("java") || skill.contains("spring") || skill.contains("sql")) {
                core.add(skill);
            } else {
                secondary.add(skill);
            }
        }

        StringBuilder response = new StringBuilder();

        response.append("⚠️ Skill Gap Analysis:\n\n");

        if (!core.isEmpty()) {
            response.append("🔴 High Priority Skills: ")
                    .append(String.join(", ", core))
                    .append("\n\n");
        }

        if (!secondary.isEmpty()) {
            response.append("🟡 Additional Skills: ")
                    .append(String.join(", ", secondary))
                    .append("\n\n");
        }

        response.append("💡 Suggestions:\n");
        response.append("- Add missing skills in your resume\n");
        response.append("- Include projects using these technologies\n");
        response.append("- Mention tools/frameworks clearly in skills section\n");

        return response.toString();
    }
}
