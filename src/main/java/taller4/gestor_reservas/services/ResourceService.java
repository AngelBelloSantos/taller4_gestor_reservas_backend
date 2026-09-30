package taller4.gestor_reservas.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import taller4.gestor_reservas.models.Resource;
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
	public Optional<ResourceType> getRsrcById(Long id) {
		return rsrcTypes.findById(id);
	}
	public Optional<ResourceType> getRsrcByName(String name) {
		List<ResourceType> types = rsrcTypes.findByName(name);
		return types.isEmpty() ? Optional.empty() : Optional.of(types.getFirst());
	}
	
	public ResourceType newType(ResourceType rsrc) {
		Optional<ResourceType> rsrcType = getRsrcByName(rsrc.getName());
		if (rsrcType.isEmpty()) {
			return rsrcTypes.save(rsrc);
		}
		return rsrcType.get();
	}
	
	public ResourceType updateType(Long id, ResourceType rsrc) {
		if (rsrcTypes.existsById(id) && getRsrcByName(rsrc.getName()).isEmpty()) {
			ResourceType rsrcUpdated = rsrcTypes.findById(id).get();
			rsrcUpdated.setName(rsrc.getName());
			rsrcTypes.save(rsrcUpdated);
		}
		return newType(rsrc);
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
	public List<Resource> getResourceByType(String catName) {
		List<ResourceType> rsrcType = rsrcTypes.findByName(catName);
		if (rsrcType.isEmpty()) {
			return new LinkedList<Resource>();
		}
		return resources.findByResourceType(rsrcType.getFirst());
	}
	
	public Resource newResource(Resource resource) {
		return resources.save(resource);
	}
	
	public Resource upateResource(Long id, Resource resource) {
		if (resources.existsById(id)) {
			Resource resourceUpdated = resources.findById(id).get();
			resourceUpdated.setName(resource.getName());
			resourceUpdated.setDetail(resource.getDetail());
			resourceUpdated.setSharedCapacity(resource.getSharedCapacity());
			resourceUpdated.setResourceType(getRsrcByName(resource
					.getResourceType().getName())
					.orElse(newType(resource.getResourceType())));
		}
		return newResource(resource);
	}
}
