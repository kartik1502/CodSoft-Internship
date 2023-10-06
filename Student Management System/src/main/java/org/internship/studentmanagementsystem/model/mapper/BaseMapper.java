package org.internship.studentmanagementsystem.model.mapper;

import java.util.Collection;
import java.util.List;

public abstract class BaseMapper<E, D> {

    public abstract D convertToDto(E entity, Object... args);

    public abstract E convertToEntity(D dto, Object... args);

    public Collection<E> convertToEntity(Collection<D> dtos, Object... args) {
        return dtos.stream().map(dto -> convertToEntity(dto, args)).toList();
    }

    public Collection<D> convertToDto(Collection<E> entities, Object... args) {
        return entities.stream().map(entity -> convertToDto(entity, args)).toList();
    }

    public List<E> convertToEntityList(List<D> dtos, Object... args) {
        return dtos.stream().map(dto -> convertToEntity(dto, args)).toList();
    }

    public List<D> convertToDtoList(List<E> entities, Object... args) {
        return entities.stream().map(entity -> convertToDto(entity, args)).toList();
    }
}
