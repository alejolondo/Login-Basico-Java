package com.nodo.modelos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginUser {

	@NotEmpty(message="Por favor Ingresa un email")
	@Email(message="Ingresa un email valido")
	private String email;
	
	@NotEmpty(message="Por favor Ingresa una contraseña")
	@Size(min=6, max=128)
	private String password;

	public LoginUser() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
