package util;

public class Mensagens {

        public void addMsg(String produto){
            System.out.println("O produto "+produto+" foi adicionado com sucesso.");
        }
        public void MsgErroBancoDeDados(){
            System.out.println("Erro: Não conseguiu conectar no BD.");
        }

        public void voltandoMenu(){
            System.out.println("Voltando ao menu...");
        }


}
