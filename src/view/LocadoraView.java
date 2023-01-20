package view;


import controller.LocadoraController;
import exception.EntradaInvalidaOuInsuficienteException;
import exception.ListaVaziaException;
import exception.PaginacaoException;
import model.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Time;
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
        System.out.println("1 - Cadastrar veículo");
        System.out.println("2 - Editar veículo");
        System.out.println("3 - Buscar veículo");
        System.out.println("4 - Cadastrar agência");
        System.out.println("5 - Alterar agência");
        System.out.println("6 - Buscar agência");
        System.out.println("7 - Cadastrar cliente");
        System.out.println("8 - Editar cliente");
        System.out.println("9 - Alugar veículo");
        System.out.println("10 - Devolver veículo");
        System.out.println("11 - Paginacao Veiculos");
        System.out.println("12 - Paginacao Clientes");
        System.out.println("13 - Paginacao Agencias");
        System.out.println("15 - Sair do programa");


        System.out.println("98 - Teste - Listar todos os veiculos disponiveis");
        System.out.println("99 - Teste - Emitir comprovante");

        return scan.nextLine();
    }
    public void menu() {
        boolean continueMenu = true;
        while (continueMenu) {
            String option = opcaoMenu();
            try {
                switch (option) {
                    case Constantes.CADASTRAR_VEICULO -> adicionarVeiculo(informacoesVeiculo());
                    case Constantes.EDITAR_VEICULO -> consultaVeiculo();
                    case Constantes.LISTAR_VEICULO -> listarPorModelo();
                    case Constantes.CADASTRAR_AGENCIA -> cadastrarAgencia();
                    case Constantes.ALTERAR_AGENCIA -> alterarAgencia();
                    case Constantes.BUSCAR_AGENCIA -> buscarAgencia();
                    case Constantes.CADASTRAR_CLIENTE -> adicionarCliente();
                    case Constantes.EDITAR_CLIENTE -> consultaCliente();
                    case Constantes.ALUGAR_VEICULO -> alugarVeiculo();
                    case Constantes.DEVOLVER_VEICULO -> devolverVeiculo();
                    case Constantes.SAIR_PROGRAMA -> {
                        continueMenu = false;
//                        controller.sairPrograma();
                        exit(0);
                    } // 6
                    case Constantes.PAGINACAO_VEICULOS -> paginacaoVeiculos();
                    case Constantes.PAGINACAO_CLIENTES -> paginacaoClientes();
                    case Constantes.LISTA_VEICULOS_DISPONIVEIS -> listarVeiculosDisponiveisParaAluguel();
                    case Constantes.EMITIR_COMPROVANTE -> emitirComprovanteAluguel();
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
        System.out.println("Deseja editar esse Veiculo? 'Y' para sim e 'N' para nao. ");
        String resposta = scan.nextLine().toUpperCase();
        verificarEditarVeiculo(resposta);
    }

    public String editarVeiculo(){
        String placaDoCarroParaEditar = obterPlacaEditar();
        controller.editarVeiculoPorPlaca(dadosVeiculoEditar(placaDoCarroParaEditar));
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
//        Cliente cliente = retornarCliente();
        System.out.println("Digite a placa do veiculo que você alugou? ");
        String placaVeiculo = scan.nextLine();


        VeiculoDTO veiculoADevolver = controller.obterVeiculoPorPlaca(placaVeiculo);


        controller.atualizarDisponibilidadeVeiculo(placaVeiculo, "true");

        //TODO: imprimir recibo com dados da tabela de alugueis // talvez pegar por placa e disponivel = false
    }


    public void listarVeiculosDisponiveisParaAluguel(){
        controller.veiculosDisponiveisParaAluguel().forEach(System.out::println);
    }
    public void paginacaoVeiculos(){
        boolean continueLoop = true;
        int pagina = Constantes.PAGINA_INICIAL;
        while (continueLoop){
            if (controller.paginacaoVeiculos(pagina).isEmpty()){
                throw new ListaVaziaException("Não há mais veiculos a serem exibidos. Voltando ao menu...");
            }
            System.out.println("------------------------------------PAGINA "+pagina+"--------------------------------");
            System.out.format("%12s %10s %10s %10s %10s", "PLACA", "MODELO", "POTENCIA", "TIPO", "DISPONIBILIDADE\n");
            for (int i = 0; i < controller.paginacaoVeiculos(pagina).size(); i++) {
                System.out.format("%12s %10s %10s %10s %10s", ""+controller.paginacaoVeiculos(pagina).get(i).getPlaca()+"", ""
                        +controller.paginacaoVeiculos(pagina).get(i).getModelo()+"", ""
                        +controller.paginacaoVeiculos(pagina).get(i).getPotencia()+"", ""
                        +controller.paginacaoVeiculos(pagina).get(i).getTipo()+"", ""
                        +controller.paginacaoVeiculos(pagina).get(i).verificaDisponibilidade()+"\n");
            }
            System.out.println("O que deseja fazer? Digite 1 para ir para próxima página; 2 para voltar de página e 3 para voltar ao menu.");

            switch(scan.nextLine()){
                case Constantes.RESP_PROXIMA_PAGINA -> pagina++;
                case Constantes.RESP_PAGINA_ANTERIOR -> {
                    if (pagina == Constantes.PAGINA_INICIAL) {
                        System.out.println("Você já está na página 1!");
                    }
                    else{
                        pagina = pagina - Constantes.PAGINA_INICIAL;
                    }
                }
                case Constantes.RESP_VOLTAR_MENU -> continueLoop = false;
                default -> System.out.println("Opção incorreta, digite uma opção válida.");
            }
        }
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
                    String novoNome = scan.nextLine().toUpperCase();
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

        Long documentoCliente = 0L;

        System.out.println("Digite o documento do cliente (apenas números): ");

        while (!entradaValida) {
            try {
                documentoCliente = Long.parseLong(scan.nextLine());
                entradaValida = true;
            } catch (IllegalArgumentException e){
                System.out.println("Informe apenas números");
                entradaValida = false;
            }
        }
        return String.valueOf(documentoCliente);
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

    public void paginacaoClientes(){
        boolean continueLoop = true;
        int pagina = Constantes.PAGINA_INICIAL;
        while (continueLoop){
            if (controller.paginacaoClientes(pagina).isEmpty()){
                throw new ListaVaziaException("Não há mais Clientes a serem exibidos. Voltando ao menu...");
            }
            System.out.println("------------------------------------PAGINA "+pagina+"--------------------------------");
            System.out.format("%14s %12s %12s %10s", "NOME", "TELEFONE", "DOCUMENTO", "TIPO DO CLIENTE\n");
            for (int i = 0; i < controller.paginacaoClientes(pagina).size(); i++) {
                System.out.format("%14s %12s %12s %12s", ""+controller.paginacaoClientes(pagina).get(i).getNome()+"", ""
                        +controller.paginacaoClientes(pagina).get(i).getTelefone()+"", ""
                        +controller.paginacaoClientes(pagina).get(i).getDocumento()+"", ""
                        +controller.paginacaoClientes(pagina).get(i).getTipoCliente()+"\n");
            }

            System.out.println("O que deseja fazer? Digite 1 para ir para próxima página; 2 para voltar de página e 3 para voltar ao menu.");

            switch(scan.nextLine()){
                case Constantes.RESP_PROXIMA_PAGINA -> pagina++;
                case Constantes.RESP_PAGINA_ANTERIOR -> {
                    if (pagina == Constantes.PAGINA_INICIAL) {
                        System.out.println("Você já está na página 1!");
                    }
                    else{
                        pagina = pagina - Constantes.PAGINA_INICIAL;
                    }
                }
                case Constantes.RESP_VOLTAR_MENU -> continueLoop = false;
                default -> System.out.println("Opção incorreta, digite uma opção válida.");
            }
        }
    }


    public void alugarVeiculo(){
        Aluguel aluguel = new Aluguel();

        Cliente clienteParaAlugar = controller.retornarCliente(obterDocumentoEditar());

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

        System.out.println("Para o início da locação.");
        LocalTime horarioLocacao = escolherHorarioLocacao();
        if(horarioLocacao == null){
            return;
        }

        System.out.println("Para a devolução do veículo.");
        Date dataDevolucao = escolherData();

        System.out.println("Para a devolução do veículo.");
        LocalTime horarioDevolucao = escolherHorarioLocacao();
        if(horarioLocacao == null){
            return;
        }

        try{
            if(!validacaoDatasLocacao(dataInicio, dataDevolucao)){
                System.out.println("Informe datas válidas");
                return;
            }
        }catch (Exception e){
            System.out.println("Datas inválidas");
        }

        long dataInicioEmMs = dataInicio.getTime();
        long dataDevolucaoEmMs = dataDevolucao.getTime();
        long diferencaDatas = dataDevolucaoEmMs - dataInicioEmMs;

        int diariaExtra = 0;

        if (horarioDevolucao.isAfter(horarioLocacao)) {
            diariaExtra = 1;
        }

        int diasAlugado = (int) ((diferencaDatas / ( 1000 * 60 * 60 * 24)) + diariaExtra);

        System.out.println("Dias alugado: " + diasAlugado);

        System.out.println("Agencia para retirada");
        Agencia agenciaAluguel = escolherAgencia();

        System.out.println("Agencia para devolução");
        Agencia agenciaDevolucao = escolherAgencia();

        BigDecimal valorAluguel = controller.valorDevolucao(clienteParaAlugar.getDocumento(), veiculoParaAluguel, diasAlugado);

        System.out.println("Valor aluguel: " + valorAluguel);

        aluguel.setVeiculo(veiculoParaAluguel); //pendente validação
        aluguel.setAgenciaRetirada(agenciaAluguel); //ok
        aluguel.setAgenciaDevolucao(agenciaDevolucao); //ok
        aluguel.setCliente(clienteParaAlugar); //ok
        aluguel.setDataInicio(dataInicio); //ok
        aluguel.setDataDevolucao(dataDevolucao); //ok
        aluguel.setHorarioAgendado(Time.valueOf(horarioLocacao)); //ok
        aluguel.setHorarioDevolucao(Time.valueOf(horarioDevolucao)); //ok
        aluguel.setValorAluguel(valorAluguel); //pendente

        controller.atualizarDisponibilidadeVeiculo(veiculoParaAluguel.getPlaca(), "false");

        controller.salvarAluguel(aluguel);
    }





    public Agencia escolherAgencia(){
        controller.consultarAgencia("", true);
        System.out.println("Informe o id da Agência que deseja: ");
        int idAgencia = Integer.parseInt(scan.nextLine());
        boolean loop = true;
        while(loop) {
            Agencia agencia = controller.consultarAgenciaPorId(idAgencia);
            if (Objects.equals(agencia.getId(), "")) {
                System.out.println("Agência não encontrada, favor digitar outra.");
                idAgencia = Integer.parseInt(scan.nextLine());
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
        System.out.println("Informe o horário no formato HH:mm:ss");
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
            return sdf.parse(data);
        } catch (ParseException e) {
            System.out.println("Digite um formato de data válido.");
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

    public void paginacaoAgencia(){
        boolean continueLoop = true;
        int pagina = Constantes.PAGINA_INICIAL;
        while (continueLoop){
            if (controller.paginacaoClientes(pagina).isEmpty()){
                throw new ListaVaziaException("Não há mais Agencias a serem exibidos. Voltando ao menu...");
            }
            System.out.println("------------------------------------PAGINA "+pagina+"--------------------------------");
            System.out.format("%12s %10s %10s", "ID", "NOME", "LOGRADOURO\n");
            for (int i = 0; i < controller.paginacaoAgencia(pagina).size(); i++) {
                System.out.format("%12s %10s %10s", ""+controller.paginacaoAgencia(pagina).get(i).getId()+"", ""
                        +controller.paginacaoAgencia(pagina).get(i).getNome()+"", ""
                        +controller.paginacaoAgencia(pagina).get(i).getLogradouro()+"\n");
            }

            System.out.println("O que deseja fazer? Digite 1 para ir para próxima página; 2 para voltar de página e 3 para voltar ao menu.");

            switch(scan.nextLine()){
                case Constantes.RESP_PROXIMA_PAGINA -> pagina++;
                case Constantes.RESP_PAGINA_ANTERIOR -> {
                    if (pagina == Constantes.PAGINA_INICIAL) {
                        System.out.println("Você já está na página 1!");
                    }
                    else{
                        pagina = pagina - Constantes.PAGINA_INICIAL;
                    }
                }
                case Constantes.RESP_VOLTAR_MENU -> continueLoop = false;
                default -> System.out.println("Opção incorreta, digite uma opção válida.");
            }
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



}


