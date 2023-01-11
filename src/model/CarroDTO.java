package model;

import java.util.Objects;

public class CarroDTO {

    private String placa;
    private String modelo;
    private Double potencia;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarroDTO carroDTO = (CarroDTO) o;
        return Objects.equals(placa, carroDTO.placa) && Objects.equals(modelo, carroDTO.modelo) && Objects.equals(potencia, carroDTO.potencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placa, modelo, potencia);
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

    @Override
    public String toString() {
        return "CarroDTO{" +
                "placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                ", potencia=" + potencia +
                '}';
    }

    public Double getPotencia() {
        return potencia;
    }

    public void setPotencia(Double potencia) {
        this.potencia = potencia;
    }
}
