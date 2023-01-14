package exception;

public class ClienteExisteException extends RuntimeException {

    public ClienteExisteException(){
        super("O cliente adicionado já existe no Banco de Dados");
    }

}
