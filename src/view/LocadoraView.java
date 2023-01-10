package view;

import controller.LocadoraController;
import model.CarroDTO;

import java.util.Scanner;

public class LocadoraView {

    Scanner scan = new Scanner(System.in);

    LocadoraController controller = new LocadoraController();

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
