package modelo.DocumentoDTO;

import java.io.Serializable;

public class DocumentoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private int idDocumento;
	private String titulo; 
	private String fechaPublicacion; 
	private String diaPublicacion; 
	private String editorial; 
	private String estado; 
	private String propietario; 
	
	public DocumentoDTO(BuilderDoc builder ) {
		this.idDocumento = builder.idDocumento; 
		this.titulo = builder.titulo; 
		this.fechaPublicacion = builder.fechaPublicacion; 
		this.diaPublicacion = builder.diaPublicacion; 
		this.editorial = builder.editorial; 
		this.estado = builder.estado; 
		this.propietario = builder.propietario; 
	}
	
	public int getIdDocumento() { return idDocumento;}
	
	public String getTitulo() { return titulo; }
	
	public String getFechaPublicacion() { return fechaPublicacion; }
	
	public String getDiaPublicacion() {	return diaPublicacion; }
	
	public String getEditorial() { return editorial; }
	
	public String getEstado() { return estado; }
	
	public String getPropietario() { return propietario; }
	
	@Override
	public String toString() {
		String cadena = String.valueOf(this.idDocumento)+" "+this.titulo+" "+this.fechaPublicacion; 
		return cadena;
	}
	
	public static class BuilderDoc{
		private int idDocumento;
		private String titulo; 
		private String fechaPublicacion; 
		private String diaPublicacion; 
		private String editorial; 
		private String estado; 
		private String propietario;
	
		public BuilderDoc setIdDocumento(int idDocumento) { 
			this.idDocumento = idDocumento; 
			return this; 
		}
		
		public BuilderDoc setTitulo(String titulo) { 
			this.titulo = titulo; 
			return this; 
		}

		public BuilderDoc setFechaPublicacion(String fechaPublicacion) {
			this.fechaPublicacion = fechaPublicacion; 
			return this;
		}
		
		public BuilderDoc setDiaPublicacion(String diaPublicacion) { 
			this.diaPublicacion = diaPublicacion;
			return this; 
		}
        
		public BuilderDoc setEditorial(String editorial) { 
			this.editorial = editorial; 
			return this; 
		}
		
		public BuilderDoc setEstado(String estado) { 
			this.estado = estado; 
			return this;
		}
		
		public BuilderDoc setPropietario(String propietario) { 
			this.propietario = propietario; 
			return this; 
		}
		
		public DocumentoDTO build() {
            return new DocumentoDTO(this);
        }
    }

}