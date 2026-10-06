package taller4.gestor_reservas.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name="resources")
@Data @NoArgsConstructor
public class Resource {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String detail;
//	private Integer sharedCapacity;
	@ManyToOne(cascade = CascadeType.ALL)
	private ResourceType category;
//	@ManyToOne
//	private List<Resource> resources = new LinkedList<Resource>();
	@Enumerated(EnumType.STRING)
	private ResourceStatus status;
	
//	public void addResources(Resource resource) {
//		this.resources.add(resource);
//		this.sharedCapacity += resource.getSharedCapacity();
//	}
	
//	protected void updateCapacity() {
//		this.sharedCapacity = 0;
//		if (resources.isEmpty()) {
//			this.sharedCapacity = 1;
//			return;
//		}
//		for (Resource r : resources) {
//			r.updateCapacity();
//			this.sharedCapacity += r.getSharedCapacity();
//		}
//	}
}
