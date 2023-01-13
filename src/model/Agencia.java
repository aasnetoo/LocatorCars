package model;

import java.util.Objects;

public class Agencia {

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
        Agencia agencia = (Agencia) o;
        return Objects.equals(nome, agencia.nome) && Objects.equals(logadouro, agencia.logadouro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, logadouro);
    }

    @Override
    public String toString() {
        return "Agencia{" +
                "nome='" + nome + '\'' +
                ", logadouro='" + logadouro + '\'' +
                '}';
    }
}
