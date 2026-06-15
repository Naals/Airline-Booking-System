package com.project.locationservice.mapper;

import com.project.commonlib.payload.request.AirportRequest;
import com.project.commonlib.payload.response.AirportResponse;
import com.project.locationservice.modal.Airport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.time.ZoneId;

@Mapper(componentModel = "spring", uses = {CityMapper.class})
public interface AirportMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Airport toEntity(AirportRequest request);

    @Mapping(source = "timeZoneId", target = "timeZoneId", qualifiedByName = "stringToZoneId")
    AirportResponse toResponse(Airport airport);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(AirportRequest request, @MappingTarget Airport airport);

    @org.mapstruct.Named("stringToZoneId")
    default ZoneId mapStringToZoneId(String timeZoneId) {
        if (timeZoneId == null) {
            return null;
        }
        return ZoneId.of(timeZoneId);
    }
}