package taller4.gestor_reservas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import taller4.gestor_reservas.models.ResourceType;

@Repository
public interface ResourceTypeRepository extends JpaRepository<ResourceType, Long> {

	public List<ResourceType> findByName(String Name);
}
