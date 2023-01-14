package model;

import exception.EntradaInvalidaOuInsuficienteException;
import util.Constantes;
public enum TipoVeiculo {

    CARRO("Carro"),
    MOTO("Moto"),
    CAMINHAO("Caminhao");

    public String nome;

    TipoVeiculo(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static String obterTipoVeiculo(String nome) {
        switch (nome) {
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

    public static double calculaValor(String tipo) {
        switch (tipo){
            case Constantes.TIPO_CARRO -> {
                return Constantes.VALOR_CARRO;
            }
            case Constantes.TIPO_MOTO -> {
                return Constantes.VALOR_MOTO;
            }
            case Constantes.TIPO_CAMINHAO -> {
                return Constantes.VALOR_CAMINHAO;
            }
            default -> throw new EntradaInvalidaOuInsuficienteException("Entrada inválida!");
        }
    }
}
