package amw.workwavex.project;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data   // Lombok annotation to create all the getters, setters, equals, hash, and toString methods, based on the fields
//@Builder // Lombok annotation to create builder API for the class
//@NoArgsConstructor // Lombok annotation to create constructor without parameters
//@AllArgsConstructor // Lombok annotation to create constructor with all of the parameters
//@Entity // JPA annotation to make this object ready for storage in a JPA-based data store
//@Table(name= "_project") // JPA annotation to specify the table name (if not specified, the class name will be used as the table name
//public class Project {
//    @Id
//    @GeneratedValue
//    private Integer id;
//}
