package taller4.gestor_reservas.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
	public String streetName;
	public String streetNumber;
}
