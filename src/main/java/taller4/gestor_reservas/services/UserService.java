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
		log.info("CREACION DE USUARIO.");
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
		log.info("MODIFICACION DE USUARIO.");
		if (users.existsById(id)) {
			User userUpdated = findUserById(id).get();
			userUpdated.setName(user.getName());
			userUpdated.setIdentificationNumber(user.getIdentificationNumber());
			userUpdated.setAddress(user.getAddress());
			userUpdated.setPhoneNumber(user.getPhoneNumber());
			return users.save(userUpdated);
		}
		log.error("ERROR. EL ID NO ES VALIDO.");
		return createUser(user);
	}
	public Optional<User> updatePassword(Long id, User user) {
		log.info("MODIFICACION DE CONTRASEÑA.");
		if (users.existsById(id)) {
			User userUpdated = findUserById(id).get();
			userUpdated.setPassword(user.getPassword());
			users.save(userUpdated);
		}
		log.error("EL USUARIO NO EXISTE.");
		return Optional.empty();
	}
	public Optional<User> setUserRole(Long id, User user) {
		log.info("MODIFICACION DE ROL.");
		boolean validRole = false;
		for (UserRole role : UserRole.values()) {
			if (role.compareTo(user.getRole()) == 0) validRole = true;
		}
		if (users.existsById(id) && validRole) {
			User userUpdated = users.findById(id).get();
			userUpdated.setRole(user.getRole());
			return Optional.of(users.save(userUpdated));
		}
		log.error("ERROR. EL USUARIO NO EXISTE O EL NUEVO ROL ES INVALIDO.");
		return Optional.empty();
	}
	
	public void deleteUserById(Long id) {
		log.info("ELIMINACION DE USUARIO.");
		users.deleteById(id);
	}
}
