package model;

import database.Conexao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarroDAO  {

    Conexao instance = Conexao.getInstance();

    public CarroDAO() throws SQLException {
    }

    public void incluir(CarroDTO carroDTO) {
        String query = "INSERT into carros (placa, modelo, potencia) values('" + carroDTO.getPlaca() + "', '"
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

    public List<CarroDTO> listaCarros(){
        List<CarroDTO> listaCarros = new ArrayList<>();
        try{
            String sql = "SELECT * FROM carros";
            PreparedStatement stm = instance.getConnection().prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()){
                CarroDTO carroDTO = new CarroDTO();
                carroDTO.setPlaca(rs.getString("placa"));
                carroDTO.setModelo(rs.getString("modelo"));
                carroDTO.setPotencia(rs.getDouble("potencia"));
                listaCarros.add(carroDTO);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaCarros;
    }

//    public void atualizarCarro(CarroDTO carroDTO){
//        try {
//
//            String sql = "UPDATE carros " +
//                    " SET placa, " +
//                    " modelo = ?," +
//                    " potencia = ?," +
//                    " tipo = ? ";
//
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, pessoaDTO.getNome());
//            statement.setLong(2, pessoaDTO.getCpf());
//            //statement.setString(3, pessoaDTO.getEndereco());
//            statement.setString(3, String.valueOf(pessoaDTO.getSexo()));
//            statement.setDate(4, new Date(pessoaDTO.getDtNascimento().getTime()));
//            statement.setInt(5, pessoaDTO.getIdPessoa());
//
//            statement.execute();
//            connection.close();
//            //ATUALIZA AGORA O relacionamento da pessoa
//            atualizaEndereco(pessoaDTO.getEnderecoDTO());
//        } catch(Exception e) {
//            e.printStackTrace();
//            throw new PersistenciaExcpetion(e.getMessage(), e);
//        }
//    }















}
