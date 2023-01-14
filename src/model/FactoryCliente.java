package model;

import exception.EntradaInvalidaOuInsuficienteException;
import util.Constantes;

public class FactoryCliente {

    public Cliente getCliente(String documento, String tipoPessoa){
        switch(tipoPessoa.toLowerCase()){
            case Constantes.CLIENTE_FISICO -> {
                return new ClienteFisico(documento);
            }
            case Constantes.CLIENTE_JURIDICO -> {
                return new ClienteJuridico(documento);
            }
            default -> throw new EntradaInvalidaOuInsuficienteException("Entrada inválida!");
        }
    }
}
