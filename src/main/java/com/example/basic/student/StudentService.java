package com.example.basic.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    private  StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        /*return Arrays.asList(
                new Student(1L, "germany" , "best@germany.com", LocalDate.of(2000, Month.JANUARY, 28) , 20)
        );*/
        return  studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional= studentRepository.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()){
            System.out.println("emailPresent opti");
            throw  new IllegalStateException("Email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw  new IllegalStateException("Student with "+studentId+ " not exists");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(long studentId,
                              String name,
                              String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student :"+studentId+" does not exists"));
        if(name != null && name.length()>0 && !Objects.equals(student.getName(), name)){
            student.setName(name);
        }
        if(email !=null && email.length() >0 && !Objects.equals(student.getEmail(),email)){
            Optional<Student> optionalStudent = studentRepository.findStudentByEmail(email);
            if(optionalStudent.isPresent()){
                throw  new IllegalStateException("Email taken");
            }
            student.setEmail(email);
        }
        studentRepository.save(student);
    }
}
