package model;

public class PrecoDevolucao {

    private ITabelaPreco tabelaPreco;
    private ITabelaDesconto tabelaDesconto;

    public PrecoDevolucao(){

    }
    public double calculaDevolucao(VeiculoDTO veiculoDTO, int dias){
        double desconto = tabelaDesconto.valorDesconto(dias);
        double valorTipo = tabelaPreco.calculaValor(veiculoDTO.getTipo());

        return (valorTipo - (valorTipo*desconto));

    }
}
