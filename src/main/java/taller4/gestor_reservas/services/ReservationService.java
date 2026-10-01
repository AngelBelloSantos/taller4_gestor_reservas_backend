package taller4.gestor_reservas.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import taller4.gestor_reservas.models.Reservation;
import taller4.gestor_reservas.repositories.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {
	private final ReservationRepository reservations;
	
	public List<Reservation> getAll() {
		return reservations.findAll();
	}
	public Optional<Reservation> getReservationById(Long id) {
		return reservations.findById(id);
	}
	
	public Reservation newReservation(Reservation reservation) {
		Reservation newReservation = new Reservation();
		newReservation.setReservationDate(LocalDate.now());
		newReservation.setStartDate(reservation.getStartDate());
		newReservation.setEndDate(reservation.getEndDate());
		newReservation.setUser(reservation.getUser());
		// CHEQUEAR DISPONIBILIDAD DE LOS RECURSOS...
		
		return null;
	}
}
