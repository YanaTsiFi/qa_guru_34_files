package guru.qa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record Company(
        @JsonProperty("company") String companyName,
        @JsonProperty("employees") List<Employee> employees,
        @JsonProperty("departments") List<String> departments
) {
    public record Employee(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("position") String position,
            @JsonProperty("skills") List<String> skills,
            @JsonProperty("active") boolean active,
            @JsonProperty("hire_date") String hireDate
    ) {}
}