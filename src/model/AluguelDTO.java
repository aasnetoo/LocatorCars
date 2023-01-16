package model;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class AluguelDTO {

    Long id;
    Cliente cliente;
    Veiculo veiculo;
    Date dataInicio;
    Date dataDevolucao;
    Time horarioAgendado;
    Agencia agenciaRetirada;
    Agencia agenciaDevolucao;
    BigDecimal valorAluguel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AluguelDTO that = (AluguelDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(cliente, that.cliente) && Objects.equals(veiculo, that.veiculo) && Objects.equals(dataInicio, that.dataInicio) && Objects.equals(dataDevolucao, that.dataDevolucao) && Objects.equals(agenciaRetirada, that.agenciaRetirada) && Objects.equals(agenciaDevolucao, that.agenciaDevolucao) && Objects.equals(valorAluguel, that.valorAluguel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cliente, veiculo, dataInicio, dataDevolucao, agenciaRetirada, agenciaDevolucao, valorAluguel);
    }

    @Override
    public String toString() {
        return "AluguelDTO{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", veiculo=" + veiculo +
                ", dataInicio=" + dataInicio +
                ", dataDevolucao=" + dataDevolucao +
                ", agenciaRetirada=" + agenciaRetirada +
                ", agenciaDevolucao=" + agenciaDevolucao +
                ", valorAluguel=" + valorAluguel +
                '}';
    }

    public Time getHorarioAgendado() {
        return horarioAgendado;
    }

    public void setHorarioAgendado(Time horarioAgendado) {
        this.horarioAgendado = horarioAgendado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
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
}