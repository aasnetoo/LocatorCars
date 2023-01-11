package controller;

import exception.CarroExisteException;
import model.CarroDAO;
import model.CarroDTO;

import java.sql.SQLException;

public class LocadoraController {

    CarroDAO carroDAO = new CarroDAO();

    public LocadoraController() throws SQLException {
    }

    public void adicionarCarro (CarroDTO carroDTO){
        boolean carroExiste = carroDAO.listaCarros().stream().anyMatch(carro -> carro.equals(carroDTO));

        if (carroExiste){
            throw new CarroExisteException();
        }
        carroDAO.incluir(carroDTO);
    }






}
