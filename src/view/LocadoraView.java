package view;

import controller.LocadoraController;
import exception.EntradaInvalidaOuInsuficienteException;
import exception.ListaVaziaException;
import model.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        System.out.println("7 - Alugar Veículo");
        // System.out.println("7 - Remover um produto");
        System.out.println("8 - Devolver Veiculo - TESTE");
        System.out.println("9 - Cadastrar novo cliente (PF/PJ)");
        System.out.println("10 - Editar cliente (PF/PJ)");
        // System.out.println("8 - Devolver Veiculo - TESTE");
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
                    case Constantes.CADASTRAR_AGENCIA -> cadastrarAgencia();
                    case Constantes.ALTERAR_AGENCIA -> alterarAgencia();
                    case Constantes.BUSCAR_AGENCIA -> buscarAgencia();
                    case Constantes.ALUGAR_VEICULO -> alugarVeiculo();
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
        String placaCarro = scan.nextLine().toUpperCase();
        return dadosCarroEditar(placaCarro);
    }

    public void listarPorModelo(){
        System.out.println("Digite o nome ou parte dele do modelo veiculo: ");
        String modeloBuscar = scan.nextLine().toUpperCase();
        List<Veiculo> modelosEncontrados = controller.ConsultaPorModelo(modeloBuscar);
        if (modelosEncontrados.isEmpty()){
            throw new ListaVaziaException("Não foi encontrado nenhum veículo");
        }
        modelosEncontrados.forEach(System.out::println);
    }

    private void cadastrarAgencia() {
        System.out.println("Digite o nome da agência: ");
        String nomeAgencia = scan.nextLine().toUpperCase();
        System.out.println("Digite o endereço da agência: ");
        String enderecoAgencia = scan.nextLine().toUpperCase();
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
                        String nomeAgencia = scan.nextLine().toUpperCase();
                        String paramsQuery = "UPDATE|nome|" + nomeAgencia + "|" + idAgencia;
                        controller.editarAgencia(paramsQuery);
                        loop = false;
                    }
                    case "2" -> {
                        System.out.println("Digite o novo logradouro :");
                        String logradouro = scan.nextLine().toUpperCase();
                        String paramsQuery = "UPDATE|logradouro|" + logradouro + "|" + idAgencia;
                        controller.editarAgencia(paramsQuery);
                        loop = false;
                    }
                    case "3" -> {
                        System.out.println("Digite o novo nome:");
                        String nomeAgencia = scan.nextLine().toUpperCase();
                        System.out.println("Digite o novo logradouro :");
                        String logradouro = scan.nextLine().toUpperCase();
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

        System.out.println("Insira a opção desejada:");
        System.out.println("1. Consultar agências por nome");
        System.out.println("2. Consultar agências por logradouro");
        System.out.println("3. Listar todas as agências");

        boolean loop = true;
        while (loop) {
            String choice = scan.nextLine();
            switch (choice) {
                case "1" -> {
                    System.out.println("Digite o novo nome:");
                    String nomeAgencia = scan.nextLine().toUpperCase();
                    String paramsQuery = "nome|" + nomeAgencia;
                    controller.consultarAgencia(paramsQuery);
                    loop = false;
                }
                case "2" -> {
                    System.out.println("Digite o logradouro :");
                    String logradouro = scan.nextLine().toUpperCase();
                    String paramsQuery = "logradouro|" + logradouro;
                    controller.consultarAgencia(paramsQuery);
                    loop = false;
                }
                case "3" -> {
                    controller.consultarAgencia("");
                    loop = false;
                }
                default -> System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Digite opção válida" + ConsoleColors.RESET);
            }
        }
    }

    public String obterPlacaEditar(){
        System.out.println("Digite a placa do carro que deseja alterar os seus dados: ");
        return scan.nextLine().toUpperCase();
    }

    public void verificarEditarCarro (String resposta){
        switch(resposta.toUpperCase()){
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
        String resposta = scan.nextLine().toUpperCase();
        verificarEditarCarro(resposta);
    }

    public String editarCarro(){
        String placaDoCarroParaEditar = obterPlacaEditar();
        controller.editarCarroPorPlaca(dadosCarroEditar(placaDoCarroParaEditar));
        return "Carro editado com sucesso.";

    }

    private VeiculoDTO dadosCarroEditar(String placaDoCarroParaEditar) {
        System.out.println("Qual o modelo do veiculo: ");
        String modeloCarro = scan.nextLine().toUpperCase();
        System.out.println("Qual a potencia do veiculo: ");
        Double potenciaCarro = scan.nextDouble();
        scan.nextLine();
        System.out.println("Qual o tipo do carro? Carro, Moto ou Caminhao");
        String tipoCarro = TipoVeiculo.obterTipoVeiculo(scan.nextLine().toUpperCase());

        VeiculoDTO novoVeiculoDTO = new VeiculoDTO();
        novoVeiculoDTO.setPlaca(placaDoCarroParaEditar);
        novoVeiculoDTO.setModelo(modeloCarro);
        novoVeiculoDTO.setPotencia(potenciaCarro);
        novoVeiculoDTO.setTipo(tipoCarro);
        novoVeiculoDTO.setDisponivel(true);
        return novoVeiculoDTO;
    }

    public void adicionarVeiculo(VeiculoDTO novoVeiculoDTO){
        controller.adicionarVeiculo(novoVeiculoDTO);

    }

    //Método de teste - quando as outras classes foram implementadas irá ter mudança, mas o metodo tá calculando certo
    //e pegando os valores corretos.
    public void devolverVeiculo(){
        System.out.println("Qual o tipo de Cliente? Digite 'PJ' para Cliente Juridico e 'PF' para Cliente Fisico");
        String tipoCliente = scan.nextLine().toUpperCase();
        System.out.println("Qual o nome do cliente? ");
        String nomeCliente = scan.nextLine().toUpperCase();
        System.out.println("Quantos dias ficou com o veiculo? ");
        int diasVeiculo = scan.nextInt();
        scan.nextLine();
        System.out.println("Digite a placa do veiculo que você alugou? ");
        String placaVeiculo = scan.nextLine().toUpperCase();
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

    public String obterDocumentoEditar(){
        System.out.println("Digite o documento do cliente que deseja alterar os seus dados: ");
        return scan.nextLine();
    }

    public void verificarEditarCliente (String resposta){
        switch(resposta.toUpperCase()){
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
        String resposta = scan.nextLine().toUpperCase();
        verificarEditarCliente(resposta);
    }

    public String editarCliente(){
        String documentoDoClienteParaEditar = obterDocumentoEditar();
        controller.editarClientePorDocumento(dadosClienteEditar(documentoDoClienteParaEditar));
        return "Cliente editado com sucesso.";

    }

    private ClienteDTO dadosClienteEditar(String documentoDoClienteParaEditar) {
        System.out.println("Qual o nome do cliente: ");
        String nomeCliente = scan.nextLine().toUpperCase();
        System.out.println("Qual o telefone do cliente: ");
        String telefoneCliente = scan.nextLine();
        System.out.println("Qual o tipo de cliente? PF ou PJ");
        String tipoCliente = TipoCliente.obterTipoCliente(scan.nextLine().toUpperCase());

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

    //////////////Inicio Aluguel
    public void alugarVeiculo(){
        Cliente clieteParaAlugar = pegarCliente();
        if (clieteParaAlugar == null){
            System.out.println("Cliente não encontrado");
            return;
        }

        VeiculoDTO veiculoParaAluguel = escolherVeiculo();
        if(veiculoParaAluguel == null){
            System.out.println("Veículo não encontrado");
            return;
        }

        if(!verificarVeiculoDisponivel(veiculoParaAluguel)){
            System.out.println("Veículo alugado no momento. Tente com outro veículo.");
            return;
        }

        //Mon Jan 16 00:00:00 BRT 2023
        System.out.println("Para o início da locação.");
        Date dataInicio = escolherData();

        System.out.println("Para a devolução do veículo.");
        Date dataDevolucao = escolherData();
        try{
            if(!validacaoDatasLocacao(dataInicio, dataDevolucao)){
                System.out.println("Informe datas válidas");
                return;
            }
            //escolherHorarioLocacao(dataInicio);
        }catch (Exception e){
            System.out.println("Datas inválidas");
        }

    }

    public boolean validacaoDatasLocacao(Date dataInicio, Date dataDevolucao){
        Date dataAtual = new Date();
        if(dataInicio.after(dataDevolucao)){
            return false;
        }
        if(dataInicio.before(dataAtual)){
            return false;
        }
        return true;
    }

    public boolean verificarVeiculoDisponivel(VeiculoDTO veiculoParaVerificacao){
        return true;
    }

    public void escolherHorarioLocacao(Date dataInicioLocacao){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String dataLocacao = dataInicioLocacao.toString();
        System.out.println("Informe o horário para locação no formato HH:mm:ss");
        String horarioUsuario = scan.nextLine();
        try {
            Date horarioLocacao;
            horarioLocacao = (Date) dataInicioLocacao.clone();
            //horarioLocacao = Calendar.getInstance().getTime();
            //horarioLocacao.setTime(sdf.parse(horarioUsuario));
            horarioLocacao = sdf.parse(horarioUsuario);
            System.out.println(horarioLocacao);
        } catch (ParseException e) {
            System.out.println("Informe um horário válido");
            return;
        }
    }

    public Date escolherData(){
        System.out.println("Informe a data desejada no formato dd/MM/yyyy.");
        String data = scan.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dataLocacao = sdf.parse(data);
            return dataLocacao;
        } catch (ParseException e) {
            System.out.println("Digite um formato de data válido.");
            return null;
        }
    }

    public Cliente pegarCliente(){

        System.out.println("Digite o documento de quem vai alugar o veículo (apenas números):");
        try {
            Integer documento = Integer.parseInt(scan.nextLine());
            String documentoParaBusca = documento.toString();
            //controller.consultarClientePorId(documentoParaBusca);
            Cliente clienteTemporario = new ClienteFisico("123456");
            return clienteTemporario;
        }catch (IllegalArgumentException e){
            System.out.println("Informe apenas números");
            return null;
        }
    }

    public VeiculoDTO escolherVeiculo(){
        try{
            listarPorModelo();
            System.out.println("Informe a placa do veículo que deseja alugar.");
            return controller.obterVeiculoPorPlaca(scan.nextLine().toUpperCase());
        }catch (ListaVaziaException e){
            System.out.println("Não possuímos esse veículo em nosso estoque.");
            return null;
        }
    }

    //////////////Fim Aluguel
}
