package controller;

import Repository.AgenciaDAO;
import Repository.AluguelDAO;
import Repository.ClienteDAO;
import Repository.VeiculoDAO;
import exception.ClienteExisteException;
import exception.VeiculoExisteException;
import model.*;
import util.TablePrinter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocadoraController {

    VeiculoDAO veiculoDAO = new VeiculoDAO();
    AgenciaDAO agenciaDAO = new AgenciaDAO();
    ClienteDAO clienteDAO = new ClienteDAO();
    AluguelDAO aluguelDAO = new AluguelDAO();

    public LocadoraController() throws SQLException {
    }

    public void adicionarVeiculo (VeiculoDTO veiculoDTO){
        boolean carroExiste = veiculoDAO.listarTodos().stream().anyMatch(veiculo -> veiculo.equals(veiculoDTO));

        if (carroExiste){
            throw new VeiculoExisteException();
        }
        veiculoDAO.incluir(veiculoDTO);
    }

    public void consultaVeiculo(String placa){
        veiculoDAO.consulta(placa);
    }

    public List<Veiculo> ConsultaPorModelo(String modelo){
        List<Veiculo> veiculosFiltrados = new ArrayList<>();
        for (int i = 0; i < veiculoDAO.listaVeiculos().size(); i++) {
            if (veiculoDAO.listaVeiculos().get(i).getModelo().contains(modelo)){
                veiculosFiltrados.add(veiculoDAO.listaVeiculos().get(i));
            }
        }
        return veiculosFiltrados;
    }


    public void editarCarroPorPlaca(VeiculoDTO veiculoDTO){
        veiculoDAO.atualizarPorPlaca(veiculoDTO);
    }
    public List<Veiculo> veiculosDisponiveisParaAluguel(){
        return veiculoDAO.listaVeiculosDisponiveis();
    }

    // Agencia
    public void adicionarAgencia(AgenciaDTO agenciaDTO) {
        String paramsQuery = "INSERT|nome;logradouro|";
        paramsQuery += agenciaDTO.getNome().toUpperCase();
        paramsQuery += ";";
        paramsQuery += agenciaDTO.getLogradouro().toUpperCase();
        agenciaDAO.incluir(paramsQuery);
    }

    public void editarAgencia(String paramsQuery) {
        agenciaDAO.incluir(paramsQuery);
    }

    public boolean consultarAgencia(String paramsQuery, boolean print) {

        // Obtém as agências presentes no Banco de Dados
        List<AgenciaDTO> listAgenciaDTO = new ArrayList<>();
        listAgenciaDTO = agenciaDAO.consulta(paramsQuery);
        if (print) {
            // Imprime no console as Agências
            TablePrinter tablePrinter = new TablePrinter();
            tablePrinter.agenciaTablePrinter(listAgenciaDTO);
        }

        return !(listAgenciaDTO.size() == 0);
    }
    public void deletarAgencia(String paramsQuery) {
        agenciaDAO.deletar(paramsQuery);
    }

    public boolean verificaExistenciaAgenciaPorId(int idAgencia) {

        List<AgenciaDTO> listAgenciaDTO = new ArrayList<>();
        String query = "id_agencia|" + idAgencia;
        listAgenciaDTO = agenciaDAO.consulta(query);

        return listAgenciaDTO.size() == 1;
    }
    // Fica faltando só mudar a disponibilidade do veiculo dps que devolver, quase OK
    public double valorDevolucao(String documento, VeiculoDTO veiculoDTO, int dias, String tipoCliente){
        double precoFinal = 0.0;
        Cliente cliente = clienteDAO.retornarCliente(documento);
        double desconto = cliente.valorDesconto(dias, cliente.getTipoCliente());
        double valorSemDesconto = (TipoVeiculo.calculaValor(veiculoDTO.getTipo())*dias);
        precoFinal = valorSemDesconto - (valorSemDesconto*desconto);

        return precoFinal;
    }

    public VeiculoDTO obterVeiculoPorPlaca(String placa){
        return veiculoDAO.pegarVeiculoPorPlaca(placa);
    }


    /////////////cliente
    public void adicionarCliente (Cliente cl){
        boolean clienteExiste = clienteDAO.listarTodos().stream().anyMatch(cliente -> cliente.equals(cl));

        if (clienteExiste){
            throw new ClienteExisteException();
        }
        clienteDAO.incluir(cl);
    }

    public void consultarCliente(String documento){
        clienteDAO.consulta(documento);
    }


    public void editarClientePorDocumento(Cliente cliente){
        clienteDAO.atualizarPorDocumento(cliente);
    }

    public Cliente retornarCliente(String documento){
        return clienteDAO.retornarCliente(documento);
    }

    //fim cliente

    ///////////Aluguel
    public void salvarAluguel(Aluguel aluguel){
        aluguelDAO.salvarAluguel(aluguel);
    }

    ///////Fim Aluguel


}
