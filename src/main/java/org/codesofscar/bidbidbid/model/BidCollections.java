package org.codesofscar.bidbidbid.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "collections")
public class BidCollections {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collections_id")
    @JsonIgnore
    private Long id;

    @Size(min = 3, message = "First name must be at least 3 characters")
    @Column(unique = true, nullable = false)
    private String collectionName;

    @Column
    private String descriptions;

    @Column
    private Integer stocks;

    @Column
    private BigDecimal price;

    @OneToMany
    @Column
    private List<Bids> bidsInCollection;

}
