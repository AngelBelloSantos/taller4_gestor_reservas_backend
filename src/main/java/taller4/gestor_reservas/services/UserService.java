package taller4.gestor_reservas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import taller4.gestor_reservas.models.User;
import taller4.gestor_reservas.models.UserRole;
import taller4.gestor_reservas.repositories.UserRepository;

@Service
@RequiredArgsConstructor @Slf4j
public class UserService {
	private final UserRepository users;
	
	public List<User> findAll() {
		return users.findAll();
	}
	public Optional<User> findUserById(Long id) {
		return users.findById(id);
	}
	
	public User createUser(User user) {
		log.info("Creando Usuario.");
		User newUser = new User();
		newUser.setName(user.getName());
		newUser.setIdentificationNumber(user.getIdentificationNumber());
		newUser.setPassword(user.getPassword());
		newUser.setRole(UserRole.STANDARD_USER);
		newUser.setEmail(user.getEmail());
		newUser.setPhoneNumber(user.getPhoneNumber());
		newUser.setAddress(user.getAddress());
		return users.save(newUser);
	}
	
	public User updateUser(Long id, User user) {
		log.info("Modificando Usuario.");
		if (users.existsById(id)) {
			User userUpdated = findUserById(id).get();
			userUpdated.setName(user.getName());
			userUpdated.setIdentificationNumber(user.getIdentificationNumber());
			userUpdated.setAddress(user.getAddress());
			userUpdated.setPhoneNumber(user.getPhoneNumber());
			return users.save(userUpdated);
		}
		log.error("El Usuario NO se pudo modificar.");
		return createUser(user);
	}
	public Optional<User> updatePassword(Long id, User user) {
		log.info("Modificando Contraseña de Usuario.");
		if (users.existsById(id)) {
			User userUpdated = findUserById(id).get();
			userUpdated.setPassword(user.getPassword());
			users.save(userUpdated);
		}
		log.error("El Usuario NO existe.");
		return Optional.empty();
	}
	public Optional<User> setUserRole(Long id, User user) {
		log.info("Modificando Rol de Usuario.");
		boolean validRole = false;
		for (UserRole role : UserRole.values()) {
			if (role.compareTo(user.getRole()) == 0) validRole = true;
		}
		if (users.existsById(id) && validRole) {
			User userUpdated = users.findById(id).get();
			userUpdated.setRole(user.getRole());
			return Optional.of(users.save(userUpdated));
		}
		log.error("El Rol NO se pudo modificar.");
		return Optional.empty();
	}
	
	public void deleteUserById(Long id) {
		log.info("Eliminando Usuario.");
		users.deleteById(id);
	}
}
