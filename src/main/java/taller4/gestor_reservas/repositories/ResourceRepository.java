package taller4.gestor_reservas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import taller4.gestor_reservas.models.Resource;
import taller4.gestor_reservas.models.ResourceType;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

	public List<Resource> findByResourceType(ResourceType rsrcType);
}
