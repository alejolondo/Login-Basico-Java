package com.nodo.controladores;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nodo.modelos.LoginUser;
import com.nodo.modelos.User;
import com.nodo.servicios.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserService service;
	
	// Ruta para registrar un nuevo usuario
    @PostMapping("/registro")
    public ResponseEntity<?> register(@Valid @RequestBody User nuevoUsuario, BindingResult result) {
        User user = service.register(nuevoUsuario, result);
        if (user == null) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Map<String, String> response = new HashMap<>();
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        //return ResponseEntity.status(HttpStatus.CREATED).body("Usuario Creado Exitosamente");
    }

    // Ruta para iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUser nuevoLogin, BindingResult result) {
        User user = service.login(nuevoLogin, result);
        if (user == null) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        return ResponseEntity.ok(user);
    }

}
