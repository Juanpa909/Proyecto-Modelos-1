package modelo.HistorialDTO;

import java.io.Serializable;

import modelo.DocumentoDTO.DocumentoDTO;
import modelo.persistencia.UsuarioDTO;

public class EventoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int idEvento;
    private String tipoEvento;
    private String usuario;
    private String documento;

    public EventoDTO(BuilderEvento builder) {
        this.idEvento = builder.idEvento;
        this.tipoEvento = builder.tipoEvento;
        this.usuario = builder.usuario;
        this.documento = builder.documento;
    }

    public int getIdEvento() { return idEvento; }

    public String getTipoEvento() { return tipoEvento; }

    public String getUsuario() { return usuario; }

    public String getDocumento() { return documento; }

    @Override
    public String toString() {
        return "EventoDTO{idEvento=" + idEvento + ", tipoEvento='" + tipoEvento + "', usuario=" + usuario + ", documento=" + documento + "}";
    }

    public static class BuilderEvento {
        private int idEvento;
        private String tipoEvento;
        private String usuario;
        private String documento;

        public BuilderEvento setIdEvento(int idEvento) {
            this.idEvento = idEvento;
            return this;
        }

        public BuilderEvento setTipoEvento(String tipoEvento) {
            this.tipoEvento = tipoEvento;
            return this;
        }

        public BuilderEvento setUsuario(String usuario) {
            this.usuario = usuario;
            return this;
        }

        public BuilderEvento setDocumento(String documento) {
            this.documento = documento;
            return this;
        }

        public EventoDTO build() {
            return new EventoDTO(this);
        }
    }
}
