package com.floapp.security.student;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/stundents")
public class StudentManagementController {
    private static final List<Student> STUDENTS= Arrays.asList(
            new Student(1,"Fernando Lopez"),
            new Student(2,"Adriana Montoya"),
            new Student(3,"Juan Lopez Montoya"),
            new Student(4,"Victoria Lopez Montoya")

    );
    //hasRole("ROLE_") hasAnyRole("ROLE_") hasAuthority("permission") hasAnyAuthority("permission")
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudents(){
        return  STUDENTS;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('student:write')")
    public void  registerNewStudent(@RequestBody  Student student){
        System.out.println(student);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAnyAuthority('student:write')")
    public void  deleteStudent(@PathVariable ("id") Integer id){
        System.out.println(id);
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("hasAnyAuthority('student:write')")
    public void  updateStudent(@PathVariable ("id") Integer id,@RequestBody  Student student){
        System.out.println(String.format("%s %s",id,student.getStudentName()));
    }
}
