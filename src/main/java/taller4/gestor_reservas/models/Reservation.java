package taller4.gestor_reservas.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor
public class Reservation {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private User user;
	@ManyToMany
	private List<Resource> resource;
}
