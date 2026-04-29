package com.example.demo.models;


import lombok.Data;

@Data
public class ResumeRequest {
    private String name;
    private String skills;
    private String experience;
    private String role;
    private String jobSkills;
}
