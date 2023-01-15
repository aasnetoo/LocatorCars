package view;

import controller.LocadoraController;
import exception.EntradaInvalidaOuInsuficienteException;
import exception.ListaVaziaException;
import model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import util.ConsoleColors;
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
        System.out.println("4 - Cadastrar Agência");
        System.out.println("5 - Alterar Agência");
        System.out.println("6 - Buscar Agência");
        System.out.println("7 - Remover um produto");
        // System.out.println("8 - Devolver Veiculo - TESTE");
        System.out.println("8 - Sair do Programa");

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
                    case Constantes.CADASTRAR_AGENCIA -> cadastrarAgencia();
                    case Constantes.ALTERAR_AGENCIA -> alterarAgencia();
                    case Constantes.BUSCAR_AGENCIA -> buscarAgencia();
//                    case Constantes.REMOVER_PRODUTO -> controller.removerProduto();
                    case Constantes.DEVOLVER_VEICULO -> devolverVeiculo();
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

    private void cadastrarAgencia() {
        System.out.println("Digite o nome da agência: ");
        String nomeAgencia = scan.nextLine();
        System.out.println("Digite o endereço da agência: ");
        String enderecoAgencia = scan.nextLine();
        AgenciaDTO agenciaDTO = new AgenciaDTO(nomeAgencia, enderecoAgencia);
        controller.adicionarAgencia(agenciaDTO);
        System.out.println("Agencia cadastrada com sucesso!");
    }

    private void alterarAgencia() {

        // Verifica se existe alguma agência cadastrada no banco de dados
        boolean existeAgencia = controller.consultarAgencia("");

        if (existeAgencia) {
            int idAgencia;
            boolean idExiste = false;
            do {
                System.out.println("Por favor digite o ID da Agência que gostaria de alterar: ");
                while (!scan.hasNextDouble()) {
                    System.out.print("Por favor digite um ID válido ");
                    scan.next();
                }
                idAgencia = scan.nextInt();
                if(idAgencia < 0) System.out.println("Por Favor digite um ID válido");
                idExiste = controller.verificaExistenciaAgenciaPorId(idAgencia);
                if (idAgencia > 0 && !idExiste) System.out.println("Não foi encontrado o ID inserido, tente novamente.");
            } while (idAgencia < 0 || !idExiste);

            scan.nextLine();

            System.out.println("Deseja alterar ou deletar a agência selecionada?");
            System.out.println("1 - Alterar o nome");
            System.out.println("2 - Alterar o logradouro");
            System.out.println("3 - Alterar ambos");
            System.out.println("4 - Deletar a agência");

            boolean loop = true;
            while (loop) {
                String choice = scan.nextLine();
                switch (choice) {
                    case "1" -> {
                        System.out.println("Digite o novo nome:");
                        String nomeAgencia = scan.nextLine();
                        String paramsQuery = "UPDATE|nome|" + nomeAgencia + "|" + idAgencia;
                        controller.editarAgencia(paramsQuery);
                        loop = false;
                    }
                    case "2" -> {
                        System.out.println("Digite o novo logradouro :");
                        String logradouro = scan.nextLine();
                        String paramsQuery = "UPDATE|logradouro|" + logradouro + "|" + idAgencia;
                        controller.editarAgencia(paramsQuery);
                        loop = false;
                    }
                    case "3" -> {
                        System.out.println("Digite o novo nome:");
                        String nomeAgencia = scan.nextLine();
                        System.out.println("Digite o novo logradouro :");
                        String logradouro = scan.nextLine();
                        String paramsQuery = "UPDATE|nome;logradouro|" + nomeAgencia + ";" + logradouro + "|" + idAgencia;
                        controller.editarAgencia(paramsQuery);
                        loop = false;
                    }
                    case "4" -> {
                        controller.deletarAgencia(String.valueOf(idAgencia));
                        loop = false;
                    }
                    default -> System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Digite opção válida" + ConsoleColors.RESET);
                }
            }
        }
    }

    private void buscarAgencia() {
        controller.consultarAgencia("");
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

    //Método de teste - quando as outras classes foram implementadas irá ter mudança, mas o metodo tá calculando certo
    //e pegando os valores corretos.
    public void devolverVeiculo(){
        System.out.println("Qual o tipo de Cliente? Digite 'J' para Cliente Juridico e 'F' para Cliente Fisico");
        String tipoCliente = scan.nextLine();
        System.out.println("Qual o nome do cliente? ");
        String nomeCliente = scan.nextLine();
        System.out.println("Quantos dias ficou com o veiculo? ");
        int diasVeiculo = scan.nextInt();
        scan.nextLine();
        System.out.println("Digite a placa do veiculo que você alugou? ");
        String placaVeiculo = scan.nextLine();
        VeiculoDTO veiculoADevolver = controller.obterVeiculoPorPlaca(placaVeiculo);
        double valorTotal = controller.valorDevolucao(nomeCliente,veiculoADevolver,diasVeiculo,tipoCliente);
        System.out.println(valorTotal);
    }


}
