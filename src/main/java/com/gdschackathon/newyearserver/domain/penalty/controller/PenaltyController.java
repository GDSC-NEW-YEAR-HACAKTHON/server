package com.gdschackathon.newyearserver.domain.penalty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PenaltyController {

    @GetMapping("/penalty")
    public String showPenalty(
            Model model,
            @RequestParam("description") String description,
            @RequestParam("image-url") String imageUrl
    ) {

        model.addAttribute("description", description);
        model.addAttribute("imageUrl", imageUrl);

        return "penalty";
    }
}
