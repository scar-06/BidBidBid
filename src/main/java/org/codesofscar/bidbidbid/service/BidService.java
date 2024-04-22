package org.codesofscar.bidbidbid.service;

import org.codesofscar.bidbidbid.dto.BidsDTO;
import org.codesofscar.bidbidbid.model.BidCollections;
import org.codesofscar.bidbidbid.model.Bids;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BidService {
    List<Bids> getAllBids();

    ResponseEntity<Bids> getBidById(Long bidId);

    ResponseEntity<String> addBid (BidsDTO bidDto);

    ResponseEntity<?> updateBidById(Long bidId, BidsDTO bidsDTO);

    ResponseEntity<String> deleteBidById(Long bidId);
}
