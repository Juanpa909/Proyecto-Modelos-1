package modelo.FactoryDAO;

import modelo.persistencia.UsuarioDAO;
import modelo.persistenciaDAO.ArticuloDAO;
import modelo.persistenciaDAO.DocumentoDAO;
import modelo.persistenciaDAO.EventoDAO;
import modelo.persistenciaDAO.LibroDAO;
import modelo.persistenciaDAO.PonenciaDAO;
import modelo.persistenciaDAO.ReservaDAO;

public interface DAOFactory {

	LibroDAO crearLibro(); 
	PonenciaDAO crearPonencia(); 
	ArticuloDAO crearArticulo();

}
