package model;

import java.util.Objects;

public class Agencia {

    private int id;
    private String nome;
    private String logradouro;

    public Agencia(String nome) {
        this.nome = nome;
    }

    public Agencia(int id) {
        this.id = id;
    }

    public Agencia(String nome, String logradouro) {
        this.nome = nome;
        this.logradouro = logradouro;
    }

    public Agencia(int id, String nome, String logradouro) {
        this.id = id;
        this.nome = nome;
        this.logradouro = logradouro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agencia that = (Agencia) o;
        return Objects.equals(nome, that.nome) && Objects.equals(logradouro, that.logradouro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, logradouro);
    }
}
