package database;

import util.Mensagens;

import java.sql.*;

public class Conexao {

    private static Conexao instance;

    private Connection conn = null;
    private Statement stm = null;
    private ResultSet rs = null;

    Mensagens mensagens = new Mensagens();

    public Conexao() {
        try {
            String usuario = "postgres";
            String senha = "abc123";
            String ipDoBanco = "localhost:5432";
            String nomeDoBanco = "produtos";
            String stringDeConexao = "jdbc:postgresql://" + ipDoBanco + "/" + nomeDoBanco;
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(stringDeConexao, usuario, senha);
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
            mensagens.MsgErroBancoDeDados();
        }
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
