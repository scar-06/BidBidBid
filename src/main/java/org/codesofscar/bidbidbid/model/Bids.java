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
    @Column(name = "bids_id")
    @JsonIgnore
    private Long id;

//    @ManyToOne
//    @JsonIgnore
//    @JoinTable(
//            joinColumns = @JoinColumn(name = "bids_id"),
//            inverseJoinColumns = @JoinColumn(name = "collection_id")
//    )
//    private BidCollections collection;


    private Long collectionId;

    @Column
    private BigDecimal price;


//    @OneToOne
//    @JsonIgnore
//    private Users user;

    private Long userId;

    @Column
    private BidStatus status;
}
