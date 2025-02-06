package modelo.FactoryDAO;

import modelo.persistencia.UsuarioDAO;
import modelo.persistenciaDAO.ArticuloDAO;
import modelo.persistenciaDAO.DocumentoDAO;
import modelo.persistenciaDAO.EventoDAO;
import modelo.persistenciaDAO.LibroDAO;
import modelo.persistenciaDAO.PonenciaDAO;
import modelo.persistenciaDAO.ReservaDAO;

public class FabricaDAO implements DAOFactory{

	@Override
	public ArticuloDAO crearArticulo() {
		ArticuloDAO articuloDAO = new ArticuloDAO(); 
		return articuloDAO;
	}

	@Override
	public LibroDAO crearLibro() {
		LibroDAO libroDAO = new LibroDAO(); 
		return libroDAO;
	}

	@Override
	public PonenciaDAO crearPonencia() {
		PonenciaDAO ponenciaDAO = new PonenciaDAO(); 
		return ponenciaDAO;
	}

	@Override
	public DocumentoDAO crearDocumento() {
		DocumentoDAO documentoDAO = new DocumentoDAO(); 
		return documentoDAO;
	}

	@Override
	public ReservaDAO crearReserva() {
		ReservaDAO reservaDAO = new ReservaDAO(); 
		return reservaDAO;
	}

	@Override
	public EventoDAO crearEvento() {
		EventoDAO eventoDAO = new EventoDAO(); 
		return eventoDAO;
	}

	@Override
	public UsuarioDAO crearUsuario() {
		UsuarioDAO usuarioDAO = new UsuarioDAO(); 
		return usuarioDAO;
	}

}
