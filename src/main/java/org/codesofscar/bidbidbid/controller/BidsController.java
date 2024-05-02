package org.codesofscar.bidbidbid.controller;

import org.codesofscar.bidbidbid.dto.BidsDTO;
import org.codesofscar.bidbidbid.model.Bids;
import org.codesofscar.bidbidbid.serviceImpl.BidServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bids")
public class BidsController {
    private BidServiceImpl bidService;

    @Autowired
    public BidsController(BidServiceImpl bidService) {
        this.bidService = bidService;
    }

    @GetMapping("/allbids")
    List<Bids> allBids () {
        return bidService.getAllBids();
    }

    @GetMapping("/findbid/{bidId}")
    public ResponseEntity<Bids> getBid(@PathVariable Long bidId) {
        return bidService.getBidById(bidId);
    }

    @DeleteMapping("/deletebid/{bidId}")
    public ResponseEntity<String> deleteBid(@PathVariable Long bidId) {
        return bidService.deleteBidById(bidId);
    }

    @PutMapping("/updatebid/{bidId}")
    public ResponseEntity<?> updateBid(@PathVariable Long bidId,@RequestBody BidsDTO bidsDTO) {
        return ResponseEntity.ok(bidService.updateBidById(bidId, bidsDTO));
    }

    @PostMapping("/addbid")
    public ResponseEntity<String> addBid(@RequestBody BidsDTO bidsDTO) {
        return bidService.addBidToCollection(bidsDTO);
    }

    @PostMapping("/acceptbid/{bidId}")
    public ResponseEntity<String> acceptBid(@PathVariable Long bidId) {
        return bidService.acceptBid(bidId);
    }

}
