package Repository;

import java.util.List;

public interface IGenericoRepository<E> {
    void incluir (E object);
    void consulta(String string);
    void deletar(E object);
    List<E> listarTodos();
}
