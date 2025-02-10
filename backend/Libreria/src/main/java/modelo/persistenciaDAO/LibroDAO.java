package modelo.persistenciaDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import modelo.DocumentoDTO.LibroDTO;
import modelo.persistencia.ConexionDB;

public class LibroDAO implements DAO<LibroDTO>{

	public int crear(LibroDTO libro) throws SQLException {
		String sql = "INSERT INTO libro (numeropaginas, isbn, documento) VALUES (?, ?, ?)";
		try(Connection conexion = ConexionDB.getInstance().getConnection();
			PreparedStatement pstmt = conexion.prepareStatement(sql)){
			pstmt.setInt(1, Integer.parseInt(libro.getNumeroPaginas()));
			pstmt.setString(2, libro.getIsbn());
			pstmt.setInt(3, libro.getIdDocumento());
			pstmt.executeUpdate();
			
			return libro.getIdDocumento(); 
		}
	}
	

	public void eliminarPorID(int id) throws SQLException {
	    String sqlLibro = "DELETE FROM libro WHERE idlibro = ?";
	    String sqlDocumento = "DELETE FROM documento WHERE iddocumento = " +
	                          "(SELECT documento FROM libro WHERE idlibro = ?)";

	    try (Connection conn = ConexionDB.getInstance().getConnection()) {
	        conn.setAutoCommit(false);

	        try (PreparedStatement pstmtDocumento = conn.prepareStatement(sqlDocumento);
	             PreparedStatement pstmtLibro = conn.prepareStatement(sqlLibro)) {
	            
	            pstmtDocumento.setInt(1, id);
	            pstmtDocumento.executeUpdate();

	            pstmtLibro.setInt(1, id);
	            pstmtLibro.executeUpdate();
	            
	            conn.commit();
	        } catch (SQLException e) {
	            conn.rollback();
	            throw e;
	        }
	    }
	}


	public void actualizar(LibroDTO libro) throws SQLException {
	    String sqlDocumento = "UPDATE documento SET titulo = ?, fechapublicacion = ?, autores = ?, diapublicacion = ?, " +
	                           "mespublicacion = ?, editorial = ?, estado = ?, propietario = ?, tipo=? WHERE iddocumento = ?";
	    
	    String sqlLibro = "UPDATE libro SET isbn = ?, numeropaginas = ? WHERE idlibro = ?";

	    try (Connection conn = ConexionDB.getInstance().getConnection()) {
	        conn.setAutoCommit(false); 

	        try (PreparedStatement pstmtDocumento = conn.prepareStatement(sqlDocumento);
	             PreparedStatement pstmtLibro = conn.prepareStatement(sqlLibro)) {

	            pstmtDocumento.setString(1, libro.getTitulo());
	            pstmtDocumento.setDate(2, convertirStringADate(libro.getFechaPublicacion()));
	            pstmtDocumento.setString(3, libro.getAutores());
	            pstmtDocumento.setString(4, libro.getDiaPublicacion());
	            pstmtDocumento.setString(5, libro.getMesPublicacion());
	            pstmtDocumento.setString(6, libro.getEditorial());
	            pstmtDocumento.setString(7, libro.getEstado());
	            pstmtDocumento.setString(8, libro.getPropietario());
	            pstmtDocumento.setInt(9, libro.getIdDocumento());
	            pstmtDocumento.setString(10, libro.getTipo());
	            pstmtDocumento.executeUpdate();

	            pstmtLibro.setString(1, libro.getIsbn());
	            pstmtLibro.setInt(2, Integer.parseInt(libro.getNumeroPaginas()));
	            pstmtLibro.executeUpdate();

	            conn.commit(); // Confirma los cambios
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

	public LibroDTO buscarPorId(int id) throws SQLException {
	    String sql = "SELECT l.idlibro, l.isbn, l.numeropaginas, " +
	                 "d.iddocumento, d.titulo, d.fechapublicacion, d.autores, " +
	                 "d.mespublicacion, d.diapublicacion, d.editorial, d.estado, d.propietario " +
	                 "FROM libro l " +
	                 "JOIN documento d ON l.documento = d.iddocumento " +
	                 "WHERE l.idlibro = ?";
	    
	    try (Connection conn = ConexionDB.getInstance().getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                System.out.println(rs.getString("titulo"));
	                return new LibroDTO.BuilderLibro()
	                        .setIsbn(rs.getString("isbn"))
	                        .setNumeroPaginas(rs.getString("numeropaginas"))
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
