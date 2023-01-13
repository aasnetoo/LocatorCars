package model;

import java.util.Objects;

public class AgenciaDTO {

    private String nome;
    private String logadouro;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogadouro() {
        return logadouro;
    }

    public void setLogadouro(String logadouro) {
        this.logadouro = logadouro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgenciaDTO that = (AgenciaDTO) o;
        return Objects.equals(nome, that.nome) && Objects.equals(logadouro, that.logadouro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, logadouro);
    }
}
