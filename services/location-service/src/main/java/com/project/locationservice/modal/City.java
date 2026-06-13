package com.project.locationservice.modal;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "cities",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_city_code", columnNames = {"city_code"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "city_code", nullable = false, length = 3)
    private String cityCode;

    @Column(nullable = false, length = 100)
    private String countryCode;
    
    @Column(nullable = false, length = 100)
    private String country;

    @Column(name = "time_zone", nullable = false, length = 50)
    private String timeZone;

    @Size(max = 10)
    private String regionCode;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
