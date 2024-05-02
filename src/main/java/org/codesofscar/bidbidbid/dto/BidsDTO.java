package org.codesofscar.bidbidbid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codesofscar.bidbidbid.enums.BidStatus;
import org.codesofscar.bidbidbid.model.BidCollections;
import org.codesofscar.bidbidbid.model.Users;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BidsDTO {
    private Long id;
    private Long collectionId;
    private BigDecimal price;
    private Long userId;
    private BidStatus status;
}
