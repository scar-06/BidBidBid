package org.codesofscar.bidbidbid.controller;

import org.codesofscar.bidbidbid.model.Bids;
import org.codesofscar.bidbidbid.serviceImpl.BidServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bids")
public class BidsController {
    private BidServiceImpl bidService;

    @GetMapping("/allbids")
    List<Bids> getAllBids () {
        return bidService.getAllBids();
    }
}
