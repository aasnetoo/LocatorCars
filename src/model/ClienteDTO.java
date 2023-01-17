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

    @Override
    public int hashCode() {
        return Objects.hash(nome, telefone, documento, tipoCliente);
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
                "nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", documento='" + documento + '\'' +
                ", tipoCliente='" + tipoCliente + '\'' +
                '}';
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
