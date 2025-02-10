package modelo.persistenciaDAO;

import java.sql.SQLException;

import modelo.HistorialDTO.ReservaDTO;

public class ReservaDAO implements DAO<ReservaDTO> {

	@Override
	public int crear(ReservaDTO DTO) throws SQLException {
		// TODO Auto-generated method stub
		return 0; 
	}

	@Override
	public void eliminarPorID(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizar(ReservaDTO DTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ReservaDTO buscarPorId(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
