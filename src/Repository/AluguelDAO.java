package Repository;

import database.Conexao;
import model.Aluguel;

import java.sql.SQLException;
import java.sql.Statement;

public class AluguelDAO {

    Conexao instance = Conexao.getInstance();

    public AluguelDAO() throws SQLException {
    }


    public void salvarAluguel(Aluguel aluguel) {
        String query = "INSERT into alugueis (id, cliente_nome, cliente_documento, veiculo_modelo, placa_veiculo, data_inicio, data_devolucao, horario_agendado, valor, tipo_cliente, agencia_retirada_nome, agencia_devolucao_nome) values('"
                + aluguel.getId() + "', '"
                + aluguel.getCliente().nome + "', '"
                + aluguel.getCliente().documento + "', '"
                + aluguel.getVeiculo().getModelo() + "', '"
                + aluguel.getVeiculo().getPlaca() + "', '"
                + aluguel.getDataInicio() + "', '"
                + aluguel.getDataDevolucao() + "', '"
                + aluguel.getHorarioAgendado() + "', '"
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

}
