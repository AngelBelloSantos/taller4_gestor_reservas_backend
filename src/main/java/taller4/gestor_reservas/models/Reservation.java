package taller4.gestor_reservas.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor
public class Reservation {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime reservationDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime startDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime endDate;
	@ManyToOne
	private User user;
	@ManyToOne
	private Resource resource;
	@Enumerated(EnumType.STRING)
	private ReservationStatus status = ReservationStatus.PENDING;
	
	public void accept() {
		if (this.status == ReservationStatus.PENDING) {
			this.status = ReservationStatus.CONFIRMED;
		}
	}
	public void cancel() {
		if (this.status == ReservationStatus.PENDING || this.status == ReservationStatus.CONFIRMED) {
			this.status = ReservationStatus.CANCELLED;
		}
	}
}
