package ma.project.ex1.dao;


import java.util.List;

public interface IDao<T> {
    boolean create(T o);
    boolean update(T o);
    boolean delete(T o);
    T getById(Long id);
    List<T> getAll();
}



