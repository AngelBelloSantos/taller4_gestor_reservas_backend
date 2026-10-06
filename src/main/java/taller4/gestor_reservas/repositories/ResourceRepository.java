package taller4.gestor_reservas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import taller4.gestor_reservas.models.Resource;
import taller4.gestor_reservas.models.ResourceStatus;
import taller4.gestor_reservas.models.ResourceType;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

	public List<Resource> findByCategory(ResourceType category);
	public List<Resource> findByStatus(ResourceStatus status);
	public List<Resource> findByStatusAndCategory(ResourceStatus status, ResourceType category);
}
