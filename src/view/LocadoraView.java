package view;

import controller.LocadoraController;
import exception.EntradaInvalidaOuInsuficienteException;
import exception.ListaVaziaException;
import model.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
        System.out.println("8 - Devolver Veiculo - TESTE");
        System.out.println("9 - Cadastrar novo cliente");
        System.out.println("10 - Editar cliente");
        System.out.println("11 - Listar todos os Veiculos Disponiveis");
        System.out.println("14 - Sair do Programa");

        return scan.nextLine();
    }
    public void menu() {
        boolean continueMenu = true;
        while (continueMenu) {
            String option = opcaoMenu();
            try {
                switch (option) {
                    case Constantes.ADICIONAR_CARRO -> adicionarVeiculo(informacoesVeiculo());
                    case Constantes.EDITAR_CARRO -> consultaVeiculo();
                    case Constantes.LISTAR_CARRO -> listarPorModelo();
                    case Constantes.CADASTRAR_AGENCIA -> cadastrarAgencia();
                    case Constantes.ALTERAR_AGENCIA -> alterarAgencia();
                    case Constantes.BUSCAR_AGENCIA -> buscarAgencia();
                    case Constantes.ALUGAR_VEICULO -> alugarVeiculo();
                    case Constantes.DEVOLVER_VEICULO -> devolverVeiculo();
                    case Constantes.CADASTRAR_CLIENTE -> adicionarCliente();
                    case Constantes.EDITAR_CLIENTE -> consultaCliente();
                    case Constantes.LISTA_VEICULOS_DISPONIVEIS -> listarVeiculosDisponiveisParaAluguel();
                    case Constantes.EMITIR_COMPROVANTE -> emitirComprovanteAluguel();
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

    public VeiculoDTO informacoesVeiculo(){
        System.out.println("Qual a placa do veículo: ");
        String placaCarro = scan.nextLine().toUpperCase();
        return dadosVeiculoEditar(placaCarro);
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
        boolean loop = true;
        while (loop) {
            System.out.println("Digite o nome da agência: ");
            String nomeAgencia = scan.nextLine().toUpperCase();
            System.out.println("Digite o endereço da agência: ");
            String enderecoAgencia = scan.nextLine().toUpperCase();

            Agencia agencia = new Agencia(nomeAgencia, enderecoAgencia);
            boolean checkAgencia = controller.consultarAgencia("SEARCH|nome;logradouro|" + nomeAgencia + ";" + enderecoAgencia, false);

            if (!checkAgencia) {
                controller.adicionarAgencia(agencia);
                loop = false;
            } else {
                System.out.println("Nome ou Logradouro já cadastrados, favor inserir um novo.");
            }

        }
        System.out.println("Agencia cadastrada com sucesso!");
    }

    private void alterarAgencia() {

        // Verifica se existe alguma agência cadastrada no banco de dados
        boolean existeAgencia = controller.consultarAgencia("", true);

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
                    case Constantes.ALTERAR_NOME_AGENCIA-> {
                        System.out.println("Digite o novo nome:");
                        String nomeAgencia = scan.nextLine().toUpperCase();
                        boolean checkNomeAgencia = controller.consultarAgencia("SEARCH|nome|" + nomeAgencia, false);
                        if (!checkNomeAgencia) {
                            String paramsQuery = "UPDATE|nome|" + nomeAgencia + "|" + idAgencia;
                            controller.editarAgencia(paramsQuery);
                            loop = false;
                        } else {
                            System.out.println("Nome já cadastrado, favor escolher outro.");
                        }
                    }
                    case Constantes.ALTERAR_LOGRADOURO_AGENCIA -> {
                        System.out.println("Digite o novo logradouro :");
                        String logradouro = scan.nextLine().toUpperCase();

                        boolean checkLogradouroAgencia = controller.consultarAgencia("SEARCH|logradouro|" + logradouro, false);
                        if (!checkLogradouroAgencia) {
                            String paramsQuery = "UPDATE|logradouro|" + logradouro + "|" + idAgencia;
                            controller.editarAgencia(paramsQuery);
                            loop = false;
                        } else {
                            System.out.println("Logradouro já cadastrado, favor escolher outro.");
                        }
                    }
                    case Constantes.ALTERAR_NOME_E_LOGRADOURO_AGENCIA -> {
                        System.out.println("Digite o novo nome:");
                        String nomeAgencia = scan.nextLine().toUpperCase();
                        System.out.println("Digite o novo logradouro :");
                        String logradouro = scan.nextLine().toUpperCase();
                        String paramsQuery = "UPDATE|nome;logradouro|" + nomeAgencia + ";" + logradouro + "|" + idAgencia;
                        controller.editarAgencia(paramsQuery);
                        loop = false;
                    }
                    case Constantes.DELETAR_AGENCIA -> {
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
                case Constantes.CONSULTAR_AGENCIA_POR_NOME -> {
                    System.out.println("Digite o nome:");
                    String nomeAgencia = scan.nextLine().toUpperCase();
                    String paramsQuery = "ILIKE|nome|" + nomeAgencia;
                    controller.consultarAgencia(paramsQuery, true);
                    loop = false;
                }
                case Constantes.CONSULTAR_AGENCIA_POR_LOGRADOURO-> {
                    System.out.println("Digite o logradouro :");
                    String logradouro = scan.nextLine().toUpperCase();
                    String paramsQuery = "ILIKE|logradouro|" + logradouro;
                    controller.consultarAgencia(paramsQuery, true);
                    loop = false;
                }
                case Constantes.LISTAR_TODAS_AGENCIAS -> {
                    controller.consultarAgencia("", true);
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

    public void verificarEditarVeiculo (String resposta){
        switch(resposta.toUpperCase()){
            case Constantes.RESP_SIM -> editarVeiculo();
            case Constantes.RESP_NAO -> mensagens.voltandoMenu();
            default -> throw new EntradaInvalidaOuInsuficienteException("Entrada inválida!");
        }
    }


    public void consultaVeiculo(){
        controller.consultaVeiculo(obterPlacaEditar());
        confirmacaoEditarVeiculo();
    }

    public void confirmacaoEditarVeiculo(){
        System.out.println("Deseja editar esse Carro? 'Y' para sim e 'N' para nao. ");
        String resposta = scan.nextLine().toUpperCase();
        verificarEditarVeiculo(resposta);
    }

    public String editarVeiculo(){
        String placaDoCarroParaEditar = obterPlacaEditar();
        controller.editarCarroPorPlaca(dadosVeiculoEditar(placaDoCarroParaEditar));
        return "Carro editado com sucesso.";

    }

    private VeiculoDTO dadosVeiculoEditar(String placaDoCarroParaEditar) {
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

    //Método de teste — quando as outras classes foram implementadas irá ter mudança, mas o metodo tá calculando certo
    //e pegando os valores corretos.
    public void devolverVeiculo(){
        System.out.println("Digite o tipo do Cliente? PF ou PJ ");
        String tipoCliente = scan.nextLine();
        System.out.println("Digite o nome do cliente: ");
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


    public void listarVeiculosDisponiveisParaAluguel(){
        controller.veiculosDisponiveisParaAluguel().forEach(System.out::println);
    /////// clientes
    }

     public Cliente retornarCliente() {
//        Cliente cliente = controller.retornarCliente(obterDocumentoEditar());
//        System.out.println(cliente.getNome());
        return controller.retornarCliente(obterDocumentoEditar());
    }

    public void adicionarCliente(){
        Cliente novoCliente = dadosClienteAdicionarOuEditar(obterDocumentoEditar());
        controller.adicionarCliente(novoCliente);
    }

    private Cliente dadosClienteAdicionarOuEditar(String documentoDoClienteParaEditar) {
        System.out.println("Qual o nome do cliente: ");
        String nomeCliente = scan.nextLine().toUpperCase();
        System.out.println("Qual o telefone do cliente: ");
        String telefoneCliente = scan.nextLine();
        System.out.println("Qual o tipo de cliente? PF ou PJ");
        String tipoCliente = TipoCliente.obterTipoCliente(scan.nextLine().toUpperCase());

        Cliente novoCliente = new Cliente();
        novoCliente.setDocumento(documentoDoClienteParaEditar);
        novoCliente.setNome(nomeCliente);
        novoCliente.setTelefone(telefoneCliente);
        novoCliente.setTipoCliente(tipoCliente);
        return novoCliente;
    }

    private void editarDadosCliente () {

        String documentoDoClienteParaEditar = obterDocumentoEditar();

        System.out.println("Digite o número da informação deseja editar: ");
        System.out.println("1. Nome");
        System.out.println("2. Telefone");
        System.out.println("3. Tipo Cliente");

        Cliente clienteEditar = controller.retornarCliente(documentoDoClienteParaEditar);

        boolean loop = true;
        while (loop) {
            String choice = scan.nextLine();
            switch (choice) {
                case Constantes.ALTERAR_NOME_CLIENTE -> {
                    System.out.println("Digite o nome:");
                    String novoNome = scan.nextLine().toUpperCase();;
                    clienteEditar.setNome(novoNome);
                    loop = false;
                }
                case Constantes.ALTERAR_TELEFONE_CLIENTE-> {
                    System.out.println("Digite o telefone :");
                    String novoTelefone = scan.nextLine().toUpperCase();
                    clienteEditar.setTelefone(novoTelefone);
                    loop = false;
                }
                case Constantes.ALTERAR_TIPO_CLIENTE -> {
                    System.out.println("Digite o tipo (PF/PJ) :");
                    String novoTipo = TipoCliente.obterTipoCliente(scan.nextLine().toUpperCase());
                    clienteEditar.setTipoCliente(novoTipo);
                    loop = false;
                }
                default -> System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Digite opção válida" + ConsoleColors.RESET);
            }

        }
        controller.editarClientePorDocumento(clienteEditar);
        System.out.println("Cliente editado com sucesso.");
    }

    public void consultaCliente(){
        controller.consultarCliente(obterDocumentoEditar());
        confirmacaoEditarCliente();
    }

    public String obterDocumentoEditar(){

        boolean entradaValida = false;

        String documentoParaBuscar = null;

        System.out.println("Digite o documento do cliente (apenas números): ");

        while (!entradaValida) {
            try {
                int documentoCliente = Integer.parseInt(scan.nextLine());
                documentoParaBuscar = Integer.toString(documentoCliente);
                entradaValida = true;
            }catch (IllegalArgumentException e){
                System.out.println("Informe apenas números");
                entradaValida = false;
                documentoParaBuscar = "Docuemento invalido";
            }
        }
        return documentoParaBuscar;
    }

    public void verificarEditarCliente (String resposta){
        switch(resposta.toUpperCase()){
            case Constantes.RESP_SIM -> editarDadosCliente();
            case Constantes.RESP_NAO -> mensagens.voltandoMenu();
            default -> throw new EntradaInvalidaOuInsuficienteException("Entrada inválida!");
        }
    }

    public void confirmacaoEditarCliente(){
        System.out.println("Deseja editar este cliente? 'Y' para sim e 'N' para nao. ");
        String resposta = scan.nextLine().toUpperCase();
        verificarEditarCliente(resposta);
    }


    //////////////Inicio Aluguel
    public void alugarVeiculo(){
        Aluguel aluguel = new Aluguel();

        Cliente clienteParaAlugar = pegarCliente();
        if (clienteParaAlugar == null) {
            System.out.println("Cliente não encontrado");
            return;
        }

        listarVeiculosDisponiveisParaAluguel();

        VeiculoDTO veiculoParaAluguel = escolherVeiculo();
        if(veiculoParaAluguel == null){
            System.out.println("Veículo não encontrado");
            return;
        }

        System.out.println("Para o início da locação.");
        Date dataInicio = escolherData();

        System.out.println("Para a devolução do veículo.");
        Date dataDevolucao = escolherData();

        LocalTime horarioLocacao = escolherHorarioLocacao();
        if(horarioLocacao == null)
            return;

        try{
            if(!validacaoDatasLocacao(dataInicio, dataDevolucao)){
                System.out.println("Informe datas válidas");
                return;
            }
        }catch (Exception e){
            System.out.println("Datas inválidas");
        }

        Agencia agenciaAluguel = escolherAgencia();

        //controller.valorDevolucao();
        //listarAgencias();

        Agencia agenciaDevolucao = escolherAgencia();

        aluguel.setVeiculo(veiculoParaAluguel); //pendente validação
        aluguel.setAgenciaRetirada(agenciaAluguel); //pendente
        aluguel.setAgenciaDevolucao(agenciaDevolucao); //pendente
        aluguel.setCliente(clienteParaAlugar); //ok
        aluguel.setDataInicio(dataInicio); //ok
        aluguel.setDataDevolucao(dataDevolucao); //ok
        aluguel.setHorarioAgendado(Time.valueOf(horarioLocacao)); //pendente
        aluguel.setValorAluguel(BigDecimal.valueOf(350)); //pendente

        /*void mudarStatusVeiculoIndisponivel(String placa){

        }*/

        controller.salvarAluguel(aluguel);
    }

    public Agencia escolherAgencia(){
        // Faz o print das agências disponíveis
        controller.consultarAgencia("", true);
        System.out.println("Informe o nome da Agência que deseja pegar o carro: ");
        String nomeAgencia = scan.nextLine();
        boolean loop = true;
        while(loop) {
            Agencia agencia = controller.consultarAgenciaPorNome(nomeAgencia);
            if (Objects.equals(agencia.getNome(), "")) {
                System.out.println("Agência não encontrada, favor digitar outra.");
                nomeAgencia = scan.nextLine();
            } else {
                loop = false;
                return agencia;
            }
        }
        return new Agencia("");
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

    public LocalTime escolherHorarioLocacao(){
        System.out.println("Informe o horário para locação no formato HH:mm:ss");
        String hora = scan.nextLine();
        try {
        LocalTime horarioAluguel = LocalTime.parse(hora);
        System.out.println(horarioAluguel);
            return horarioAluguel;
        } catch (Exception e) {
            System.out.println("Formato de hora inválido");
            return null;
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
        try {
            Cliente clienteAluguel = retornarCliente();
            return clienteAluguel;
        }catch (IllegalArgumentException e){
            System.out.println("Informe apenas números");
            return null;
        }
    }

    public VeiculoDTO escolherVeiculo(){
        try{
            System.out.println("Informe a placa do veículo que deseja alugar.");
            return controller.obterVeiculoPorPlaca(scan.nextLine().toUpperCase());
        }catch (ListaVaziaException e){
            System.out.println("Não possuímos esse veículo em nosso estoque.");
            return null;
        }
    }

    private void emitirComprovanteAluguel() {
        System.out.println("Informe o número da sua reserva: ");
        try {
            int numeroReserva = Integer.parseInt(scan.nextLine());
            Aluguel aluguel = controller.buscarAluguelPorId(numeroReserva);
            if (aluguel == null){
                System.out.println("Contrato não encontrado.");
            }else{
                System.out.println(aluguel);
            }
        }catch (Exception e){
            System.out.println("Entrada inválida");
        }
    }

    //////////////Fim Aluguel
}


