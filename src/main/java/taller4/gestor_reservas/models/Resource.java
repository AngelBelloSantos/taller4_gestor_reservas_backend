package taller4.gestor_reservas.models;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class Resource {
	private String name;
	private String detail;
	private Integer sharedCapacity;
	private ResourceType type;
	private List<Resource> resources;
	private ResourceStatus status;
}
