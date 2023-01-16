package model;

import java.util.Objects;

public class ClienteDTO {

    public String nome;
    public String telefone;
    public String documento;
    public String tipoCliente;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteDTO that = (ClienteDTO) o;
        return Objects.equals(documento, that.documento) && Objects.equals(tipoCliente, that.tipoCliente);
    }

    public Double valorDesconto(int dias, String tipoCliente) {

        if(tipoCliente == "PF") {
            if (dias>5){
                return 0.05;
            }else{
                return 0.0;
            }

        } else if (tipoCliente == "PJ") {
            if (dias>3){
                return 0.10;
            }else{
                return 0.0;
            }

        } else {
            System.out.println("Tipo de cliente não reconhecido");
            return 0.0;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, telefone, documento, tipoCliente);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
}
