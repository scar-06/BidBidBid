package org.codesofscar.bidbidbid.controller;

import org.codesofscar.bidbidbid.model.BidCollections;
import org.codesofscar.bidbidbid.model.Bids;
import org.codesofscar.bidbidbid.serviceImpl.BidCollectionServiceImpl;
import org.codesofscar.bidbidbid.serviceImpl.BidServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/collections")
public class BidCollectionsController {

    private BidCollectionServiceImpl collectionService;
    private BidServiceImpl bidService;

    public BidCollectionsController (BidCollectionServiceImpl collectionService, BidServiceImpl bidService) {
        this.collectionService = collectionService;
        this.bidService = bidService;
    }

    @GetMapping("/findcollection/{collectionId}")
    public ResponseEntity<BidCollections> getCollection(Long collectionId) {
        return collectionService.getCollectionById(collectionId);
    }

    @GetMapping("/allcollections")
    List<BidCollections> allCollections () {
        return collectionService.getAllCollections();
    }



}
