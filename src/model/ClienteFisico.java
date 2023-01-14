package model;

public class ClienteFisico extends Cliente{
    public ClienteFisico(String nome, String telefone, String documento, String tipoCliente) {
        this.nome = nome;
        this.telefone = telefone;
        this.documento = documento;
        this.tipoCliente = tipoCliente;
    }

    public ClienteFisico(String documento) {
        this.documento = documento;
    }

    @Override
    public Double valorDesconto(int dias) {
        if (dias>5){
            return 0.05;
        }else{
            return 0.0;
        }
    }

}
