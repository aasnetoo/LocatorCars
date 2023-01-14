package Repository;

import database.Conexao;
import model.ClienteDTO;
import model.Veiculo;
import model.VeiculoDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements IGenericoRepository<ClienteDTO> {

    Conexao instance = Conexao.getInstance();


    public ClienteDAO() throws SQLException {
    }

    @Override
    public void incluir(ClienteDTO clienteDTO) {
        String query = "INSERT into clientes (nome, telefone, documento, tipoCliente) values('"
                + clienteDTO.getNome() + "', '"
                + clienteDTO.getTelefone() + "', "
                + clienteDTO.getDocumento() + ", '"
                + clienteDTO.getTipoCliente()
                + "')";
        try {
            Statement stm = instance.getConnection().createStatement();
            stm.executeUpdate(query);
            System.out.println("O cliente foi adicionado com sucesso. ");
        } catch (SQLException ex) {
            System.out.println("Nao conseguiu executar o DML\n" + query);
        }
    }

    @Override
    public void consulta(String documento) {
        String consulta = "SELECT * FROM clientes WHERE documento like '"+documento+"'";
        try {
            Statement stm = instance.getConnection().createStatement();
            ResultSet resultado = stm.executeQuery(consulta);

            while (resultado.next()) {
                System.out.print(resultado.getString("nome"));
                System.out.print(" - " + resultado.getString("telefone"));
                System.out.print(" - " + resultado.getString("documento") + "\n");
                System.out.print(" - " + resultado.getString("tipoCliente") + "\n");
            }
        }catch(SQLException ex){
            System.out.println("Não conseguiu consultar os dados do cliente.");
        }
    }

//    public VeiculoDTO pegarVeiculoPorPlaca(String placa){
//        String consulta = "SELECT * FROM carros WHERE placa like '"+placa+"'";
//        VeiculoDTO veiculoDTO = new VeiculoDTO();
//        try {
//            Statement stm = instance.getConnection().createStatement();
//            ResultSet resultado = stm.executeQuery(consulta);
//            while(resultado.next()) {
//
//                veiculoDTO.setPlaca(resultado.getString("placa"));
//                veiculoDTO.setModelo(resultado.getString("modelo"));
//                veiculoDTO.setPotencia(resultado.getDouble("potencia"));
//                veiculoDTO.setTipo(resultado.getString("tipo"));
//            }
//
//        }catch(SQLException ex){
//            System.out.println("Não conseguiu consultar os dados do Veiculo.");
//        }
//        return veiculoDTO;
//    }

    @Override
    public void deletar(ClienteDTO object) {

    }

    @Override
    public List<ClienteDTO> listarTodos(){
        List<ClienteDTO> listaClientes = new ArrayList<>();
        try{
            String sql = "SELECT * FROM clientes";
            PreparedStatement stm = instance.getConnection().prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()){
                ClienteDTO clienteDTO = new ClienteDTO();
                clienteDTO.setNome(rs.getString("nome"));
                clienteDTO.setTelefone(rs.getString("telefone"));
                clienteDTO.setDocumento(rs.getString("documento"));
                clienteDTO.setTipoCliente(rs.getString("tipoCliente"));
                listaClientes.add(clienteDTO);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaClientes;
    }
//
//    public List<Veiculo> listaVeiculos(){
//        List<Veiculo> listaVeiculos = new ArrayList<>();
//        try{
//            String sql = "SELECT * FROM carros";
//            PreparedStatement stm = instance.getConnection().prepareStatement(sql);
//            ResultSet rs = stm.executeQuery();
//
//            while (rs.next()){
//                Veiculo veiculo = new Veiculo();
//                veiculo.setPlaca(rs.getString("placa"));
//                veiculo.setModelo(rs.getString("modelo"));
//                veiculo.setPotencia(rs.getDouble("potencia"));
//                veiculo.setTipo(rs.getString("tipo"));
//                listaVeiculos.add(veiculo);
//
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return listaVeiculos;
//    }

    public void atualizarPorDocumento(ClienteDTO clienteDTO) {
        try {
            String sql = "UPDATE clientes " +
                    " SET nome = '"+clienteDTO.getNome()+"', " +
                    " telefone = "+clienteDTO.getTelefone()+"," +
                    " tipoCliente = '"+clienteDTO.getTipoCliente()+"' " +
                    " WHERE documento = '"+clienteDTO.getDocumento()+"' ";

            PreparedStatement statement = instance.getConnection().prepareStatement(sql);
            statement.execute();
            System.out.println("O cliente do documento: " + clienteDTO.getDocumento() + " foi atualizado com sucesso.");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
