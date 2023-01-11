package controller;

import exception.CarroExisteException;
import model.Carro;
import model.CarroDAO;
import model.CarroDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocadoraController {

    CarroDAO carroDAO = new CarroDAO();


    public LocadoraController() throws SQLException {
    }

    public void adicionarCarro (CarroDTO carroDTO){
        boolean carroExiste = carroDAO.listaCarrosDTO().stream().anyMatch(carro -> carro.equals(carroDTO));

        if (carroExiste){
            throw new CarroExisteException();
        }
        carroDAO.incluir(carroDTO);
    }

    public void consultaCarro(String placa){
        carroDAO.consulta(placa);
    }

    public List<Carro> ConsultaPorModelo(String modelo){
        List<Carro> carrosFiltrados = new ArrayList<>();
        for (int i = 0; i < carroDAO.listaCarros().size(); i++) {
            if (carroDAO.listaCarros().get(i).getModelo().contains(modelo)){
                carrosFiltrados.add(carroDAO.listaCarros().get(i));
            }
        }
        return carrosFiltrados;
    }


    public void editarCarroPorPlaca(CarroDTO carroDTO){
        carroDAO.atualizarPorPlaca(carroDTO);
    }







}
