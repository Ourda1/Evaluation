package ma.projet.ex2.dao;
import java.util.List;

public interface IDao<T> {
    void create(T t);
    T update(T t);
    void delete(T t);
    T findById(int id);
    List<T> findAll();
}