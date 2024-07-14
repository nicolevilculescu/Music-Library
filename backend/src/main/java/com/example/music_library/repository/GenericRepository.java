package com.example.music_library.repository;

import java.util.List;

public interface GenericRepository<T> {
    List<T> findAll();
    T findById(String id);
    void save(T entity);
    void deleteById(String id);
}
