package modelo.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO{
	
	
	public void crear(UsuarioDTO usuario) throws SQLException{
		String sql = "INSERT INTO usuario (nombre, contrasena, correoelectronico, direccionfisica, numerotelefonico) VALUES (?, ?, ?, ?, ?)";
		try(Connection conexion = ConexionDB.getInstance().getConnection();
				PreparedStatement pstmt = conexion.prepareStatement(sql)){
			pstmt.setString(1, usuario.getNombre());
			pstmt.setString(2, usuario.getContrasena());
			pstmt.setString(3, usuario.getCorreoElectronico());
			pstmt.setString(4, usuario.getDireccionFisica());
			pstmt.setString(5, usuario.getNumeroTelefonico());
			pstmt.executeUpdate();
		}
	}

	
	public UsuarioDTO buscarPorNombre(String usuario) throws SQLException{
		String sql = "SELECT * from usuario where nombre = ?";
		try (Connection conn = ConexionDB.getInstance().getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, usuario);
	            try (ResultSet rs = pstmt.executeQuery()) {
	            	if(rs.next()) {
	            		return new UsuarioDTO.Builder()
	                            .setContrasena(rs.getString("contrasena"))
	                            .setNombre(rs.getString("nombre"))
	                            .setCorreoElectronico(rs.getString("correoelectronico"))
	                            .setDireccionFisica(rs.getString("direccionfisica"))
	                            .setNumeroTelefonico(rs.getString("numerotelefonico"))
	                            .build();
	            	};
	                
	                
	            }
	    }
	    return null;
	}
}
