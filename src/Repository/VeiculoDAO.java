package Repository;

import database.Conexao;
import model.Veiculo;
import model.VeiculoDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO implements IGenericoRepository<VeiculoDTO>{

    Conexao instance = Conexao.getInstance();

    public VeiculoDAO() throws SQLException {
    }

    public void incluir(VeiculoDTO veiculoDTO) {
        String query = "INSERT into veiculos (placa, modelo, potencia, tipo, disponivel) values('" + veiculoDTO.getPlaca() + "', '"
                + veiculoDTO.getModelo() + "', '"
                + veiculoDTO.getPotencia() + "', '"
                + veiculoDTO.getTipo() + "', '"
                + veiculoDTO.isDisponivel()
                + "')";
        try {
            Statement stm = instance.getConnection().createStatement();
            stm.executeUpdate(query);
            System.out.println("O Veiculo foi adicionado com sucesso. ");
        } catch (SQLException ex) {
            System.out.println("Nao conseguiu executar o DML\n" + query);
        }
    }

    @Override
    public void consulta(String placa) {
        String consulta = "SELECT * FROM veiculos WHERE placa like '"+placa+"'";
        try {
            Statement stm = instance.getConnection().createStatement();
            ResultSet resultado = stm.executeQuery(consulta);

            while (resultado.next()) {
                System.out.print(resultado.getString("placa"));
                System.out.print(" - " + resultado.getString("modelo"));
                System.out.print(" - " + resultado.getString("potencia") + "\n");
                System.out.print(" - " + resultado.getString("tipo") + "\n");
                System.out.print(" - " + resultado.getString("disponivel") + "\n");

            }
        }catch(SQLException ex){
            System.out.println("Não conseguiu consultar os dados do Veiculo.");
        }
    }

    public VeiculoDTO pegarVeiculoPorPlaca(String placa){
        String consulta = "SELECT * FROM veiculos WHERE placa like '"+placa+"'";
        VeiculoDTO veiculoDTO = new VeiculoDTO();
        try {
            Statement stm = instance.getConnection().createStatement();
            ResultSet resultado = stm.executeQuery(consulta);
            while(resultado.next()) {

                veiculoDTO.setPlaca(resultado.getString("placa"));
                veiculoDTO.setModelo(resultado.getString("modelo"));
                veiculoDTO.setPotencia(resultado.getDouble("potencia"));
                veiculoDTO.setTipo(resultado.getString("tipo"));
                veiculoDTO.setDisponivel(resultado.getBoolean("disponivel"));

            }

        }catch(SQLException ex){
            System.out.println("Não conseguiu consultar os dados do Veiculo.");
        }
        return veiculoDTO;
    }

    public List<Veiculo> listaVeiculosDisponiveis(){
        String consulta = "SELECT * FROM veiculos WHERE disponivel";
        List<Veiculo> veiculosDisponiveis = new ArrayList<>();
        try {
            Statement stm = instance.getConnection().createStatement();
            ResultSet resultado = stm.executeQuery(consulta);
            while(resultado.next()) {
                Veiculo veiculoDisponivel = new Veiculo();
                veiculoDisponivel.setPlaca(resultado.getString("placa"));
                veiculoDisponivel.setModelo(resultado.getString("modelo"));
                veiculoDisponivel.setPotencia(resultado.getDouble("potencia"));
                veiculoDisponivel.setTipo(resultado.getString("tipo"));
                veiculoDisponivel.setDisponivel(resultado.getBoolean("disponivel"));

                veiculosDisponiveis.add(veiculoDisponivel);

            }

        }catch(SQLException ex){
            System.out.println("Não conseguiu consultar os dados do Veiculo.");
        }
        return veiculosDisponiveis;
    }
    @Override
    public void deletar(VeiculoDTO object) {

    }

    @Override
    public List<VeiculoDTO> listarTodos(){
        List<VeiculoDTO> listaCarros = new ArrayList<>();
        try{
            String sql = "SELECT * FROM veiculos";
            PreparedStatement stm = instance.getConnection().prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()){
                VeiculoDTO veiculoDTO = new VeiculoDTO();
                veiculoDTO.setPlaca(rs.getString("placa"));
                veiculoDTO.setModelo(rs.getString("modelo"));
                veiculoDTO.setPotencia(rs.getDouble("potencia"));
                veiculoDTO.setTipo(rs.getString("tipo"));
                veiculoDTO.setDisponivel(rs.getBoolean("disponivel"));

                listaCarros.add(veiculoDTO);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaCarros;
    }

    public List<Veiculo> listaVeiculos(){
        List<Veiculo> listaVeiculos = new ArrayList<>();
        try{
            String sql = "SELECT * FROM veiculos";
            PreparedStatement stm = instance.getConnection().prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()){
                Veiculo veiculo = new Veiculo();
                veiculo.setPlaca(rs.getString("placa"));
                veiculo.setModelo(rs.getString("modelo"));
                veiculo.setPotencia(rs.getDouble("potencia"));
                veiculo.setTipo(rs.getString("tipo"));
                veiculo.setDisponivel(rs.getBoolean("disponivel"));

                listaVeiculos.add(veiculo);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaVeiculos;
    }

    public void atualizarPorPlaca(VeiculoDTO veiculoDTO) {
        try {
            String sql = "UPDATE veiculos " +
                    " SET modelo = '"+veiculoDTO.getModelo()+"', " +
                    " potencia = "+veiculoDTO.getPotencia()+"," +
                    " tipo = '"+veiculoDTO.getTipo()+"' " +
                    " WHERE placa = '"+veiculoDTO.getPlaca()+"' ";

            PreparedStatement statement = instance.getConnection().prepareStatement(sql);
            statement.execute();
            System.out.println("O veiculo com placa: " + veiculoDTO.getPlaca() + " foi atualizado com sucesso.");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
