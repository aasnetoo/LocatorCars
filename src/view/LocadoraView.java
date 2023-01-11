package view;

import controller.LocadoraController;
import model.CarroDTO;

import java.sql.SQLException;
import java.util.Scanner;
import util.Constantes;

import static java.lang.System.exit;

public class LocadoraView {

    Scanner scan = new Scanner(System.in);

    LocadoraController controller = new LocadoraController();

    public LocadoraView() throws SQLException {
    }

    public String opcaoMenu() {
        System.out.println("---------- MENU ----------");
        System.out.println("1 - Adicionar Produto");
        System.out.println("2 - Listar");
        System.out.println("3 - Editar Produto");
        System.out.println("4 - Remover um produto");
        System.out.println("5 - Buscar por nome");
        System.out.println("6 - Sair do Programa");

        return scan.nextLine();
    }
    public void menu() {
        boolean continueMenu = true;
        while (continueMenu) {
            String option = opcaoMenu();
            try {
                switch (option) {
                    case Constantes.ADICIONAR_CARRO -> adicionarCarro(informacoesCarro());
//                    case Constantes.LISTAR_PRODUTO -> controller.listarProduto();
//                    case Constantes.EDITAR_PRODUTO -> System.out.println();
//                    case Constantes.REMOVER_PRODUTO -> controller.removerProduto();
//                    case Constantes.BUSCA_POR_NOME -> controller.buscarPorNome(nomeBusca());
                    case Constantes.SAIR_PROGRAMA -> {
                        continueMenu = false;
//                        controller.sairPrograma();
                        exit(0);
                    } // 6
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public CarroDTO informacoesCarro(){
        System.out.println("Qual a placa do carro: ");
        String placaCarro = scan.nextLine();
        System.out.println("Qual o modelo do carro: ");
        String modeloCarro = scan.nextLine();
        System.out.println("Qual a potencia do carro: ");
        Double potenciaCarro = scan.nextDouble();
        scan.nextLine();

        CarroDTO novoCarroDTO = new CarroDTO();
        novoCarroDTO.setPlaca(placaCarro);
        novoCarroDTO.setModelo(modeloCarro);
        novoCarroDTO.setPotencia(potenciaCarro);


        return novoCarroDTO;
    }


    public void adicionarCarro(CarroDTO novoCarroDTO){
        controller.adicionarCarro(novoCarroDTO);

    }




}
