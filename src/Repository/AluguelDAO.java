package Repository;

import database.Conexao;
import model.AluguelDTO;

import java.sql.SQLException;
import java.sql.Statement;

public class AluguelDAO {

    Conexao instance = Conexao.getInstance();

    public AluguelDAO() throws SQLException {
    }

    public void incluirAluguel(AluguelDTO aluguelDTO) {
        String query = "INSERT into alugueis (id, cliente_nome, cliente_documento, veiculo, placa_veiculo, data_inicio, data_devolucao, horario_agendado, valor) values('"
                + aluguelDTO.getId() + "', '"
                + aluguelDTO.getCliente().nome + "', '"
                + aluguelDTO.getCliente().documento + "', '"
                + aluguelDTO.getVeiculo().getModelo() + "', '"
                + aluguelDTO.getVeiculo().getPlaca() + "', '"
                + aluguelDTO.getDataInicio() + "', '"
                + aluguelDTO.getDataDevolucao() + "', '"
                + aluguelDTO.getHorarioAgendado() + "', '"
                + aluguelDTO.getAgenciaRetirada() + "', '"
                + aluguelDTO.getAgenciaDevolucao() + "', '"
                + aluguelDTO.getValorAluguel() + "', '"
                + aluguelDTO.getCliente().tipoCliente + "', '"
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
