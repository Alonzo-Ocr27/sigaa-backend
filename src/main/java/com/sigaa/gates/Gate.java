package com.sigaa.gates;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gates")
public class Gate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String tipo;

    @ElementCollection
    @CollectionTable(
            name = "gate_tipos_aeronave",
            joinColumns = @JoinColumn(name = "gate_id")
    )
    @Column(name = "tipo_aeronave")
    private List<String> tiposAeronaveAceptados = new ArrayList<>();

    @Column(nullable = false)
    private String estado;

    public Gate() {}

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getTipo() { return tipo; }
    public List<String> getTiposAeronaveAceptados() { return tiposAeronaveAceptados; }
    public String getEstado() { return estado; }

    public void setId(Long id) { this.id = id; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setTiposAeronaveAceptados(List<String> tiposAeronaveAceptados) { this.tiposAeronaveAceptados = tiposAeronaveAceptados; }
    public void setEstado(String estado) { this.estado = estado; }
}
