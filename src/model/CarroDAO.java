package model;

import database.Conexao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CarroDAO  {

    Conexao instance = new Conexao();

    public void incluir(CarroDTO carroDTO) {
        String query = "INSERT into produtos (placa, modelo, potencia) values('" + carroDTO.getPlaca() + "', '"
                + carroDTO.getModelo() + "', "
                + carroDTO.getPotencia()
                + ")";
        try {
            Statement stm = instance.getConnection().createStatement();
            stm.executeUpdate(query);
            System.out.println("O produto foi adicionado com sucesso. ");
        } catch (SQLException ex) {
            System.out.println("Nao conseguiu executar o DML\n" + query);
        }
    }















}
