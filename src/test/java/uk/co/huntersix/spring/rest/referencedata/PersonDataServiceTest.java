package uk.co.huntersix.spring.rest.referencedata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.*;

import java.util.List;

import uk.co.huntersix.spring.rest.model.Person;

@RunWith(SpringRunner.class)
public class PersonDataServiceTest {
	
	@InjectMocks
	private PersonDataService personDataService;
	
    @Test
    public void shouldReturnNullWhenPersonNotFound()
    {
    	
    	Person person = personDataService.findPerson("Cetin", "Baskose");
    	
    	
    	assertNull(person);
    }
    
    @Test
    public void shouldReturnPersonWhenFound()
    {
		Person person = personDataService.findPerson("Smith", "Mary");
    	
    	assertTrue("Mary".equals(person.getFirstName()));
     	assertTrue("Smith".equals(person.getLastName()));
    	
    }
    
    
    @Test
    public void shouldReturnEmptyListnWhenNotFoundBySurname()
    {
		List<Person> person = personDataService.findPersonBySurname("Peker");
    	
    	assertEquals(0,person.size() );
    	
    }
    
    
    @Test
    public void shouldReturnPersonListnWhenFoundBySurname()
    {
		List<Person> person = personDataService.findPersonBySurname("Smith");
    	
    	assertEquals(1,person.size() );
    	
    }
    
    @Test
    public void shouldReturnAllPersonListWhenFoundBySurname()
    {
		List<Person> person = personDataService.findPersonBySurname("Brown");
    	
    	assertEquals(2,person.size() );
    	
    }
    
    
    @Test
    public void shouldReturnStatusOkWhenNewPersonIsAdded()
    {
		ResponseEntity<Person> responseEntity =personDataService.addPerson("Baskose", "Cetin");
    	
    	assertEquals(HttpStatus.OK,responseEntity.getStatusCode() );
    	assertEquals("Cetin", responseEntity.getBody().getFirstName());
    	assertEquals("Baskose", responseEntity.getBody().getLastName());
    	
    }
    
    
    @Test
    public void shouldReturnStatusConflictWhenNewPersonAlreadyExists()
    {
		ResponseEntity<Person> responseEntity =personDataService.addPerson("Brown", "Collin");
    	
    	assertEquals(HttpStatus.CONFLICT,responseEntity.getStatusCode() );
    	assertEquals("Collin", responseEntity.getBody().getFirstName());
    	assertEquals("Brown", responseEntity.getBody().getLastName());
    	
    }
    
}
