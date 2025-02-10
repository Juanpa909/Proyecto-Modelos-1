package modelo.persistenciaDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import modelo.DocumentoDTO.PonenciaDTO;
import modelo.persistencia.ConexionDB;

public class PonenciaDAO implements DAO<PonenciaDTO>{

	@Override
	public int crear(PonenciaDTO ponencia) throws SQLException {
		String sql = "INSERT INTO ponencia (congreso, isbn, documento) VALUES (?, ?, ?)";
		try(Connection conexion = ConexionDB.getInstance().getConnection();
			PreparedStatement pstmt = conexion.prepareStatement(sql)){
			pstmt.setString(1, ponencia.getCongreso());
			pstmt.setString(2, ponencia.getIsbn());
			pstmt.setInt(3, 4);
			pstmt.executeUpdate();
			
			return ponencia.getIdDocumento();
		}
	}


	@Override
	public void eliminarPorID(int id) throws SQLException {
	    String sqlPonencia = "DELETE FROM ponencia WHERE idponencia = ?";
	    String sqlDocumento = "DELETE FROM documento WHERE iddocumento = " +
	                          "(SELECT documento FROM ponencia WHERE idponencia = ?)";

	    try (Connection conn = ConexionDB.getInstance().getConnection()) {
	        conn.setAutoCommit(false);

	        try (PreparedStatement pstmtDocumento = conn.prepareStatement(sqlDocumento);
	             PreparedStatement pstmtPonencia = conn.prepareStatement(sqlPonencia)) {
	            
	            pstmtDocumento.setInt(1, id);
	            pstmtDocumento.executeUpdate();

	            pstmtPonencia.setInt(1, id);
	            pstmtPonencia.executeUpdate();
	            
	            conn.commit();
	        } catch (SQLException e) {
	            conn.rollback();
	            throw e;
	        }
	    }
	}


	@Override
	public void actualizar(PonenciaDTO ponencia) throws SQLException {
	    String sqlDocumento = "UPDATE documento SET titulo = ?, fechapublicacion = ?, autores = ?, diapublicacion = ?, " +
	                           "mespublicacion = ?, editorial = ?, estado = ?, propietario = ? WHERE iddocumento = ?";
	    
	    String sqlPonencia = "UPDATE ponencia SET congreso = ?, isbn = ? WHERE idponencia = ?";

	    try (Connection conn = ConexionDB.getInstance().getConnection()) {
	        conn.setAutoCommit(false); 

	        try (PreparedStatement pstmtDocumento = conn.prepareStatement(sqlDocumento);
	             PreparedStatement pstmtPonencia = conn.prepareStatement(sqlPonencia)) {

	            pstmtDocumento.setString(1, ponencia.getTitulo());
	            pstmtDocumento.setDate(2, convertirStringADate(ponencia.getFechaPublicacion()));
	            pstmtDocumento.setString(3, ponencia.getAutores());
	            pstmtDocumento.setString(4, ponencia.getDiaPublicacion());
	            pstmtDocumento.setString(5, ponencia.getMesPublicacion());
	            pstmtDocumento.setString(6, ponencia.getEditorial());
	            pstmtDocumento.setString(7, ponencia.getEstado());
	            pstmtDocumento.setString(8, ponencia.getPropietario());
	            pstmtDocumento.setInt(9, ponencia.getIdDocumento());
	            pstmtDocumento.setString(10, ponencia.getTipo());	            pstmtDocumento.executeUpdate();
	            
	            pstmtPonencia.setString(1, ponencia.getCongreso());
	            pstmtPonencia.setString(2, ponencia.getIsbn());
	            pstmtPonencia.executeUpdate();
	        
	            conn.commit();
	        } catch (SQLException e) {
	            conn.rollback(); // Revierte en caso de error
	            throw e;
	        }
	    }
	}

	  public static Date convertirStringADate(String fechaStr) {
	        try {
	            SimpleDateFormat formato = new SimpleDateFormat("d/M/yyyy"); // Define el formato esperado
	            java.util.Date fechaUtil = formato.parse(fechaStr); // Convierte a java.util.Date
	            return new java.sql.Date(fechaUtil.getTime()); // Convierte a java.sql.Date
	        } catch (ParseException e) {
	            e.printStackTrace();
	            return null; // Manejo de error: Devuelve null si hay un fallo
	        }
	    }

	@Override
	public PonenciaDTO buscarPorId(int id) throws SQLException {
	    String sql = "SELECT p.idponencia, p.congreso, p.isbn, " +
	                 "d.iddocumento, d.titulo, d.fechapublicacion, d.autores, " +
	                 "d.mespublicacion, d.diapublicacion, d.editorial, d.estado, d.propietario " +
	                 "FROM ponencia p " +
	                 "JOIN documento d ON p.documento = d.iddocumento " +
	                 "WHERE p.idponencia = ?";
	    
	    try (Connection conn = ConexionDB.getInstance().getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                System.out.println(rs.getString("titulo"));
	                return new PonenciaDTO.BuilderPonencia()
	                        .setCongreso(rs.getString("congreso"))
	                        .setIsbn(rs.getString("isbn"))
	                        .setIdDocumento(rs.getInt("iddocumento"))  // Datos de documento heredados
	                        .setTitulo(rs.getString("titulo"))
	                        .setFechaPublicacion(rs.getString("fechapublicacion"))
	                        .setAutores(rs.getString("autores"))
	                        .setMesPublicacion(rs.getString("mespublicacion"))
	                        .setDiaPublicacion(rs.getString("diapublicacion"))
	                        .setEditorial(rs.getString("editorial"))
	                        .setEstado(rs.getString("estado"))
	                        .setPropietario(rs.getString("propietario"))
	                        .build();
	            }
	        }
	    }
	    return null;
	}


}
