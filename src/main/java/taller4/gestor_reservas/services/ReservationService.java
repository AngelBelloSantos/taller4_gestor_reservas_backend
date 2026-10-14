package taller4.gestor_reservas.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import taller4.gestor_reservas.models.Reservation;
import taller4.gestor_reservas.models.ReservationStatus;
import taller4.gestor_reservas.models.Resource;
import taller4.gestor_reservas.models.ResourceStatus;
import taller4.gestor_reservas.models.User;
import taller4.gestor_reservas.repositories.ReservationRepository;

@Service
@RequiredArgsConstructor @Slf4j
public class ReservationService {
	private final ReservationRepository reservations;
	private final ResourceService resources;
	private final UserService users;
	
	public List<Reservation> findAll() {
		return reservations.findAll();
	}
	public Optional<Reservation> findReservationById(Long id) {
		return reservations.findById(id);
	}
	
	public Reservation createReservation(Reservation reservation) {
		log.info("Creando Reserva.");
		if (reservation.getStartDate().isBefore(LocalDateTime.now())) {
			log.error("La fecha de Inicio de la Reserva es anterior a la actual");
			return null; // ERROR. La fecha de inicio de la reserva ya pasó... 
		}
		if (
				reservation.getStartDate().isAfter(reservation.getEndDate()) 
				|| reservation.getStartDate().isEqual(reservation.getEndDate())) {
			log.error("La fecha de Inicio de la Reserva es posterior a la de Finalización.");
			return null; // ERROR. La fecha de inicio es posterior al de finalización.
		}
		
		Optional<User> user = users.findUserById(reservation.getUser().getId());
		if (user.isEmpty()) {
			log.error("El Usuario NO existe");
			return null;
		}
		
		Optional<Resource> resource = resources.findResourceById(reservation.getResource().getId());
		if (resource.isEmpty()) {
			log.error("El Recurso que se desea reservar NO existe");
			return null; // ERROR. El recurso que se quiere reservar no existe.
		}
		if (!resource.get().getStatus().equals(ResourceStatus.OPERATIONAL)) {
			log.error("El Recurso NO está disponible");
			return null; // ERROR. El recurso no está Operativo (OUT OF SERVICE | RETIRED)
		}
		
		List<Reservation> reservationList = findReservationsByStatusAndResourceAndTimeRange(
				ReservationStatus.CONFIRMED, 
				resource.get().getId(),
				reservation.getStartDate(),
				reservation.getEndDate());
		
		// Check Resource availability.
		if (!reservationList.isEmpty()) {
			log.error("El Recurso está reservado dentro de ese rango de tiempo");
			return null; // ERROR. El recurso está reservado dentro de ese lapso de tiempo.
		}
		
		Reservation newReservation = new Reservation();
		newReservation.setReservationDate(LocalDateTime.now());
		newReservation.setStartDate(reservation.getStartDate());
		newReservation.setEndDate(reservation.getEndDate());
		newReservation.setUser(user.get());
		newReservation.setResource(resource.get());
//		newReservation.setStatus(ReservationStatus.CONFIRMED);
		
		return reservations.save(newReservation);
	}
	
	public List<Reservation> findReservationsByStatusAndResourceAndTimeRange(
			ReservationStatus status, Long resourceId, LocalDateTime start, LocalDateTime end) {
		return reservations.findByStatusAndResourceIdAndStartDateLessThanAndEndDateGreaterThan(status, resourceId, end, start);
	}
	
	private boolean updateStatus(Reservation reservation, ReservationStatus status) {
		log.info("Modificando Status de Reserva");
		
		if (reservation.getStatus() == status) {
			return false;
		}
		
		switch (status) {
		case PENDING: {
			if (reservation.getStatus() != ReservationStatus.CANCELLED) {
				return false;
			}
			reservation.setStatus(ReservationStatus.PENDING);
			reservation.setReservationDate(LocalDateTime.now());
			return true;
		}
		case CONFIRMED: {
			if (reservation.getStatus() == ReservationStatus.CANCELLED) {
				return false;
			}
			List<Reservation> reservationList = findReservationsByStatusAndResourceAndTimeRange(
					ReservationStatus.CONFIRMED,
					reservation.getResource().getId(),
					reservation.getStartDate(),
					reservation.getEndDate());
			if (reservationList.isEmpty()) {
				reservation.accept();
				return true;
			}
			return false;
		}
		case CANCELLED: {
			reservation.cancel();
			return true;
		}
		default: {
			log.info("Status NO definido");
			return false;
		}
		}
	}
	
	public Optional<Reservation> updateReservation(Long id, Reservation reservation) {
		log.info("Modificando Reserva");
		
		Optional<Reservation> reservationUpdated = reservations.findById(id);
		if (reservationUpdated.isEmpty()) {
			log.info("La Reserva NO existe");
			return Optional.empty();
		}
		if (updateStatus(reservationUpdated.get(), reservation.getStatus())) {
			return Optional.of(reservations.save(reservationUpdated.get()));
		}
		return Optional.empty();
	}
	
	public void deleteReservation(Long id) {
		log.info("Eliminando Reserva");
		reservations.deleteById(id);
	}
}
