package exception;

public class CarroExisteException extends RuntimeException{

    public CarroExisteException(){
        super("O carro adicionado já existe no Banco de Dados");
    }


}
