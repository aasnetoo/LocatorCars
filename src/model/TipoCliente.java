package model;

import exception.EntradaInvalidaOuInsuficienteException;
import util.Constantes;

public enum TipoCliente {
    PESSOA_FISICA("PF"),
    PESSOA_JURIDICA("PJ");

    public String tipoCliente;

    TipoCliente(String tipoCliente){
        this.tipoCliente = tipoCliente;
    }

    public String getTipo() {
        return tipoCliente;
    }

    public static String obterTipoCliente(String documento) {
        switch (documento) {
            case Constantes.CLIENTE_FISICO -> {
                return PESSOA_FISICA.getTipo();
            }
            case Constantes.CLIENTE_JURIDICO -> {
                return PESSOA_JURIDICA.getTipo();
            }
            default -> throw new EntradaInvalidaOuInsuficienteException("Entrada inválida!");
        }
    }
}
