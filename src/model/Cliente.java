package model;

public abstract class Cliente {
    public String nome;
    public String tipoCliente;

    public Double valorDesconto(int dias) {
        return 0.0;
    }

}
