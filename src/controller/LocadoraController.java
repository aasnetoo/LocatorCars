package controller;

import exception.CarroExisteException;
import exception.EntradaInvalidaOuInsuficienteException;
import model.CarroDAO;
import model.CarroDTO;
import util.Constantes;
import util.Mensagens;
import view.LocadoraView;

import java.sql.SQLException;

public class LocadoraController {

    CarroDAO carroDAO = new CarroDAO();

    Mensagens mensagens = new Mensagens();



    public LocadoraController() throws SQLException {
    }

    public void adicionarCarro (CarroDTO carroDTO){
        boolean carroExiste = carroDAO.listaCarros().stream().anyMatch(carro -> carro.equals(carroDTO));

        if (carroExiste){
            throw new CarroExisteException();
        }
        carroDAO.incluir(carroDTO);
    }

    public void consultaCarro(String placa){
        carroDAO.consulta(placa);
    }

//    public void confirmacaoEditarCarro (String resposta){
//        switch(resposta){
//            case Constantes.RESP_SIM -> view.editarCarro();
//            case Constantes.RESP_NAO -> mensagens.voltandoMenu();
//            default -> throw new EntradaInvalidaOuInsuficienteException("Entrada inválida!");
//        }
//    }
//
//    public void editarCarroPorPlaca(CarroDTO carroDTO){
//        carroDAO.atualizarPorPlaca(carroDTO);
//    }







}
