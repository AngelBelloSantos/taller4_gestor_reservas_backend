package taller4.gestor_reservas.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class User {
	private UserRole role;
	private String name;
	private String identificationNumber;
	private String phoneNumber;
	private Address address;
}
