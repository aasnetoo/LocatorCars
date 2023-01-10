package database;

import java.sql.*;
import util.Mensagens;

public class Conexao {

    private static Conexao instance;

    private Connection conn = null;
    private Statement stm = null;
    private ResultSet rs = null;

    Mensagens mensagens = new Mensagens();

    public Conexao() {
        try {
            String usuario = "postgres";
            String senha = "waer9a0s";
            String ipDoBanco = "localhost:5432";
            String nomeDoBanco = "produtos";
            String stringDeConexao = "jdbc:postgresql://" + ipDoBanco + "/" + nomeDoBanco;
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(stringDeConexao, usuario, senha);
            System.out.println("Conectou no banco de dados.");
        } catch (SQLException | ClassNotFoundException ex) {
            mensagens.MsgErroBancoDeDados();
        }
    }



    public ResultSet executarConsulta(String consulta) throws SQLException {

        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(consulta);
        } catch (SQLException ex) {
            System.out.println("Não conseguiu executar a consulta\n" + consulta);
            //Caso ocorra algum erro desconecta do banco de dados.
        }finally{
            desconectar();
        }

        return rs;
    }

    public ResultSet executarQuery (String query){
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Não conseguiu executar a listagem\n" + query);
            //Caso ocorra algum erro desconecta do banco de dados.
            desconectar();
        }

        return rs;
    }



    public boolean executeQuery(String query) {
        boolean ok = false;
        try {
            stm = conn.createStatement();
            stm.executeUpdate(query);
            ok = true;
            System.out.println("O produto foi adicionado com sucesso. ");
        } catch (SQLException ex) {
            System.out.println("Nao conseguiu executar o DML\n" + query);
        }finally{
            desconectar();
        }

        return ok;
    }

    public void desconectar() {
        fecharResultSet(this.rs);
        fecharStatement(this.stm);
        fecharConnection(this.conn);
    }

    public void fecharConnection(Connection conn) {
        try {
            if(conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Desconectou do banco de dados.");
            }
        } catch (SQLException ex) {
            System.out.println("Nao conseguiu desconectar do BD.");
        }
    }

    public void fecharStatement(Statement stm) {
        try {
            if(stm != null && !stm.isClosed()) {
                stm.close();
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar o procedimento de consulta.");
        }
    }

    public void fecharResultSet(ResultSet resultado) {
        try {
            if(resultado != null && !resultado.isClosed()) {
                resultado.close();
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar o resultado da consulta.");
        }
    }


    public Connection getConnection() {
        return conn;
    }
    public static Conexao getInstance() throws SQLException {
        if (instance == null) {
            instance = new Conexao();
        }
        return instance;
    }

}
