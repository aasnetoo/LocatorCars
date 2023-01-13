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

    @Override
    public void incluir(VeiculoDTO veiculoDTO) {
        String query = "INSERT into carros (placa, modelo, potencia, tipo) values('" + veiculoDTO.getPlaca() + "', '"
                + veiculoDTO.getModelo() + "', "
                + veiculoDTO.getPotencia() + ", '"
                + veiculoDTO.getTipo()
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
        String consulta = "SELECT * FROM carros WHERE placa like '"+placa+"'";
        try {
            Statement stm = instance.getConnection().createStatement();
            ResultSet resultado = stm.executeQuery(consulta);

            while (resultado.next()) {
                System.out.print(resultado.getString("placa"));
                System.out.print(" - " + resultado.getString("modelo"));
                System.out.print(" - " + resultado.getString("potencia") + "\n");
                System.out.print(" - " + resultado.getString("tipo") + "\n");
            }
        }catch(SQLException ex){
            System.out.println("Não conseguiu consultar os dados do Veiculo.");
        }
    }

    @Override
    public void deletar(VeiculoDTO object) {

    }

    @Override
    public List<VeiculoDTO> listarTodos(){
        List<VeiculoDTO> listaCarros = new ArrayList<>();
        try{
            String sql = "SELECT * FROM carros";
            PreparedStatement stm = instance.getConnection().prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()){
                VeiculoDTO veiculoDTO = new VeiculoDTO();
                veiculoDTO.setPlaca(rs.getString("placa"));
                veiculoDTO.setModelo(rs.getString("modelo"));
                veiculoDTO.setPotencia(rs.getDouble("potencia"));
                veiculoDTO.setTipo(rs.getString("tipo"));
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
            String sql = "SELECT * FROM carros";
            PreparedStatement stm = instance.getConnection().prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()){
                Veiculo veiculo = new Veiculo();
                veiculo.setPlaca(rs.getString("placa"));
                veiculo.setModelo(rs.getString("modelo"));
                veiculo.setPotencia(rs.getDouble("potencia"));
                veiculo.setTipo(rs.getString("tipo"));
                listaVeiculos.add(veiculo);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaVeiculos;
    }

    public void atualizarPorPlaca(VeiculoDTO veiculoDTO) {
        try {
            String sql = "UPDATE carros " +
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
