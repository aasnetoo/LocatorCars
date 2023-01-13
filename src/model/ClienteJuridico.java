package model;

public class ClienteJuridico extends Cliente implements ITabelaDesconto{

    public ClienteJuridico(String nome) {
        this.nome = nome;
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
