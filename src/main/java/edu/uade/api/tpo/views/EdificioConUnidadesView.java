package edu.uade.api.tpo.views;

import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EdificioConUnidadesView {

    private int codigo;
    private String nombre;
    private String direccion;

    private List<UnidadSinEdificioView> unidades;

}
