package Repository;

import controller.LocadoraController;
import database.Conexao;
import model.Aluguel;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AluguelDAO {

    Conexao instance = Conexao.getInstance();

    public AluguelDAO() throws SQLException {
    }

    public void salvarAluguel(Aluguel aluguel) {
        String query = "INSERT into alugueis (cliente_nome, cliente_documento, veiculo_modelo, placa_veiculo, data_inicio, data_devolucao, horario_agendado, horario_devolucao, valor, tipo_cliente, agencia_retirada_nome, agencia_devolucao_nome) values('"
                + aluguel.getCliente().nome + "', '"
                + aluguel.getCliente().documento + "', '"
                + aluguel.getVeiculo().getModelo() + "', '"
                + aluguel.getVeiculo().getPlaca() + "', '"
                + aluguel.getDataInicio() + "', '"
                + aluguel.getDataDevolucao() + "', '"
                + aluguel.getHorarioAgendado() + "', '"
                + aluguel.getHorarioDevolucao() + "', '"
                + aluguel.getValorAluguel() + "', '"
                + aluguel.getCliente().tipoCliente + "', '"
                + aluguel.getAgenciaRetirada().getNome() + "', '"
                + aluguel.getAgenciaDevolucao().getNome()
                + "')";
        try {
            Statement stm = instance.getConnection().createStatement();
            stm.executeUpdate(query);
            System.out.println("Aluguel adicionado com sucesso. ");
        } catch (SQLException ex) {
            System.out.println("Nao conseguiu executar o DML\n" + query);
        }
    }

    public Aluguel pegarAluguelPorId(int id){
        String consulta = "SELECT * FROM alugueis WHERE id = "+id;
        Aluguel aluguel = new Aluguel();
        try {
            LocadoraController locadoraController = new LocadoraController();
            Statement stm = instance.getConnection().createStatement();
            ResultSet resultado = stm.executeQuery(consulta);
            while(resultado.next()) {

                aluguel.setId(resultado.getInt("id"));
                aluguel.setCliente(locadoraController.retornarCliente(resultado.getString("cliente_documento")));
                aluguel.setVeiculo(locadoraController.obterVeiculoPorPlaca(resultado.getString("placa_veiculo")));
                aluguel.setDataInicio(resultado.getDate("data_inicio"));
                aluguel.setDataDevolucao(resultado.getDate("data_devolucao"));
                aluguel.setHorarioAgendado(resultado.getTime("horario_agendado"));
                aluguel.setHorarioDevolucao(resultado.getTime("horario_devolucao"));
                aluguel.setAgenciaRetirada(locadoraController.consultarAgenciaPorNome(resultado.getString("agencia_retirada_nome")));
                aluguel.setAgenciaDevolucao(locadoraController.consultarAgenciaPorNome(resultado.getString("agencia_devolucao_nome")));
                aluguel.setValorAluguel(BigDecimal.valueOf((resultado.getDouble("valor"))));

            }

        }catch(SQLException ex){
            System.out.println("N??o conseguiu consultar os dados do Aluguel.");
        }
        return aluguel;
    }

}