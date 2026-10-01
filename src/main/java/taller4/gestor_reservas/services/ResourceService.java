package taller4.gestor_reservas.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import taller4.gestor_reservas.models.Resource;
import taller4.gestor_reservas.models.ResourceStatus;
import taller4.gestor_reservas.models.ResourceType;
import taller4.gestor_reservas.repositories.ResourceRepository;
import taller4.gestor_reservas.repositories.ResourceTypeRepository;

@Service
@RequiredArgsConstructor
public class ResourceService {
	private final ResourceRepository resources;
	private final ResourceTypeRepository rsrcTypes;
	
	/*
	 * RESOURCE'S TYPE SERVICES.
	 */
	public List<ResourceType> getAllTypes() {
		return rsrcTypes.findAll();
	}
	public Optional<ResourceType> getTypeById(Long id) {
		return rsrcTypes.findById(id);
	}
	
	public ResourceType newType(ResourceType type) {
		if (rsrcTypes.existsByName(type.getName())) {
			return rsrcTypes.findByName(type.getName()).getFirst();
		}
		ResourceType newType = new ResourceType();
		newType.setName(type.getName());
		return rsrcTypes.save(newType);
	}
	
	public ResourceType updateType(Long id, ResourceType type) {
		if (rsrcTypes.existsById(id) && !rsrcTypes.existsByName(type.getName())) {
			ResourceType typeUpdated = rsrcTypes.findById(id).get();
			typeUpdated.setName(type.getName());
			rsrcTypes.save(typeUpdated);
		}
		return newType(type);
	}
	
	/*
	 * RESOURCE SERVICES.
	 */
	public List<Resource> getAllResources() {
		return resources.findAll();
	}
	public Optional<Resource> getResourceById(Long id) {
		return resources.findById(id);
	}
	public List<Resource> getResourcesByType(Long typeId) {
		if (rsrcTypes.existsById(typeId)) {
			return resources.findByResourceType(rsrcTypes.findById(typeId).get());
		}
		return new LinkedList<Resource>();
	}
	public List<Resource> getResourcesByStatus(ResourceStatus status) {
		return resources.findByStatus(status);
	}
	
	public List<Resource> getResourcesByStatusAndType(ResourceStatus status, Long typeId) {
		if (rsrcTypes.existsById(typeId)) {
			return resources.findByStatusAndResourceType(status, rsrcTypes.findById(typeId).get());
		}
		return new LinkedList<Resource>();
	}
	
	public Resource newResource(Resource resource) {
		Resource newResource = new Resource();
		newResource.setName(resource.getName());
		newResource.setDetail(resource.getDetail());
		newResource.setSharedCapacity(resource.getSharedCapacity());
		newResource.setResourceType(newType(resource.getResourceType()));
		newResource.setStatus(ResourceStatus.OUT_OF_SERVICE);
		return resources.save(newResource);
	}
	
	public Resource upateResource(Long id, Resource resource) {
		if (resources.existsById(id)) {
			Resource resourceUpdated = resources.findById(id).get();
			resourceUpdated.setName(resource.getName());
			resourceUpdated.setDetail(resource.getDetail());
			resourceUpdated.setSharedCapacity(resource.getSharedCapacity());
			resourceUpdated.setResourceType(rsrcTypes.findById(resource
					.getResourceType().getId())
					.orElse(newType(resource.getResourceType())));
			return resources.save(resourceUpdated);
		}
		return newResource(resource);
	}
	public Optional<Resource> updateResourceStatus(Long id, ResourceStatus status) {
		if (resources.existsById(id)) {
			Resource resourceUpdated = resources.findById(id).get();
			resourceUpdated.setStatus(status);
			return Optional.of(resources.save(resourceUpdated));
		}
		return Optional.empty();
	}
	
	public void deleteResource(Long id) {
		resources.deleteById(id);
	}
}
