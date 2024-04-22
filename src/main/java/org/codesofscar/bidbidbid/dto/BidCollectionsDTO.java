package org.codesofscar.bidbidbid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codesofscar.bidbidbid.model.Bids;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BidCollectionsDTO {
    private Long id;
    private String collectionName;
    private String descriptions;
    private Integer stocks;
    private BigDecimal price;
    private List<Bids> bidsInCollection;
}
