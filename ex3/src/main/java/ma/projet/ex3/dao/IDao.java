package ma.projet.ex3.dao;

import java.util.List;

public interface IDao<T> {
    void create(T t);
    void update(T t);
    void delete(T t);
    T findById(Integer id);
    List<T> findAll();
}
