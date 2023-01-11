package model;

import java.util.Objects;

public class VeiculoDTO {

    private String placa;
    private String modelo;
    private Double potencia;
    private String tipo;

    public String getPlaca() {
        return placa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VeiculoDTO that = (VeiculoDTO) o;
        return Objects.equals(placa, that.placa) && Objects.equals(modelo, that.modelo) && Objects.equals(potencia, that.potencia) && Objects.equals(tipo, that.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placa, modelo, potencia, tipo);
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getPotencia() {
        return potencia;
    }

    public void setPotencia(Double potencia) {
        this.potencia = potencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
