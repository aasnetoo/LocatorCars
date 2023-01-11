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
                + carroDTO.getPotencia() + ", ''"
                + carroDTO.getTipo()
                + "'')";
        try {
            Statement stm = instance.getConnection().createStatement();
            stm.executeUpdate(query);
            System.out.println("O produto foi adicionado com sucesso. ");
        } catch (SQLException ex) {
            System.out.println("Nao conseguiu executar o DML\n" + query);
        }
    }

    public void consulta(String placa) {
        String consulta = "SELECT * FROM PESSOA WHERE NOME like '"+placa+"'";
        try {
            Statement stm = instance.getConnection().createStatement();
            ResultSet resultado = stm.executeQuery(consulta);

            while (resultado.next()) {
                System.out.print(resultado.getString("nome"));
                System.out.print(" - " + resultado.getString("modelo"));
                System.out.print(" - " + resultado.getString("potencia") + "\n");
                System.out.print(" - " + resultado.getString("tipo") + "\n");
            }
            }catch(SQLException ex){
                System.out.println("Não conseguiu consultar os dados do Carro.");
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

    public void atualizarPorPlaca(CarroDTO carroDTO) {
        try {

            String sql = "UPDATE carros " +
                    " SET modelo = '"+carroDTO.getModelo()+"', " +
                    " potencia = "+carroDTO.getPotencia()+"," +
                    " tipo = '"+carroDTO.getTipo()+"' " +
                    " WHERE placa = '"+carroDTO.getPlaca()+"' ";

           PreparedStatement statement = instance.getConnection().prepareStatement(sql);
           statement.execute();


        } catch(Exception e) {
            throw new RuntimeException(e);

        }
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
