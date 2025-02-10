package modelo.persistenciaDAO;

import java.sql.SQLException;


public interface DAO<T>{ //La haremos genereica, pues puede tener diferentes DTO
	int crear(T DTO) throws SQLException;
	T buscarPorId(int id) throws SQLException; 
	void eliminarPorID(int id) throws SQLException; 
	void actualizar(T DTO) throws SQLException;
}
