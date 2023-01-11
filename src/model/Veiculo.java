package model;

import java.util.Objects;

public class Veiculo {

    private String placa;
    private String modelo;
    private Double potencia;
    private String tipo;

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

    public String getTipo() {
        return tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Veiculo veiculo = (Veiculo) o;
        return Objects.equals(placa, veiculo.placa) && Objects.equals(modelo, veiculo.modelo) && Objects.equals(potencia, veiculo.potencia) && Objects.equals(tipo, veiculo.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placa, modelo, potencia, tipo);
    }

    @Override
    public String toString() {
        return "Veiculo{" +
                "placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                ", potencia=" + potencia +
                ", tipo='" + tipo + '\'' +
                '}';
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
