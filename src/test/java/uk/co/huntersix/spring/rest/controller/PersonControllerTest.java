package uk.co.huntersix.spring.rest.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonDataService personDataService;

    @Test
    public void shouldReturnPersonFromService() throws Exception {
        when(personDataService.findPerson(any(), any())).thenReturn(new Person("Mary", "Smith"));
        this.mockMvc.perform(get("/person/smith/mary"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("firstName").value("Mary"))
            .andExpect(jsonPath("lastName").value("Smith"));
    }
    
    
    @Test
    public void shouldReturnPersonListBySurnameFromService() throws Exception {
         List<Person> personList = Arrays.asList(
                new Person("Mary", "Smith"),
                new Person("Brian", "Archer"),
                new Person("Collin", "Brown")
            );
        
        when(personDataService.findPersonBySurname(any())).thenReturn(personList);
        this.mockMvc.perform(get("/personBySurname/smith/"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$",hasSize(3)))
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].firstName").value("Mary"))
            .andExpect(jsonPath("$[0].lastName").value("Smith"))
            .andExpect(jsonPath("$[1].firstName").value("Brian"))
            .andExpect(jsonPath("$[1].lastName").value("Archer"))
            .andExpect(jsonPath("$[2].firstName").value("Collin"))
            .andExpect(jsonPath("$[2].lastName").value("Brown"));
    }
    
    
    @Test
    public void shouldReturnEmptyListBySurnameFromServiceWhenNotFound() throws Exception {
         List<Person> personList = Arrays.asList(
         
            );
        
        when(personDataService.findPersonBySurname(any())).thenReturn(personList);
        
        this.mockMvc.perform(get("/personBySurname/baskose/"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$",hasSize(0)))
            ;
    }
    
    
    @Test
    public void shouldReturnStatusOkWhenNewPersonIsAdded() throws Exception {
    	Person person = new Person("Cetin", "Baskose");
    	ResponseEntity<Person> responseEntity = new ResponseEntity<Person>(person,HttpStatus.OK);
    	  when(personDataService.addPerson(any(), any())).thenReturn(responseEntity);
          this.mockMvc.perform(post("/personAdd/baskose/cetin"))
              .andDo(print())
              .andExpect(status().isOk())
              .andExpect(jsonPath("id").exists())
              .andExpect(jsonPath("firstName").value("Cetin"))
              .andExpect(jsonPath("lastName").value("Baskose"));
    }
    
    
    @Test
    public void shouldReturnStatusConflictWhenNewPersonAlreadyExists() throws Exception {
    	Person person = new Person("Cetin", "Baskose");
    	ResponseEntity<Person> responseEntity = new ResponseEntity<Person>(person,HttpStatus.CONFLICT);
    	  when(personDataService.addPerson(any(), any())).thenReturn(responseEntity);
          this.mockMvc.perform(post("/personAdd/baskose/cetin"))
              .andDo(print())
              .andExpect(status().isConflict())
              .andExpect(jsonPath("id").exists())
              .andExpect(jsonPath("firstName").value("Cetin"))
              .andExpect(jsonPath("lastName").value("Baskose"));
    }
    
}