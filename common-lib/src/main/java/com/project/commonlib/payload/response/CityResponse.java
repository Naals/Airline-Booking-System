package com.project.commonlib.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityResponse {

    private Long id;
    private String name;
    private String cityCode;
    private String countryCode;
    private String country;
    private String timeZone;
    private String regionCode;
}