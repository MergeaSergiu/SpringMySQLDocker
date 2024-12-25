package org.api.crudspringbootmysqldocker.controller;

import org.api.crudspringbootmysqldocker.entity.Student;
import org.api.crudspringbootmysqldocker.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents(){
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        return new ResponseEntity<>(studentRepository.save(student), HttpStatus.CREATED);
    }

    @DeleteMapping("/students")
    public ResponseEntity<String> deleteStudent(@RequestParam String name){
        studentRepository.deleteByName(name);
        return new ResponseEntity<>("User was deleted", HttpStatus.NO_CONTENT);
    }

}
