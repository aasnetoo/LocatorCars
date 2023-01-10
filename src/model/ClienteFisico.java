package model;

public class ClienteFisico extends Pessoa {

    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "ClienteFisico{" +
                "cpf='" + cpf + '\'' +
                '}';
    }
}
