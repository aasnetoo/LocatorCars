package model;

public abstract class Cliente {
    public String nome;
    public String telefone;
    public String documento;
    public String tipoCliente;

    public Double valorDesconto(int dias) {
        return 0.0;
    }

}
