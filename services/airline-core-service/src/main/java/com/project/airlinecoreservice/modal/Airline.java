package com.project.airlinecoreservice.modal;

import com.project.commonlib.embeddable.Support;
import com.project.commonlib.enums.AirlineStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(
        name = "airlines",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_airline_iata", columnNames = {"iata_code"}),
                @UniqueConstraint(name = "uk_airline_icao", columnNames = {"icao_code"})
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "iata_code", unique = true, nullable = false, length = 3)
    private String iataCode;

    @Column(name = "icao_code", unique = true, nullable = false, length = 4)
    private String icaoCode;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Long ownerId;

    @Embedded
    private Support support;

    @Column(length = 50)
    private String alias;

    @Column(name = "logo_url")
    private String logoUrl;

    private String website;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private AirlineStatus status = AirlineStatus.ACTIVE;

    private String alliance;

    private Long headquartersCityId;

    private Long updatedById;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private java.time.Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private java.time.Instant updatedAt;
}