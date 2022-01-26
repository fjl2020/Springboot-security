package com.floapp.security.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/stundents")
public class StudentController {

    private static final List<Student> STUDENTS= Arrays.asList(
        new Student(1,"Fernando Lopez"),
        new Student(2,"Adriana Montoya"),
        new Student(3,"Juan Lopez Montoya"),
        new Student(4,"Victoria Lopez Montoya")

    );
    @GetMapping(path = "{studentId}")
    public Student getStudent(@PathVariable ("studentId") Integer studentId){
        return STUDENTS.stream().filter(s->studentId.equals(s.getStudentId()))
                .findFirst()
                .orElseThrow(()->new IllegalStateException("Student "+studentId+" doesn't exist"));
    }
}
