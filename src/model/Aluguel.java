package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Aluguel {

    Long id;
    ClienteDTO cliente;
    Veiculo veiculo;
    Date dataInicio;
    Date dataDevolucao;
    Agencia agenciaRetirada;
    Agencia agenciaDevolucao;
    BigDecimal valorAluguel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Agencia getAgenciaRetirada() {
        return agenciaRetirada;
    }

    public void setAgenciaRetirada(Agencia agenciaRetirada) {
        this.agenciaRetirada = agenciaRetirada;
    }

    public Agencia getAgenciaDevolucao() {
        return agenciaDevolucao;
    }

    public void setAgenciaDevolucao(Agencia agenciaDevolucao) {
        this.agenciaDevolucao = agenciaDevolucao;
    }

    public BigDecimal getValorAluguel() {
        return valorAluguel;
    }

    public void setValorAluguel(BigDecimal valorAluguel) {
        this.valorAluguel = valorAluguel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluguel aluguel = (Aluguel) o;
        return Objects.equals(id, aluguel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}