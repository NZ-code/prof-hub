package pl.zenev.profhub.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Service<T> {
    List<T> getAll();
    Optional<T> getById(UUID uuid);

    void add(T t);
}
