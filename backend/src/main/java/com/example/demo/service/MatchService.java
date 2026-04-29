package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
@Service
public class MatchService {
    public int calculateMatch(String resumeSkills, String jobSkills) {

        Set<String> resumeSet = new HashSet<>();
        for(String skill : resumeSkills.toLowerCase().split(",")) {
            resumeSet.add(skill.trim());   // ✅ TRIM FIX
        }

        Set<String> jobSet = new HashSet<>();
        for(String skill : jobSkills.toLowerCase().split(",")) {
            jobSet.add(skill.trim());      // ✅ TRIM FIX
        }

        int matchCount = 0;

        for(String skill : jobSet) {
            if(resumeSet.contains(skill)) {
                matchCount++;
            }
        }

        return (matchCount * 100) / jobSet.size();
    }
}
