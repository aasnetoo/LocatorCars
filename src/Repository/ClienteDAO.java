package Repository;

import database.Conexao;
import model.Cliente;
import model.Veiculo;
import util.Constantes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements IGenericoRepository<Cliente> {

    Conexao instance = Conexao.getInstance();


    public ClienteDAO() throws SQLException {
    }

    @Override
    public void incluir(Cliente cliente) {
        String query = "INSERT into clientes (nome, telefone, documento, tipo) values('"
                + cliente.getNome() + "', '"
                + cliente.getTelefone() + "', '"
                + cliente.getDocumento() + "', '"
                + cliente.getTipoCliente()
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
                System.out.print(" - " + resultado.getString("documento"));
                System.out.print(" - " + resultado.getString("tipo") + "\n");
            }
        }catch(SQLException ex){
            System.out.println("Não conseguiu consultar os dados do cliente.");
        }
    }

    @Override
    public void deletar(Cliente object) {

    }

    @Override
    public List<Cliente> listarTodos(){
        List<Cliente> listaClientes = new ArrayList<>();
        try{
            String sql = "SELECT * FROM clientes";
            PreparedStatement stm = instance.getConnection().prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()){
                Cliente cliente = new Cliente();
                cliente.setNome(rs.getString("nome"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setDocumento(rs.getString("documento"));
                cliente.setTipoCliente(rs.getString("tipo"));
                listaClientes.add(cliente);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaClientes;
    }

    public Cliente retornarCliente(String documento){
        Cliente cliente =  new Cliente();
        try{
            String sql = "SELECT * FROM clientes WHERE documento like '"+documento+"'";
            PreparedStatement stm = instance.getConnection().prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()){
                cliente.setNome(rs.getString("nome"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setDocumento(rs.getString("documento"));
                cliente.setTipoCliente(rs.getString("tipo"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cliente;
    }

    public void atualizarPorDocumento(Cliente cliente) {
        try {
            String sql = "UPDATE clientes " +
                    " SET nome = '"+ cliente.getNome()+"', " +
                    " telefone = "+ cliente.getTelefone()+"," +
                    " tipo = '"+ cliente.getTipoCliente()+"' " +
                    " WHERE documento = '"+ cliente.getDocumento()+"' ";

            PreparedStatement statement = instance.getConnection().prepareStatement(sql);
            statement.execute();
            System.out.println("O cliente do documento: " + cliente.getDocumento() + " foi atualizado com sucesso.");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Cliente> paginacaoClientes(int pagina){

        List<Cliente> listaClientes = new ArrayList<>();
        try{
            String sql = "SELECT * FROM clientes LIMIT "+ Constantes.ITENS_POR_PAGINA+"  OFFSET("+pagina+" - 1) * "+Constantes.ITENS_POR_PAGINA;
            PreparedStatement stm = instance.getConnection().prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()){
                Cliente cliente = new Cliente();
                cliente.setNome(rs.getString("nome"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setDocumento(rs.getString("documento"));
                cliente.setTipoCliente(rs.getString("tipo"));
                listaClientes.add(cliente);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaClientes;
    }


}
