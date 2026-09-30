package taller4.gestor_reservas.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "users")
@Data @NoArgsConstructor
public class User {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private UserRole role;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	private String name;
	private String identificationNumber;
	private String email;
	private String phoneNumber;
	@Embedded
	private Address address;
}
