package taller4.gestor_reservas.controllers.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import taller4.gestor_reservas.models.Resource;
import taller4.gestor_reservas.models.ResourceStatus;
import taller4.gestor_reservas.models.ResourceType;
import taller4.gestor_reservas.services.ResourceService;

@RestController
@RequestMapping("/api/admin/resources")
@RequiredArgsConstructor
public class AdminResourceController {
	private final ResourceService resources;
	
	/*
	 * RESOURCES API
	 */
	@GetMapping
	public List<Resource> getAll() {
		return resources.getAllResources();
	}
	@GetMapping("/")
	public List<Resource> getResourcesByStatus(@RequestParam ResourceStatus status) {
		return resources.getResourcesByStatus(status);
	}
	@GetMapping("/filter")
	public List<Resource> getResourcesByType(@RequestParam Long typeId) {
		return resources.getResourcesByType(typeId);
	}
	
	@PostMapping
	public Resource postResource(@RequestBody Resource rsrc) {
		return resources.newResource(rsrc);
	}
	
	@PutMapping("/{id}")
	public Resource putResource(@PathVariable Long id, @RequestBody Resource rsrc) {
		return resources.upateResource(id, rsrc);
	}
	@PutMapping("/status/{id}")
	public Optional<Resource> putResourceStatus(@PathVariable Long id, @RequestBody Resource rsrc) {
		return resources.updateResourceStatus(id, rsrc.getStatus());
	}
	
	@DeleteMapping("/{id}")
	public void deleteResource(@PathVariable Long id) {
		resources.deleteResource(id);
	}
	
	/*
	 * RESOURCE'S TYPES API
	 */
	@GetMapping("/categories")
	public List<ResourceType> getAllResourceTypes() {
		return resources.getAllTypes();
	}
	
	@PutMapping("/categories/{id}")
	public ResourceType putResourceType(@PathVariable Long id, @RequestBody ResourceType rsrcType) {
		return resources.updateType(id, rsrcType);
	}
}
