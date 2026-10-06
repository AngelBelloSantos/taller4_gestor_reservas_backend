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
		log.info("CREACION DE RESERVA.");
		if (reservation.getStartDate().isBefore(LocalDateTime.now())) {
			log.error("ERROR. LA FECHA DE INICIO YA PASÓ.");
			return null; // ERROR. La fecha de inicio de la reserva ya pasó... 
		}
		if (
				reservation.getStartDate().isAfter(reservation.getEndDate()) 
				|| reservation.getStartDate().isEqual(reservation.getEndDate())) {
			log.error("ERROR. LA FECHA DE INICIO ES POSTERIOR A LA DE FINALIZACION.");
			return null; // ERROR. La fecha de inicio es posterior al de finalización.
		}
		
		Optional<User> user = users.findUserById(reservation.getUser().getId());
		if (user.isEmpty()) {
			log.error("ERROR. EL USUARIO NO EXISTE.");
			return null;
		}
		
		Optional<Resource> resource = resources.findResourceById(reservation.getResource().getId());
		if (resource.isEmpty()) {
			log.error("ERROR. EL RECURSO QUE SE QUIERE RESERVAR NO EXISTE.");
			return null; // ERROR. El recurso que se quiere reservar no existe.
		}
		if (!resource.get().getStatus().equals(ResourceStatus.OPERATIONAL)) {
			log.error("ERROR. EL RECURSO NO ESTA DISPONIBLE.");
			return null; // ERROR. El recurso no está Operativo (OUT OF SERVICE | RETIRED)
		}
		
		List<Reservation> reservationList = reservations.findByStatusAndResourceIdAndStartDateLessThanAndEndDateGreaterThan(
				ReservationStatus.CONFIRMED, 
				resource.get().getId(), 
				reservation.getEndDate(), 
				reservation.getStartDate());
		
		// Check Resource availability.
		if (!reservationList.isEmpty()) {
			log.error("ERROR. EL RECURSO ESTA RESERVADO DENTRO DE ESE LAPSO DE TIEMPO.");
			return null; // ERROR. El recurso está reservado dentro de ese lapso de tiempo.
		}
		
		Reservation newReservation = new Reservation();
		newReservation.setReservationDate(LocalDateTime.now());
		newReservation.setStartDate(reservation.getStartDate());
//		newReservation.setStartDate(LocalDateTime.of(
//				reservation.getStartDate().getYear(),
//				reservation.getStartDate().getMonth(),
//				reservation.getStartDate().getDayOfMonth(),
//				reservation.getStartDate().getHour(), 0));
		newReservation.setEndDate(reservation.getEndDate());
//		newReservation.setEndDate(LocalDateTime.of(
//				reservation.getEndDate().getYear(),
//				reservation.getEndDate().getMonth(),
//				reservation.getEndDate().getDayOfMonth(),
//				reservation.getEndDate().getHour(), 0));
		newReservation.setUser(user.get());
		newReservation.setResource(resource.get());
		newReservation.setStatus(ReservationStatus.CONFIRMED);
		
		return reservations.save(newReservation);
	}
	
	public List<Reservation> findReservationsByStatusAndResourceAndTimeRange(
			ReservationStatus status, Long resourceId, LocalDateTime start, LocalDateTime end) {
		return reservations.findByStatusAndResourceIdAndStartDateLessThanAndEndDateGreaterThan(status, resourceId, end, start);
	}
	
	public Reservation updateStatus(Long id, ReservationStatus status) {
		log.info("MODIFICACION DE STATUS DE RESERVA.");
		Optional<Reservation> reservation = reservations.findById(id);
		if (reservation.isEmpty()) {
			log.error("ERROR. NO EXISTE LA RESERVA.");
			return null;
		}
		reservation.get().setStatus(status);
		return reservations.save(reservation.get());
	}
	
	public void deleteReservation(Long id) {
		log.info("ELIMINACION DE RESERVA.");
		reservations.deleteById(id);
	}
}
