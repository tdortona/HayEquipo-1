package ar.edu.grupoesfera.cursospring.servicios;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import ar.edu.grupoesfera.cursospring.modelo.Loggin;

public interface VerificarUsuario {
	public Boolean verificarUsuarioLogeado(Loggin user);

}
