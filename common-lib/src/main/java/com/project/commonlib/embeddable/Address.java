package com.project.commonlib.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.*;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    private String street;
    private String postalCode;
}
