package controller;

import Repository.ClienteDAO;
import Repository.VeiculoDAO;
import exception.ClienteExisteException;
import exception.VeiculoExisteException;
import model.*;
import util.Constantes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocadoraController {

    VeiculoDAO veiculoDAO = new VeiculoDAO();

    ClienteDAO clienteDAO = new ClienteDAO();

    FactoryCliente factoryCliente = new FactoryCliente();


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

    // Fica faltando só mudar a disponibilidade do veiculo dps que devolver, quase OK
    public double valorDevolucao(String documento, VeiculoDTO veiculoDTO, int dias, String tipoCliente){
        double precoFinal = 0.0;
        Cliente cliente = factoryCliente.getCliente(documento,tipoCliente);
        double desconto = cliente.valorDesconto(dias);
        double valorSemDesconto = (TipoVeiculo.calculaValor(veiculoDTO.getTipo())*dias);
        precoFinal = valorSemDesconto - (valorSemDesconto*desconto);

        return precoFinal;
    }

    public VeiculoDTO obterVeiculoPorPlaca(String placa){
        return veiculoDAO.pegarVeiculoPorPlaca(placa);
    }


    /////////////cliente
    public void adicionarCliente (ClienteDTO clienteDTO){
        boolean clienteExiste = clienteDAO.listarTodos().stream().anyMatch(cliente -> cliente.equals(clienteDTO));

        if (clienteExiste){
            throw new ClienteExisteException();
        }
        clienteDAO.incluir(clienteDTO);
    }

    public void consultarCliente(String documento){
        clienteDAO.consulta(documento);
    }


    public void editarClientePorDocumento(ClienteDTO clienteDTO){
        clienteDAO.atualizarPorDocumento(clienteDTO);
    }
    //fim cliente


}
