package pl.zenev.profhub.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Repository<T> {
    List<T> getAll();
    Optional<T> getById(UUID uuid);
    void add(T t);

}
