package com.example.praktikum2.mapper;

import com.example.praktikum2.dto.KtpDto;
import com.example.praktikum2.entity.Ktp;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface KtpMapper {
    KtpMapper INSTANCE = Mappers.getMapper(KtpMapper.class);

    KtpDto toDto(Ktp ktp);

    Ktp toEntity(KtpDto ktpDto);

    @org.mapstruct.Mapping(target = "id", ignore = true)
    void updateEntityFromDto(KtpDto ktpDto, @MappingTarget Ktp ktp);
}
