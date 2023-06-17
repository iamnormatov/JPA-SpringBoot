package com.example.jpa.dto;

public interface SimpleCRUD<K, V> {
    ResponseDto<V> create(V dto);
    ResponseDto<V> get(K entityId);
    ResponseDto<V> update(K entityId, V dto);
    ResponseDto<V> delete(K entityId);

}
