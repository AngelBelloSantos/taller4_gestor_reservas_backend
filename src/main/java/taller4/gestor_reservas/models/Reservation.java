package taller4.gestor_reservas.models;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class Reservation {
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private User user;
	private List<Resource> resource;
}
