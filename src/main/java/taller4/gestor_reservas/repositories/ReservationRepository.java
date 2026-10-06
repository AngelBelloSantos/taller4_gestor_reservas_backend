package taller4.gestor_reservas.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import taller4.gestor_reservas.models.Reservation;
import taller4.gestor_reservas.models.ReservationStatus;
import taller4.gestor_reservas.models.ResourceStatus;
import taller4.gestor_reservas.models.ResourceType;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	public List<Reservation> findByStatus(ResourceStatus status);
	public List<Reservation> findByStatusAndResourceCategory(ReservationStatus status, ResourceType category);
	
	/**
	 * Encuentra todas las reservas con determinado status, de un determinado recurso,
	 *  que estén completa o parcialmente dentro de un rango de tiempo.
	 * @param status
	 * @param resourceId
	 * @param endDate
	 * @param startDate
	 * @return Reservas con las condiciones antes mencionadas.
	 */
	public List<Reservation> findByStatusAndResourceIdAndStartDateLessThanAndEndDateGreaterThan(
			ReservationStatus status, Long resourceId, LocalDateTime endDate, LocalDateTime startDate);
}
