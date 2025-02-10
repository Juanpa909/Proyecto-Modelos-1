package modelo.persistenciaDAO;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.HistorialDTO.EventoDTO;
import modelo.persistencia.ConexionDB;

public class EventoDAO{

	public List<String> buscarPorDocumento(int id) throws SQLException {
	    String sql = "SELECT e.tipoevento, d.titulo, d.tipo, e.usuario, d.fechapublicacion " +
	                 "FROM evento e " +
	                 "JOIN documento d ON e.documento = d.iddocumento " +
	                 "WHERE d.iddocumento = ?";

	    List<String> eventos = new ArrayList<>();

	    try (Connection conn = ConexionDB.getInstance().getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, id);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                String eventoStr = rs.getString("tipoevento") + " - " +
	                                   rs.getString("titulo") + "|" +
	                                   rs.getString("tipo") + ", " +
	                                   rs.getString("usuario") + ", " +
	                                   rs.getString("fechapublicacion");
	                
	                eventos.add(eventoStr);
	            }
	        }
	    }
	    return eventos;
	}


}
