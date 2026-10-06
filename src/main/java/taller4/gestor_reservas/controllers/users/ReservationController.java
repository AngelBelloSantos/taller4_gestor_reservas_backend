package taller4.gestor_reservas.controllers.users;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import taller4.gestor_reservas.models.Reservation;
import taller4.gestor_reservas.models.ReservationStatus;
import taller4.gestor_reservas.services.ReservationService;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationService reservations;
	
	@GetMapping
	public List<Reservation> getAllReservations() {
		return reservations.findAll();
	}
	@GetMapping("/{id}")
	public Optional<Reservation> getReservationById(@PathVariable Long id) {
		return reservations.findReservationById(id);
	}
	@GetMapping("/resource/{id}")
	public List<Reservation> getReservationByResourceId(@PathVariable Long id) {
		return null;
	}
	
	@PostMapping
	public Reservation postReservation(@RequestBody Reservation reservation) {
		return reservations.createReservation(reservation);
	}
	
	@PutMapping("/status/{id}")
	public Reservation putReservationStatus(@PathVariable Long id, @RequestBody ReservationStatus status) {
		return reservations.updateStatus(id, status);
	}
	
	@DeleteMapping("/{id}")
	public void deleteReservation(@PathVariable Long id) {
		reservations.deleteReservation(id);
	}
}
