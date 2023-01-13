package model;

public class ClienteFisico extends Cliente implements ITabelaDesconto {
    public ClienteFisico(String nome) {
        this.nome = nome;
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
