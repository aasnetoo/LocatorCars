package exception;

public class VeiculoExisteException extends RuntimeException {

    public VeiculoExisteException(){
        super("O carro adicionado já existe no Banco de Dados");
    }
}

    

