package model;

public class ClienteJuridico extends Cliente{

    public ClienteJuridico(String nome, String telefone, String documento, String tipoCliente) {
        this.nome = nome;
        this.telefone = telefone;
        this.documento = documento;
        this.tipoCliente = tipoCliente;
    }

    public ClienteJuridico(String documento) {
        this.documento = documento;
    }

    @Override
    public Double valorDesconto(int dias) {
        if (dias>3){
            return 0.10;
        }else{
            return 0.0;
        }
    }

}
