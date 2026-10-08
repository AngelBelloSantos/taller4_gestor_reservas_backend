package taller4.gestor_reservas.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import taller4.gestor_reservas.models.Resource;
import taller4.gestor_reservas.models.ResourceStatus;
import taller4.gestor_reservas.models.ResourceType;
import taller4.gestor_reservas.repositories.ResourceRepository;
import taller4.gestor_reservas.repositories.ResourceTypeRepository;

@Service
@RequiredArgsConstructor @Slf4j
public class ResourceService {
	private final ResourceRepository resources;
	private final ResourceTypeRepository rsrcTypes;
	
	/*
	 * RESOURCE'S TYPE SERVICES.
	 */
	public List<ResourceType> findAllTypes() {
		return rsrcTypes.findAll();
	}
	public Optional<ResourceType> findTypeById(Long id) {
		return rsrcTypes.findById(id);
	}
	
	public ResourceType createCategory(ResourceType type) {
		log.info("Creando Categoría.");
		if (rsrcTypes.existsByName(type.getName())) {
			log.info("La Categoría ya existe.");
			return rsrcTypes.findByName(type.getName()).getFirst();
		}
		ResourceType newType = new ResourceType();
		newType.setName(type.getName());
		return rsrcTypes.save(newType);
	}
	
	public ResourceType updateType(Long id, ResourceType type) {
		log.info("Modificando Categoría.");
		if (rsrcTypes.existsById(id) && !rsrcTypes.existsByName(type.getName())) {
			ResourceType typeUpdated = rsrcTypes.findById(id).get();
			typeUpdated.setName(type.getName());
			rsrcTypes.save(typeUpdated);
		}
		log.error("La Categoría NO pudo ser modificada.");
		return createCategory(type);
	}
	
	/*
	 * RESOURCE SERVICES.
	 */
	public List<Resource> findAll() {
		return resources.findAll();
	}
	public Optional<Resource> findResourceById(Long id) {
		return resources.findById(id);
	}
	public List<Resource> findResourcesByType(Long typeId) {
		if (rsrcTypes.existsById(typeId)) {
			return resources.findByCategory(rsrcTypes.findById(typeId).get());
		}
		return new LinkedList<Resource>();
	}
	public List<Resource> findResourcesByStatus(ResourceStatus status) {
		return resources.findByStatus(status);
	}
	
	public List<Resource> findResourcesByStatusAndType(ResourceStatus status, Long typeId) {
		if (rsrcTypes.existsById(typeId)) {
			return resources.findByStatusAndCategory(status, rsrcTypes.findById(typeId).get());
		}
		return new LinkedList<Resource>();
	}
	
	public Resource createResource(Resource resource) {
		log.info("Creando Recurso.");
		Resource newResource = new Resource();
		newResource.setName(resource.getName());
		newResource.setDetail(resource.getDetail());
//		newResource.setSharedCapacity(resource.getSharedCapacity());
		newResource.setCategory(createCategory(resource.getCategory()));
		newResource.setStatus(ResourceStatus.OUT_OF_SERVICE);
		return resources.save(newResource);
	}
	
	public Resource upateResource(Long id, Resource resource) {
		log.info("Modificando Recurso.");
		if (resources.existsById(id)) {
			Resource resourceUpdated = resources.findById(id).get();
			resourceUpdated.setName(resource.getName());
			resourceUpdated.setDetail(resource.getDetail());
//			resourceUpdated.setSharedCapacity(resource.getSharedCapacity());
			resourceUpdated.setCategory(rsrcTypes.findById(resource
					.getCategory().getId())
					.orElse(createCategory(resource.getCategory())));
			return resources.save(resourceUpdated);
		}
		log.error("El Recurso NO se pudo modificar.");
		return createResource(resource);
	}
	public Optional<Resource> updateResourceStatus(Long id, ResourceStatus status) {
		log.info("Modificando Status de Recurso.");
		if (resources.existsById(id)) {
			Resource resourceUpdated = resources.findById(id).get();
			resourceUpdated.setStatus(status);
			return Optional.of(resources.save(resourceUpdated));
		}
		log.error("El Recurso NO pudo ser modificado.");
		return Optional.empty();
	}
	
	public void deleteResource(Long id) {
		log.info("Eliminando Recurso.");
		resources.deleteById(id);
	}
}
