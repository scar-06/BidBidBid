package org.codesofscar.bidbidbid.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codesofscar.bidbidbid.enums.BidStatus;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bids")
public class Bids {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonIgnore
    private Long id;

    @ManyToOne
    private BidCollections collection;

    @Column
    private BigDecimal price;

    @OneToOne
    private Users user;

    private BidStatus status;
}
