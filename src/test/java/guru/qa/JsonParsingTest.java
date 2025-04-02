package guru.qa;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.model.Company;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonParsingTest {

    @Test
    void parseCompanyJsonTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("company.json")) {
            Company company = mapper.readValue(is, Company.class);

            assertEquals("Tech Innovations", company.companyName());

            List<String> departments = company.departments();
            assertNotNull(departments);
            assertEquals(3, departments.size());
            assertTrue(departments.containsAll(List.of("IT", "HR", "Finance")));

            List<Company.Employee> employees = company.employees();
            assertNotNull(employees);
            assertEquals(2, employees.size());

            Company.Employee firstEmployee = employees.get(0);
            assertEquals(101, firstEmployee.id());
            assertEquals("Иван Иванов", firstEmployee.name());
            assertEquals("Разработчик", firstEmployee.position());
            assertIterableEquals(List.of("Java", "Spring", "SQL"), firstEmployee.skills());
            assertTrue(firstEmployee.active());
            assertEquals("2021-05-15", firstEmployee.hireDate());

            Company.Employee secondEmployee = employees.get(1);
            assertEquals(102, secondEmployee.id());
            assertEquals("Петр Петров", secondEmployee.name());
            assertEquals("Тестировщик", secondEmployee.position());
            assertIterableEquals(List.of("Selenium", "JUnit", "Python"), secondEmployee.skills());
            assertTrue(secondEmployee.active());
            assertEquals("2022-03-10", secondEmployee.hireDate());
        }
    }
}