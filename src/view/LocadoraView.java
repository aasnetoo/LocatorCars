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
        System.out.println("5 - Devolver Veiculo - TESTE");
        System.out.println("7 - Cadastrar novo cliente (PF/PJ)");
        System.out.println("8 - Editar cliente (PF/PJ)");
        System.out.println("14 - Sair do Programa");

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
                    case Constantes.DEVOLVER_VEICULO -> devolverVeiculo();
                    case Constantes.CADASTRAR_CLIENTE -> adicionarCliente(informacoesCliente());
                    case Constantes.EDITAR_CLIENTE -> consultaCliente();
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


    /////// clientes
    public ClienteDTO informacoesCliente(){
        System.out.println("Qual é o numero do documento do cliente (apenas números): ");
        String documentoCliente = scan.nextLine();
        return dadosClienteEditar(documentoCliente);
    }

//    public void listarPorNomeCliente(){
//        System.out.println("Digite o nome completo do cliente ou parte dele: ");
//        String nomeClienteBuscar = scan.nextLine();
//        List<Cliente> clientesEncontrados = controller.ConsultaPorModelo(nomeClienteBuscar);
//        if (modelosEncontrados.isEmpty()){
//            throw new ListaVaziaException("Não foi encontrado nenhum veículo");
//        }
//        modelosEncontrados.forEach(System.out::println);
//    }

    public String obterDocumentoEditar(){
        System.out.println("Digite o documento do cliente que deseja alterar os seus dados: ");
        return scan.nextLine();
    }

    public void verificarEditarCliente (String resposta){
        switch(resposta.toLowerCase()){
            case Constantes.RESP_SIM -> editarCliente();
            case Constantes.RESP_NAO -> mensagens.voltandoMenu();
            default -> throw new EntradaInvalidaOuInsuficienteException("Entrada inválida!");
        }
    }

    public void consultaCliente(){
        controller.consultarCliente(obterDocumentoEditar());
        confirmacaoEditarCliente();
    }


    public void confirmacaoEditarCliente(){
        System.out.println("Deseja editar este cliente? 'Y' para sim e 'N' para nao. ");
        String resposta = scan.nextLine();
        verificarEditarCliente(resposta);
    }

    public String editarCliente(){
        String documentoDoClienteParaEditar = obterDocumentoEditar();
        controller.editarClientePorDocumento(dadosClienteEditar(documentoDoClienteParaEditar));
        return "Cliente editado com sucesso.";

    }

    private ClienteDTO dadosClienteEditar(String documentoDoClienteParaEditar) {
        System.out.println("Qual o nome do cliente: ");
        String nomeCliente = scan.nextLine();
        System.out.println("Qual o telefone do cliente: ");
        String telefoneCliente = scan.nextLine();
        System.out.println("Qual o tipo de cliente? PF ou PJ");
        String tipoCliente = TipoCliente.obterTipoCliente(scan.nextLine());

        ClienteDTO novoClienteDTO = new ClienteDTO();
        novoClienteDTO.setDocumento(documentoDoClienteParaEditar);
        novoClienteDTO.setNome(nomeCliente);
        novoClienteDTO.setTelefone(telefoneCliente);
        novoClienteDTO.setTipoCliente(tipoCliente);
        return novoClienteDTO;
    }

    public void adicionarCliente(ClienteDTO novoClienteDTO){
        controller.adicionarCliente(novoClienteDTO);

    }

    /////// fim clientes
}
