package com.example.music_library.repository;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public abstract class GenericRepositoryImpl<T> implements GenericRepository<T> {
    private final DatabaseReference databaseReference;

    public GenericRepositoryImpl(FirebaseApp firebaseApp, String collectionName) {
        if (FirebaseApp.getApps().isEmpty()) {
            throw new IllegalStateException("FirebaseApp with name \"Music Library\" doesn't exist.");
        }

        this.databaseReference = FirebaseDatabase.getInstance().getReference(collectionName);
    }

    @Override
    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    T entity = getEntity(dataSnapshot, snapshot);
                    entities.add(entity);
                }
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return entities;
    }

    @Override
    public T findById(String id) {
        T[] entity = (T[]) new Object[1];
        CountDownLatch latch = new CountDownLatch(1);
        databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                entity[0] = getEntity(dataSnapshot);
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return entity[0];
    }

    @Override
    public void save(T entity) {
        String id = getId(entity) == null ? databaseReference.push().getKey() : getId(entity);

        Map<String, Object> entityMap = convertToMap(entity);
        databaseReference.child(id).setValueAsync(entityMap);
    }

    @Override
    public void deleteById(String id) {
        databaseReference.child(id).removeValueAsync();
    }

    protected abstract String getId(T entity);

    protected abstract T getEntity(DataSnapshot dataSnapshot, DataSnapshot snapshot);

    protected abstract T getEntity(DataSnapshot dataSnapshot);

    protected abstract Map<String, Object> convertToMap(T entity);
}
