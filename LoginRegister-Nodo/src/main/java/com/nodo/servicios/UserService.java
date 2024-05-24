package com.nodo.servicios;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.nodo.modelos.LoginUser;
import com.nodo.modelos.User;
import com.nodo.repositorios.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	
	//Funcion que registra un usuario
	public User register(User nuevoUsuario, BindingResult result) {
		
		if (!nuevoUsuario.getPassword().equals(nuevoUsuario.getConfirm())) {
			result.rejectValue("password", "Matches","Las contraseñas no coinciden");
		}
		
		//Revisar si el correo ya existe
		String nuevoEmail = nuevoUsuario.getEmail();
		if(repository.findByEmail(nuevoEmail).isPresent()) {
			result.rejectValue("email", "unique", "El correo electronico ya exixte");
		}
		
		if(result.hasErrors()) {
			return null;
		}else {
			//Encriptamos la contraseña
			String contraEncriptada = BCrypt.hashpw(nuevoUsuario.getPassword(), BCrypt.gensalt());
			nuevoUsuario.setPassword(contraEncriptada);
			return repository.save(nuevoUsuario);
		}
	}
	
	//funcion inicio de sesion
	public User login(LoginUser nuevoLogin, BindingResult result) {
		
		//buscamos por correo electronico
		Optional<User> posibleUsuario = repository.findByEmail(nuevoLogin.getEmail());
		
		if (!posibleUsuario.isPresent()) {
			result.rejectValue("email", "unique", "Correo No registrado");
			return null;
		}
			User userLogin = posibleUsuario.get(); //Este es el usuario que me regresa mi base de datos
			if (!BCrypt.checkpw(nuevoLogin.getPassword(), userLogin.getPassword())) {
				result.rejectValue("password", "Matches", "Contraseña invalida");
			}
			if(result.hasErrors()) {
				return null;
		}else {
			return userLogin;
		}
		
		
	}
}
