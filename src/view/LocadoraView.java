package view;

import controller.LocadoraController;
import exception.EntradaInvalidaOuInsuficienteException;
import exception.ListaVaziaException;
import model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import util.Constantes;
import util.Mensagens;

import static java.lang.System.exit;

public class LocadoraView {

    Scanner scan = new Scanner(System.in);

    LocadoraController controller = new LocadoraController();
    Mensagens mensagens = new Mensagens();


    public LocadoraView() throws SQLException {
    }

    public String opcaoMenu() {
        System.out.println("---------- MENU ----------");
        System.out.println("1 - Adicionar Veiculo");
        System.out.println("2 - Editar Veiculo");
        System.out.println("3 - Listar Veiculo");
        System.out.println("4 - Remover um produto");
        System.out.println("5 - Buscar por nome");
        System.out.println("6 - Sair do Programa");

        return scan.nextLine();
    }
    public void menu() {
        boolean continueMenu = true;
        while (continueMenu) {
            String option = opcaoMenu();
            try {
                switch (option) {
                    case Constantes.ADICIONAR_CARRO -> adicionarVeiculo(informacoesCarro());
                    case Constantes.EDITAR_CARRO -> consultaCarro();
                    case Constantes.LISTAR_CARRO -> listarPorModelo();
//                    case Constantes.REMOVER_PRODUTO -> controller.removerProduto();
//                    case Constantes.BUSCA_POR_NOME -> controller.buscarPorNome(nomeBusca());
                    case Constantes.SAIR_PROGRAMA -> {
                        continueMenu = false;
//                        controller.sairPrograma();
                        exit(0);
                    } // 6
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public VeiculoDTO informacoesCarro(){
        System.out.println("Qual a placa do veículo: ");
        String placaCarro = scan.nextLine();
        return dadosCarroEditar(placaCarro);
    }

    public void listarPorModelo(){
        System.out.println("Digite o nome ou parte dele do modelo veiculo: ");
        String modeloBuscar = scan.nextLine();
        List<Veiculo> modelosEncontrados = controller.ConsultaPorModelo(modeloBuscar);
        if (modelosEncontrados.isEmpty()){
            throw new ListaVaziaException("Não foi encontrado nenhum veículo");
        }
        modelosEncontrados.forEach(System.out::println);
    }

    public String obterPlacaEditar(){
        System.out.println("Digite a placa do carro que deseja alterar os seus dados: ");
        return scan.nextLine();
    }

    public void verificarEditarCarro (String resposta){
        switch(resposta.toLowerCase()){
            case Constantes.RESP_SIM -> editarCarro();
            case Constantes.RESP_NAO -> mensagens.voltandoMenu();
            default -> throw new EntradaInvalidaOuInsuficienteException("Entrada inválida!");
        }
    }



    public void consultaCarro(){
        controller.consultaVeiculo(obterPlacaEditar());
        confirmacaoEditarCarro();
    }


    public void confirmacaoEditarCarro(){
        System.out.println("Deseja editar esse Carro? 'Y' para sim e 'N' para nao. ");
        String resposta = scan.nextLine();
        verificarEditarCarro(resposta);
    }

    public String editarCarro(){
        String placaDoCarroParaEditar = obterPlacaEditar();
        controller.editarCarroPorPlaca(dadosCarroEditar(placaDoCarroParaEditar));
        return "Carro editado com sucesso.";

    }

    private VeiculoDTO dadosCarroEditar(String placaDoCarroParaEditar) {
        System.out.println("Qual o modelo do veiculo: ");
        String modeloCarro = scan.nextLine();
        System.out.println("Qual a potencia do veiculo: ");
        Double potenciaCarro = scan.nextDouble();
        scan.nextLine();
        System.out.println("Qual o tipo do carro? Carro, moto ou Caminhao");
        String tipoCarro = TipoVeiculo.obterTipoVeiculo(scan.nextLine());

        VeiculoDTO novoVeiculoDTO = new VeiculoDTO();
        novoVeiculoDTO.setPlaca(placaDoCarroParaEditar);
        novoVeiculoDTO.setModelo(modeloCarro);
        novoVeiculoDTO.setPotencia(potenciaCarro);
        novoVeiculoDTO.setTipo(tipoCarro);
        return novoVeiculoDTO;
    }

    public void adicionarVeiculo(VeiculoDTO novoVeiculoDTO){
        controller.adicionarVeiculo(novoVeiculoDTO);

    }


}
