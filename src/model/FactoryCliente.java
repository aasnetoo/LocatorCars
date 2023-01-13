package model;

import exception.EntradaInvalidaOuInsuficienteException;
import util.Constantes;

public class FactoryCliente {

    public Cliente getCliente(String nome, String tipoPessoa){
        switch(tipoPessoa.toLowerCase()){
            case Constantes.CLIENTE_FISICO -> {
                return new ClienteFisico(nome);
            }
            case Constantes.CLIENTE_JURIDICO -> {
                return new ClienteJuridico(nome);
            }
            default -> throw new EntradaInvalidaOuInsuficienteException("Entrada inválida!");
        }
    }
}
