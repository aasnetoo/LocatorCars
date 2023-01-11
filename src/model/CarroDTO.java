package model;

import java.util.Objects;

public class CarroDTO {

    private String placa;
    private String modelo;
    private Double potencia;
    private String tipo;

    public String getTipo() {
        return tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarroDTO carroDTO = (CarroDTO) o;
        return Objects.equals(placa, carroDTO.placa) && Objects.equals(modelo, carroDTO.modelo) && Objects.equals(potencia, carroDTO.potencia) && Objects.equals(tipo, carroDTO.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placa, modelo, potencia, tipo);
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPlaca() {
        return placa;
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
}
