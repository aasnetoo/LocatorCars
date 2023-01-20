package util;

import model.Agencia;
import model.Aluguel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class TablePrinter {
    private final int receiptWidth = 80;
    private final int leftSpace = 2;
    private final int rightSpace = 1;
    List<String> bodyHeaders = new ArrayList<>(List.of("VEICULO","MODELO","R$","DIAS"));
    int bodyColumnsSize = (int) Math.floor((double)(receiptWidth-2) / (bodyHeaders.size()+1));
    public void agenciaTablePrinter(List<Agencia> listAgencia) {
        if (listAgencia.size() == 0) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "NÃO EXISTE NENHUMA AGÊNCIA CADASTRADA" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+"===================================================================================");
            System.out.println("|                                    AGÊNCIAS                                     |");
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("|   ID   |              NOME             |               LOGRADOURO               |");
            System.out.println("-----------------------------------------------------------------------------------");
            for (int i = 0; i < listAgencia.size(); i++) {
                int idAgencia = listAgencia.get(i).getId();
                String nome = listAgencia.get(i).getNome();
                String logradouro = listAgencia.get(i).getLogradouro();
                System.out.printf("| %-6s | %-29s | %-38s |%n", idAgencia, nome, logradouro);
            }
            System.out.println("==================================================================================="+ConsoleColors.RESET);
        }
    }
    public void printReceipt(Aluguel aluguel) {

        String nomeAgencia = "AGÊNCIA:     " + aluguel.getAgenciaRetirada().getNome();
        String logradouroAgencia = "LOGRADOURO:  " + aluguel.getAgenciaRetirada().getLogradouro();
        String nomeCliente = "CLIENTE:     " + aluguel.getCliente().getNome();
        Date date = aluguel.getDataInicio();
        BigDecimal valorAluguel;

        String topLine = generateLineSeparator("-", true).concat("\n");
        String sectionSeparator = generateLineSeparator("=", false);
        String bodySeparator = generateLineSeparator("-", false);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        String dataAluguel = "DATA DO ALUGUEL  : ".concat(dateFormatter.format(date)).concat(" / ").concat(aluguel.getHorarioAgendado().toString());
        String dataDevolucao = "DATA DE DEVOLUÇÃO: ".concat(dateFormatter.format(date)).concat(" / ").concat(aluguel.getHorarioDevolucao().toString());

        StringBuilder receipt = new StringBuilder();
        // HEADER
        receipt.append(topLine);
        receipt.append("| ").append(nomeAgencia).append(getRemainingBlankSpace(nomeAgencia));
        receipt.append("| ").append(logradouroAgencia).append(getRemainingBlankSpace(logradouroAgencia));
        receipt.append("| ").append(nomeCliente).append(getRemainingBlankSpace(nomeCliente));
        receipt.append("| ").append(dataAluguel).append(getRemainingBlankSpace(dataAluguel));
        receipt.append("| ").append(dataDevolucao).append(getRemainingBlankSpace(dataDevolucao));
        receipt.append(sectionSeparator);

        // BODY
        receipt.append(generateBodyColumns());
        receipt.append(bodySeparator);
        receipt.append(generateBodyData(aluguel));
        receipt.append(bodySeparator);
        receipt.append(generateBodyDiscount(aluguel));
        receipt.append(generateBodyTotalPrice(aluguel));

        //FOOTER
        receipt.append(generateFooter());
        System.out.println(receipt);
    }

    private String generateLineSeparator(String separator, boolean isHeader) {
        if (isHeader) {
            return separator.repeat(receiptWidth);
        }
        return "|".concat(separator.repeat(receiptWidth - 2)).concat("|\n");
    }

    private String getRemainingBlankSpace(String str) {
        int strLength = str.length();
        return " ".repeat(receiptWidth - leftSpace - strLength - rightSpace).concat("|\n");
    }

    private String generateBodyColumns() {
        StringBuilder bodyColumn = new StringBuilder();
        bodyColumn.append("|");
        bodyColumn.append(" ".repeat(bodyColumnsSize));
        for (int i = 0; i < bodyHeaders.size(); i++) {
            bodyColumn.append(centerString(bodyColumnsSize, bodyHeaders.get(i)));
        }
        bodyColumn.append(fillRemainingBlankSpace(bodyColumn.toString()));
        bodyColumn.append("|\n");
        return bodyColumn.toString();
    }

    private String generateBodyData(Aluguel aluguel) {
        List<String> bodyDataList = new ArrayList<>(List.of(
                aluguel.getVeiculo().getTipo(),
                aluguel.getVeiculo().getModelo(),
                aluguel.getValorAluguel().toString(),
                String.valueOf(aluguel.getDiasAlugados())
        ));
        StringBuilder bodyData = new StringBuilder();
        bodyData.append("|");
        bodyData.append(" ".repeat(bodyColumnsSize));
        for (int i = 0; i < bodyDataList.size(); i++) {
            bodyData.append(centerString(bodyColumnsSize, bodyDataList.get(i)));
        }
        bodyData.append(fillRemainingBlankSpace(bodyData.toString()));
        bodyData.append("|\n");
        return bodyData.toString();
    }

    private String generateBodyDiscount(Aluguel aluguel) {
        int diasAlugados = aluguel.getDiasAlugados();
        String desconto;
        if (diasAlugados > 5) {
            desconto = "5%";
        } else {
            if (diasAlugados > 3) {
                desconto = "10%";
            } else {
                desconto = "-";
            }
        }
        StringBuilder bodyDiscount = new StringBuilder();
        int numberOfBodyColumns = bodyHeaders.size() + 1;
        bodyDiscount.append("|");
        bodyDiscount.append(" ".repeat((numberOfBodyColumns-3)*bodyColumnsSize));
        bodyDiscount.append(alignRightString(2*bodyColumnsSize, "Desconto:"));
        bodyDiscount.append("   ").append(desconto);
        bodyDiscount.append(fillRemainingBlankSpace(bodyDiscount.toString()));
        bodyDiscount.append("|\n");
        return bodyDiscount.toString();
    }
    private String generateBodyTotalPrice(Aluguel aluguel) {
        BigDecimal valorFinal = null;
        int diasAlugados = aluguel.getDiasAlugados();
        if (diasAlugados > 5) {
            valorFinal = aluguel.getValorAluguel().multiply(new BigDecimal("0.95"));
        } else {
            if (diasAlugados > 3) {
                valorFinal = aluguel.getValorAluguel().multiply(new BigDecimal("0.90"));
            }
        }
        StringBuilder bodyPrice = new StringBuilder();
        int numberOfBodyColumns = bodyHeaders.size() + 1;
        bodyPrice.append("|");
        bodyPrice.append(" ".repeat((numberOfBodyColumns-3)*bodyColumnsSize));
        bodyPrice.append(alignRightString(2*bodyColumnsSize, "Valor Total (R$):"));
        bodyPrice.append("   ").append(valorFinal);
        bodyPrice.append(fillRemainingBlankSpace(bodyPrice.toString()));
        bodyPrice.append("|\n");
        return bodyPrice.toString();
    }

    private String alignRightString(int sectionSize, String str) {
        StringBuffer alignRightStringBuffer = new StringBuffer();
        alignRightStringBuffer.append(" ".repeat((int) (sectionSize-str.length())));
        alignRightStringBuffer.append(str);
        return alignRightStringBuffer.toString();
    }

    private String centerString(int sectionSize, String str) {
        StringBuffer centeredString = new StringBuffer();
        centeredString.append(" ".repeat((int) (sectionSize-str.length())/2));
        centeredString.append(str);
        centeredString.append(" ".repeat((int) (sectionSize-str.length())/2));
        return centeredString.toString();
    }
    private String generateFooter() {
        String lineSeparator = generateLineSeparator("=", false);
        String footerText = "OBRIGADO E VOLTE SEMPRE!";

        int leftSpace = (int) Math.floor((double)(receiptWidth - footerText.length() - 2) / 2);
        int rightSpace = receiptWidth - footerText.length() - 2 - leftSpace;

        StringBuilder footer = new StringBuilder();
        footer.append(lineSeparator);
        footer.append("|").append(" ".repeat(leftSpace)).append(footerText).append(" ".repeat(rightSpace)).append("|\n");
        footer.append(lineSeparator);

        return footer.toString();
    }

    private String fillRemainingBlankSpace(String str) {
        StringBuffer blankSpace = new StringBuffer();
        blankSpace.append(" ".repeat(receiptWidth-1-str.length()));
        return blankSpace.toString();
    }
}
