package uk.co.huntersix.spring.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

@RestController
public class PersonController {
    private PersonDataService personDataService;

    public PersonController(@Autowired PersonDataService personDataService) {
        this.personDataService = personDataService;
    }

    @GetMapping("/person/{lastName}/{firstName}")
    public Person person(@PathVariable(value="lastName") String lastName,
                         @PathVariable(value="firstName") String firstName) {
        return personDataService.findPerson(lastName, firstName);
    }
    
    
    @GetMapping("/personBySurname/{lastName}/")
    public List<Person> personBySurname(@PathVariable(value="lastName") String lastName) {
        return personDataService.findPersonBySurname(lastName);
     
    }
    
    @PostMapping("/personAdd/{lastName}/{firstName}")
    public ResponseEntity<Person> personBySurname(@PathVariable(value="lastName") String lastName,
            @PathVariable(value="firstName") String firstName) {
        return personDataService.addPerson(lastName, firstName);
    }
}