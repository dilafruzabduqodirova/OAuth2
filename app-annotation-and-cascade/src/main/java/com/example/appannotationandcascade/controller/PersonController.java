package com.example.appannotationandcascade.controller;

import com.example.appannotationandcascade.entity.Person;
import com.example.appannotationandcascade.payload.PersonDto;
import com.example.appannotationandcascade.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    PersonRepository personRepository;


    @PostMapping
    public HttpEntity<?> addPerson(@RequestBody PersonDto personDto){
        Person person = new Person();
        person.setFullName(personDto.getFullName());
        personRepository.save(person);
        return ResponseEntity.ok("Saqlandi");
    }
}
