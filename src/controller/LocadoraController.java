package controller;

import Repository.AgenciaDAO;
import Repository.AluguelDAO;
import Repository.ClienteDAO;
import Repository.VeiculoDAO;
import exception.ClienteExisteException;
import exception.VeiculoExisteException;
import model.*;
import util.TablePrinter;

import java.math.BigDecimal;
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

    public Veiculo consultaVeiculo(String placa){
        return veiculoDAO.retornarVeiculoPlaca(placa);
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

    public List<Veiculo> paginacaoVeiculos(int pagina){
        return veiculoDAO.paginacaoVeiculos(pagina);
    }

    public List<Cliente> paginacaoClientes(int pagina){
        return clienteDAO.paginacaoClientes(pagina);
    }

    public void editarVeiculoPorPlaca(VeiculoDTO veiculoDTO){
        veiculoDAO.atualizarPorPlaca(veiculoDTO);
    }

    public void atualizarDisponibilidadeVeiculo(String placa, String disponibilidade) {
        veiculoDAO.atualizarDisponibilidadePorPlaca(placa, disponibilidade);
    }
    public List<Veiculo> veiculosDisponiveisParaAluguel(){
        return veiculoDAO.listaVeiculosDisponiveis();
    }

    public void adicionarAgencia(Agencia agencia) {
        String paramsQuery = "INSERT|nome;logradouro|";
        paramsQuery += agencia.getNome().toUpperCase();
        paramsQuery += ";";
        paramsQuery += agencia.getLogradouro().toUpperCase();
        agenciaDAO.incluir(paramsQuery);
    }

    public void editarAgencia(String paramsQuery) {
        agenciaDAO.incluir(paramsQuery);
    }

    public Agencia consultarAgenciaPorNome(String nomeAgencia) {
        String paramsQuery = "ILIKE|nome|" + nomeAgencia;
        List<Agencia> listAgencia = new ArrayList<>();
        listAgencia = agenciaDAO.consulta(paramsQuery);
        if (listAgencia.size() == 0) return new Agencia("");
        return listAgencia.get(0);
    }

    public Agencia consultarAgenciaPorId(int idAgencia) {
        String paramsQuery = "SEARCH|id_agencia|" + idAgencia;
        List<Agencia> listAgencia = new ArrayList<>();
        listAgencia = agenciaDAO.consulta(paramsQuery);
        if (listAgencia.size() == 0) return new Agencia("");
        return listAgencia.get(0);
    }

    public boolean consultarAgencia(String paramsQuery, boolean print) {
        List<Agencia> listAgencia = new ArrayList<>();
        listAgencia = agenciaDAO.consulta(paramsQuery);
        if (print) {
            TablePrinter tablePrinter = new TablePrinter();
            tablePrinter.agenciaTablePrinter(listAgencia);
        }
        return !(listAgencia.size() == 0);
    }

    public void deletarAgencia(String paramsQuery) {
        agenciaDAO.deletar(paramsQuery);
    }

    public List<Agencia> paginacaoAgencia (int pagina){
        return agenciaDAO.paginacaoAgencia(pagina);
    }

    public BigDecimal valorDevolucao(String documento, VeiculoDTO veiculoDTO, int dias){
        double precoFinal = 0.0;
        Cliente cliente = clienteDAO.retornarCliente(documento);
        String tipoCliente = cliente.getTipoCliente();
        double desconto = cliente.percentualDesconto(dias, tipoCliente);
        double valorSemDesconto = (TipoVeiculo.calculaValor(veiculoDTO.getTipo())*dias);
        precoFinal = valorSemDesconto - (valorSemDesconto*desconto);

        return BigDecimal.valueOf(precoFinal);
    }

    public VeiculoDTO obterVeiculoPorPlaca(String placa){
        return veiculoDAO.pegarVeiculoPorPlaca(placa);
    }

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

    public void salvarAluguel(Aluguel aluguel){
        aluguelDAO.salvarAluguel(aluguel);
    }

    public Aluguel buscarAluguelPorId(int id){
        return aluguelDAO.pegarAluguelPorId(id);
    }

}
