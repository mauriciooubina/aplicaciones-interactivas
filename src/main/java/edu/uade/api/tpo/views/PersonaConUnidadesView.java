package edu.uade.api.tpo.views;

import edu.uade.api.tpo.modelo.Persona;
import edu.uade.api.tpo.modelo.Rol;
import lombok.*;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonaConUnidadesView {

    private String documento;
    private String nombre;
    private String email;
    private Rol rol;
    List<UnidadView> duenioDe;
    List<UnidadView> inquilinoDe;

    public PersonaConUnidadesView(Persona persona, List<UnidadView> duenioDe, List<UnidadView> inquilinoDe){
        documento= persona.getDocumento();
        nombre= persona.getNombre();
        email= persona.getEmail();
        rol=persona.getRol();
        this.duenioDe=duenioDe;
        this.inquilinoDe=inquilinoDe;
    }
}
