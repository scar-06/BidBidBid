package org.codesofscar.bidbidbid.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codesofscar.bidbidbid.enums.BidStatus;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bids")
public class Bids {

    @Id
    @Column(name = "user_id")
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinTable(
            joinColumns = @JoinColumn(name = "bids_id"),
            inverseJoinColumns = @JoinColumn(name = "collection_id")
    )
    private BidCollections collection;

    @Column
    private BigDecimal price;

    @JsonIgnore
    private Long collectionId;

    @JsonIgnore
    private Long userId;

    @OneToOne
    @JsonIgnore
    private Users user;

    @Column
    private BidStatus status;
}
