package de.hse.todosbackend;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITodoRepository extends CrudRepository<Todo, Integer> {
    Todo findById(int id);
    List<Todo>  findByIsActiveOrderByFinishUntilAsc(boolean isActive);
}
