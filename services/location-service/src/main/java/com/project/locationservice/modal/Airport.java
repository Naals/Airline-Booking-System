package com.project.locationservice.modal;

import com.project.commonlib.embeddable.Address;
import com.project.commonlib.embeddable.GeoCode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "airports",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_airport_code", columnNames = {"airport_code"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "iata_code", length = 3)
    private String iataCode;

    @Column(nullable = false, length = 150)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false, foreignKey = @ForeignKey(name = "fk_airport_city"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private City city;

    @Embedded
    private Address address;

    @Embedded
    private GeoCode geoCode;

    @Column(name="time_zone_id", length = 50)
    private String timeZoneId;

    @Column(nullable = false)
    @Builder.Default
    private boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}