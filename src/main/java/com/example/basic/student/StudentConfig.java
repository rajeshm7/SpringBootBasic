package com.example.basic.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;

import static java.time.Month.JANUARY;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner (StudentRepository studentRepository){
        return args -> {
            Student bruno = new Student("bruno", "bruno@gmail.com", LocalDate.of(1990, JANUARY, 02));
            Student rashford = new Student("rashford", "rashford@gmail.com", LocalDate.of(2000, JANUARY, 03) );
            studentRepository.saveAll(Arrays.asList(bruno, rashford));
        };
    }
}
