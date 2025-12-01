package com.rao.Resume.advice;

import com.rao.Resume.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAdvice {
    private final ResumeService resumeService;

    @Autowired
    public GlobalModelAdvice(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @ModelAttribute("navProfileImage")
    public String navProfileImage() {
        return this.resumeService.getNavImageBase64();
    }
}
