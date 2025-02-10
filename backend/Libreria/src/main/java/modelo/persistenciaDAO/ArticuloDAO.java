package modelo.persistenciaDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import modelo.DocumentoDTO.ArticuloDTO;
import modelo.persistencia.ConexionDB;

public class ArticuloDAO implements DAO<ArticuloDTO>{

	@Override
	public int crear(ArticuloDTO articulo) throws SQLException {
		String sql = "INSERT INTO articulo (ssn, documento) VALUES (?, ?)";
		try(Connection conexion = ConexionDB.getInstance().getConnection();
			PreparedStatement pstmt = conexion.prepareStatement(sql)){
			pstmt.setString(1, articulo.getSsn());
			pstmt.setInt(2, articulo.getIdDocumento());
			pstmt.executeUpdate();
			
			return articulo.getIdDocumento(); 
		}
	}

	


	@Override

	public void eliminarPorID(int id) throws SQLException {
	    String sqlArticulo = "DELETE FROM articulo WHERE idarticulo = ?";
	    String sqlDocumento = "DELETE FROM documento WHERE iddocumento = " +
	                          "(SELECT documento FROM articulo WHERE idarticulo = ?)";

	    try (Connection conn = ConexionDB.getInstance().getConnection()) {
	        conn.setAutoCommit(false);

	        try (PreparedStatement pstmtDocumento = conn.prepareStatement(sqlDocumento);
	             PreparedStatement pstmtArticulo = conn.prepareStatement(sqlArticulo)) {
	            
	            pstmtDocumento.setInt(1, id);
	            pstmtDocumento.executeUpdate();

	            pstmtArticulo.setInt(1, id);
	            pstmtArticulo.executeUpdate();
	            
	            conn.commit();
	        } catch (SQLException e) {
	            conn.rollback();
	            throw e;
	        }
	    }
	}


	@Override

	public void actualizar(ArticuloDTO articulo) throws SQLException {
	    String sqlDocumento = "UPDATE documento SET titulo = ?, fechapublicacion = ?, autores = ?, diapublicacion = ?, " +
	                           "mespublicacion = ?, editorial = ?, estado = ?, propietario = ? WHERE iddocumento = ?";
	    
	    String sqlArticulo = "UPDATE articulo SET ssn = ? WHERE idarticulo = ?";

	    try (Connection conn = ConexionDB.getInstance().getConnection()) {
	        conn.setAutoCommit(false); // Inicia transacción

	        try (PreparedStatement pstmtDocumento = conn.prepareStatement(sqlDocumento);
	             PreparedStatement pstmtArticulo = conn.prepareStatement(sqlArticulo)) {

	            // 1️⃣ Actualizar primero el Documento
	            pstmtDocumento.setString(1, articulo.getTitulo());
	            pstmtDocumento.setDate(2, convertirStringADate(articulo.getFechaPublicacion()));
	            pstmtDocumento.setString(3, articulo.getAutores());
	            pstmtDocumento.setString(4, articulo.getDiaPublicacion());
	            pstmtDocumento.setString(5, articulo.getMesPublicacion());
	            pstmtDocumento.setString(6, articulo.getEditorial());
	            pstmtDocumento.setString(7, articulo.getEstado());
	            pstmtDocumento.setString(8, articulo.getPropietario());
	            pstmtDocumento.setInt(9, articulo.getIdDocumento());
	            pstmtDocumento.setString(10, articulo.getTipo());
	            pstmtDocumento.executeUpdate();

	            // 2️⃣ Luego, actualizar el Artículo
	            pstmtArticulo.setString(1, articulo.getSsn());
	            pstmtArticulo.executeUpdate();

	            conn.commit(); // Confirma los cambios
	        } catch (SQLException e) {
	            conn.rollback(); // Revierte la transacción en caso de error
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
	public ArticuloDTO buscarPorId(int id) throws SQLException {
	    String sql = "SELECT a.idarticulo, a.ssn, " +
	                 "d.iddocumento, d.titulo, d.fechapublicacion, d.autores, " +
	                 "d.mespublicacion, d.diapublicacion, d.editorial, d.estado, d.propietario " +
	                 "FROM articulo a " +
	                 "JOIN documento d ON a.documento = d.iddocumento " +
	                 "WHERE a.idarticulo = ?";
	    
	    try (Connection conn = ConexionDB.getInstance().getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                System.out.println(rs.getString("titulo"));
	                return new ArticuloDTO.BuilderArticulo()
	                        .setSsn(rs.getString("ssn"))
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
