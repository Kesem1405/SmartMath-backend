package com.ashcollege.controllers;

import com.ashcollege.service.MathExerciseService;
import com.ashcollege.service.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
public class QuestionController {

    private final Persist persist;

    private final MathExerciseService mathExerciseService;
    @Autowired
    public QuestionController(Persist persist, MathExerciseService mathExerciseService) {
        this.persist = persist;
        this.mathExerciseService = mathExerciseService;
    }


}
