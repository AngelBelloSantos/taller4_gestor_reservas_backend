package taller4.gestor_reservas.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import taller4.gestor_reservas.models.Resource;
import taller4.gestor_reservas.models.ResourceType;
import taller4.gestor_reservas.services.ResourceService;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class ResourceController {
	private final ResourceService resources;
	
	/*
	 * RESOURCES API
	 */
	@GetMapping
	public List<Resource> getAll() {
		return resources.getAllResources();
	}
	@GetMapping("/by")
	public List<Resource> getAll(@RequestParam String cat) {
		return resources.getResourceByType(cat);
	}
	
	@PostMapping
	public Resource postResource(@RequestBody Resource rsrc) {
		return resources.newResource(rsrc);
	}
	
	/*
	 * RESOURCE'S TYPES API
	 */
	@GetMapping("categories")
	public List<ResourceType> getAllCategories() {
		return resources.getAllTypes();
	}
	
	@PutMapping("/edit")
	public ResourceType putResourceType(@RequestParam Long id, @RequestBody ResourceType rsrcType) {
		return resources.updateType(id, rsrcType);
	}
}
