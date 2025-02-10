package Prueba;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.sql.Date;

import modelo.DocumentoDTO.PonenciaDTO;
import modelo.HistorialDTO.EventoDTO;

import java.sql.SQLException;

import modelo.DocumentoDTO.ArticuloDTO;
import modelo.DocumentoDTO.DocumentoDTO;
import modelo.DocumentoDTO.LibroDTO;
import modelo.persistencia.UsuarioDAO;
import modelo.persistencia.UsuarioDTO;
import modelo.persistenciaDAO.ArticuloDAO;
import modelo.persistenciaDAO.DocumentoDAO;
import modelo.persistenciaDAO.EventoDAO;
import modelo.persistenciaDAO.LibroDAO;
import modelo.persistenciaDAO.PonenciaDAO;

import java.util.Iterator; 

public class ApiMain {

	public static void main(String[] args) throws SQLException {
		System.out.println("Hola mundo");
		
		//Prueba metodos de usuario
		UsuarioDTO usuario = new UsuarioDTO.Builder()
				.setNombre("Henry Jhonmarcos")
				.setNumeroTelefonico("3453523")
				.setDireccionFisica("Calle 150")
				.setCorreoElectronico("hent23ysd@asds")
				.setContrasena("Mkasdi")
				.build();
		
		
		
		//Prueba metodos de documento
		DocumentoDTO documento = new DocumentoDTO.BuilderDoc()
				.setAutores("Fantastico Señor zorro")
				.setDiaPublicacion("Martes")
				.setEditorial("Colimu12n")
				.setEstado("En Comision")
				.setFechaPublicacion("2/2/2023")
				.setMesPublicacion("Junio")
				.setTitulo("Mr and Mss fox")
				.setTipo("Libro")
				.setPropietario(usuario.getNombre())
				.build();
	
		DocumentoDAO dao3 = new DocumentoDAO(); 

		EventoDTO evento = new EventoDTO.BuilderEvento()
				.setTipoEvento("Dado de baja")
				.setDocumento("2")
				.setUsuario(usuario.getNombre())
				.build(); 
				
		EventoDAO dao4 = new EventoDAO(); 
		
		//dao4.crear(evento);
		
		/*	
		List<Map<Integer, String>> resultados = dao3.buscarPorNombre("Mr");
		if (!resultados.isEmpty()) {
		    resultados.forEach(map -> {
		        Integer id = map.keySet().iterator().next();
		        String titulo = map.values().iterator().next();
		        
		        System.out.println("ID: " + id);
		        System.out.println("Título: " + titulo);
		    });
		} else {
		    System.out.println("No se encontraron documentos.");
		}
	*/
		List<String> eventos = dao4.buscarPorDocumento(2);
		
		Iterator<String> iterador = eventos.iterator(); 
		while(iterador.hasNext()) {
			String registro = iterador.next(); 
			System.out.println(registro);
		}


	} 
}
