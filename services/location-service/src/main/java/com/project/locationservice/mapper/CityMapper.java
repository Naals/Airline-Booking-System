package com.project.locationservice.mapper;

import com.project.commonlib.payload.request.CityRequest;
import com.project.commonlib.payload.response.CityResponse;
import com.project.locationservice.modal.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    City toEntity(CityRequest request);


    CityResponse toResponse(City city);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(CityRequest request, @MappingTarget City city);
}