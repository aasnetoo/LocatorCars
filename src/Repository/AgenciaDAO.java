package Repository;

import database.Conexao;
import model.Agencia;
import model.Veiculo;
import util.Constantes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
// implements IGenericoRepository<Agencia>

public class AgenciaDAO {

    Conexao instance = Conexao.getInstance();

    public AgenciaDAO() throws SQLException {
    }

    // @Override
    public void incluir(String paramsQuery) {
        /**
            @param paramsQuery
            Primeira Coluna: INSERT ou UPDATE
            Segunda Coluna: Nome das colunas
            Terceira Coluna: Data das Colunas
            Quarta Coluna: Id da Agência

            Ex1: UPDATE|nome|fulano|1
            sql query -> UPDATE agencias SET nome='fulano' WHERE id_agencia=1

            Ex2: UPDATE|nome;logradouro|fulano;Rua10|7
            sql query -> UPDATE agencias SET nome='fulano',logradouro='Rua10' WHERE id_agencia=7

            Ex3: INSERT|nome;logradouro|fulano;Rua12
            sql query -> INSERT INTO agencias (nome, logradouro) VALUES('fulano','Rua12');
        */

        List<String> params = List.of(paramsQuery.split("\\|"));

        String sqlQuery;
        if (params.get(0).equals("INSERT")) {

            List<String> data = List.of(params.get(2).split(";"));

            sqlQuery = "INSERT INTO agencias (nome, logradouro) VALUES('"
                + data.get(0).toUpperCase() + "', '"
                + data.get(1).toUpperCase()
                + "')";

        } else {
            sqlQuery = "UPDATE agencias SET ";

            List<String> column = List.of(params.get(1).split(";"));
            List<String> data = List.of(params.get(2).split(";"));

            for (int i = 0; i < column.size(); i++) {
                sqlQuery += column.get(i) + "='" + data.get(i).toUpperCase() + "'";
                if (i == 0 && column.size() > 1) sqlQuery +=  ",";
            }
            sqlQuery += " WHERE id_agencia="+ params.get(3);
        }

        try {

            Statement stm = instance.getConnection().createStatement();
            stm.executeUpdate(sqlQuery);

            if (params.get(0).equals("INSERT")) {
                System.out.println("A Agência foi adicionada com sucesso.");
            } else {
                System.out.println("A Agência foi atualizada com sucesso.");
            }

        } catch (SQLException ex) {
            System.out.println("Nao conseguiu executar o DML\n" + sqlQuery);
        }

    }

    // @Override
    public List<Agencia> consulta(String paramsQuery) {
        
        List<String> params = List.of(paramsQuery.split("\\|"));
        String sqlQuery = "SELECT * FROM agencias";
        List<Agencia> listAgencia = new ArrayList<>();

        if (params.size() > 1) {
            List<String> coluna = List.of(params.get(1).split(";"));
            List<String> searchQuery = List.of(params.get(2).split(";"));
            if (params.get(0).equals("ILIKE")) {
                sqlQuery += " WHERE " + coluna.get(0) + " ILIKE '%" + searchQuery.get(0) + "%'";
            }
            if (params.get(0).equals("SEARCH")) {
                sqlQuery += " WHERE " + coluna.get(0) + "='" + searchQuery.get(0) + "'";
                if (coluna.size() > 1) sqlQuery += " OR " + coluna.get(1) + "='" + searchQuery.get(1) + "'";
            }
        }

        try {

            Statement stm = instance.getConnection().createStatement();
            ResultSet resultado = stm.executeQuery(sqlQuery);

            while (resultado.next()) {

                int idAgencia = resultado.getInt("id_agencia");
                String nome = resultado.getString("nome");
                String logradouro = resultado.getString("logradouro");

                Agencia agencia = new Agencia(idAgencia, nome, logradouro);
                listAgencia.add(agencia);

            }
        }catch(SQLException ex){
            System.out.println("Não conseguiu consultar os dados da Agencia.");
        }
        return listAgencia;
    }

    // @Override
    public void deletar(String paramsQuery) {

        String sqlQuery = "DELETE FROM agencias WHERE id_agencia=" + paramsQuery;

        try {

            Statement stm = instance.getConnection().createStatement();
            ResultSet resultado = stm.executeQuery(sqlQuery);
            System.out.println("Agencia deletada com sucesso.");

        }catch(SQLException ex){
            System.out.println("Não foi possível deletar a agencia.");
        }
    }
    // @Override
    public List<Agencia> listarTodos() {
        return null;
    }
    public List<Agencia> paginacaoAgencia(int pagina){
        List<Agencia> listaAgencias = new ArrayList<>();
        try{
            String sql = "SELECT * FROM agencias LIMIT "+ Constantes.ITENS_POR_PAGINA+"  OFFSET("+pagina+" - 1) * "+Constantes.ITENS_POR_PAGINA;
            PreparedStatement stm = instance.getConnection().prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()){
                int idAgencia = rs.getInt("id_agencia");
                String nome = rs.getString("nome");
                String logradouro = rs.getString("logradouro");

                Agencia agencia = new Agencia(idAgencia, nome, logradouro);


                listaAgencias.add(agencia);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaAgencias;
    }
}
