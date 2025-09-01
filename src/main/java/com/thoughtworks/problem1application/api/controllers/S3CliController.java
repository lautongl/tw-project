package com.thoughtworks.problem1application.api.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/s3")
public class S3CliController {

    @PostMapping("/bucket")
    public String createBucketViaCli(@RequestParam String bucketName) {
        try {
            Process process = new ProcessBuilder("./create-s3.sh", bucketName)
                    .inheritIO()
                    .start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return "S3 criado: " + bucketName;
            } else {
                return "Erro ao criar S3 " + bucketName + ".";
            }
        } catch (Exception e) {
            return "Erro ao executar script: " + e.getMessage();
        }
    }
}
