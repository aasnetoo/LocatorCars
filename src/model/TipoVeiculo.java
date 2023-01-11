package model;

import exception.EntradaInvalidaOuInsuficienteException;
import util.Constantes;
public enum TipoVeiculo {

    CARRO("Carro", "1"),
    MOTO("Moto", "2"),
    CAMINHAO("Caminhao", "3");

    public String nome;
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    TipoVeiculo(String nome, String id) {
        this.nome = nome;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static String obterTipoVeiculo(String id) {
        switch (id) {
            case Constantes.TIPO_CARRO -> {
                return CARRO.getNome();
            }
            case Constantes.TIPO_MOTO -> {
                return MOTO.getNome();
            }
            case Constantes.TIPO_CAMINHAO -> {
                return CAMINHAO.getNome();
            }
            default -> throw new EntradaInvalidaOuInsuficienteException("Entrada inválida!");
        }
    }




}
