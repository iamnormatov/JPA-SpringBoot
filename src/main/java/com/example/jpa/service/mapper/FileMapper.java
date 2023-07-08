package com.example.jpa.service.mapper;

import com.example.jpa.dto.FileDto;
import com.example.jpa.model.FileModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class FileMapper {
    public abstract FileDto toDto(FileModel fileModel);

    @Mapping(target = "fileId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateFile(FileDto fileDto, @MappingTarget FileModel fileModel);
}
