package util;

import model.AgenciaDTO;

import java.util.List;
public class TablePrinter {
//    private final String tableTitle;
//    private final List<String> tableColumns;
//
//    public TablePrinter(String tableTitle, List<String> tableColumns) {
//        this.tableTitle = tableTitle;
//        this.tableColumns = tableColumns;
//    }

    public void agenciaTablePrinter(List<AgenciaDTO> listAgenciaDTO) {
        if (listAgenciaDTO.size() == 0) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "NÃO EXISTE NENHUMA AGÊNCIA CADASTRADA" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT);
            System.out.println("===================================================================================");
            System.out.println("|                                    AGÊNCIAS                                     |");
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("|   ID   |              NOME             |               LOGRADOURO               |");
            System.out.println("-----------------------------------------------------------------------------------");
            for (int i = 0; i < listAgenciaDTO.size(); i++) {
                int idAgencia = listAgenciaDTO.get(i).getId();
                String nome = listAgenciaDTO.get(i).getNome();
                String logradouro = listAgenciaDTO.get(i).getLogradouro();
                System.out.printf("| %-6s | %-29s | %-38s |%n", idAgencia, nome, logradouro);
            }
            System.out.println("===================================================================================");
            System.out.println(ConsoleColors.RESET);
        }
    }
}
