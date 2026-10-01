package taller4.gestor_reservas.models;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor
public class Reservation {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate reservationDate;
	private LocalDate startDate;
	private LocalDate endDate;
	@ManyToOne
	private User user;
	@ManyToMany
	private List<Resource> resources = new LinkedList<Resource>();
	
	public void addResource(Resource resource) {
		this.resources.add(resource);
	}
}
