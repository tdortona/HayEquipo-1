package ar.edu.grupoesfera.cursospring.controladores;


import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.grupoesfera.cursospring.modelo.Cancha;
import ar.edu.grupoesfera.cursospring.modelo.Loggin;
import ar.edu.grupoesfera.cursospring.modelo.Partido;
import ar.edu.grupoesfera.cursospring.modelo.Usuario;
import ar.edu.grupoesfera.cursospring.servicios.CanchasServicios;
import ar.edu.grupoesfera.cursospring.servicios.PartidosServicios;
import ar.edu.grupoesfera.cursospring.servicios.RegistrarUsuario;
import ar.edu.grupoesfera.cursospring.servicios.UsuarioLoggin;
import ar.edu.grupoesfera.cursospring.servicios.UsuarioServicios;
import ar.edu.grupoesfera.cursospring.servicios.VerificarUsuario;

@Controller
public class ControladorInicio {
/*	@Inject
	private VerificarUsuario usuario;*/
	
	@Inject
	private CanchasServicios canchas;
	
	@Inject
	private PartidosServicios partidosServicios;
	
	@Inject
	private UsuarioServicios usuarioServicios;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView modelarVista( ModelMap model,HttpServletRequest req) {
		List<Cancha> cancha =   canchas.listarCanchas();
		List<Partido> partido = partidosServicios.listarPartidos();
		model.put("canchas",cancha);
		model.put("partidos",partido);
		return new ModelAndView("vistas", model);
	}
	
	@RequestMapping(value = "/registrarse", method = { RequestMethod.GET })
	public ModelAndView registroUsuario(ModelMap model) {
				return new ModelAndView("registrarse","command",new Usuario());
	}
	
	@RequestMapping(value = "/iniciarSesion", method = { RequestMethod.GET })
	public ModelAndView iniciarSesion(ModelMap model) {
				return new ModelAndView("loggin","command",new Loggin());
	}
	
	@RequestMapping(value="/perfil",method = { RequestMethod.GET })
	public ModelAndView irAperfil (ModelMap model, HttpServletRequest req){
		Long idUsuario= (Long) req.getSession().getAttribute("idUsuario");
		List<Partido> lista = partidosServicios.listarPartidosPorId(idUsuario);
		//List<Partido> lista = usuarioServicios.misPartidos(idUsuario);
		//model.addAttribute("misPartidos", lista);
		return new ModelAndView("perfil",model);
		
	}
	
	@RequestMapping(value = "/logout", method = { RequestMethod.GET })
	public ModelAndView cerrarSesion(HttpServletRequest session, ModelMap model) {
				session.getSession().invalidate();
				return new ModelAndView("vistas",model);
	}
	
	@RequestMapping(value="/unirse",method = { RequestMethod.GET })
	public ModelAndView unirsePartido (@RequestParam("id") Long idPartido,ModelMap model, HttpServletRequest req){
		Long idUsuario= (Long) req.getSession().getAttribute("idUsuario");
		if(req.getSession().getAttribute("usuario") == null)
			return new ModelAndView("loggin","command",new Loggin());
		else
			partidosServicios.insertarJugador(idPartido, idUsuario);
			return new ModelAndView("perfil", model);
		
	}
	

}
