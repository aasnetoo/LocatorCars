package controller;

import model.CarroDAO;
import model.CarroDTO;

public class LocadoraController {

    CarroDAO carroDAO = new CarroDAO();

    public void adicionarCarro (CarroDTO carroDTO){
        carroDAO.incluir(carroDTO);
    }


}
