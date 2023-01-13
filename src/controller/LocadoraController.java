package controller;

import Repository.VeiculoDAO;
import exception.VeiculoExisteException;
import model.*;
import util.Constantes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocadoraController {

    VeiculoDAO veiculoDAO = new VeiculoDAO();
    FactoryCliente factoryCliente = new FactoryCliente();
    PrecoDevolucao precoDevolucao = new PrecoDevolucao();


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

    public double valorDevolucao(String tipoCliente, String nome, VeiculoDTO veiculoDTO, int dias){
        double precoFinal = 0.0;
        Cliente cliente = factoryCliente.getCliente(nome,tipoCliente);
        if (cliente.tipoCliente.equalsIgnoreCase(Constantes.CLIENTE_FISICO)){
            precoFinal = precoDevolucao.calculaDevolucao(veiculoDTO, dias);
        }
        if (cliente.tipoCliente.equalsIgnoreCase(Constantes.CLIENTE_JURIDICO)){
            precoFinal = precoDevolucao.calculaDevolucao(veiculoDTO, dias);
        }
        return precoFinal;
    }







}
