package org.codesofscar.bidbidbid.controller;

import org.codesofscar.bidbidbid.dto.BidCollectionsDTO;
import org.codesofscar.bidbidbid.model.BidCollections;
import org.codesofscar.bidbidbid.model.Bids;
import org.codesofscar.bidbidbid.serviceImpl.BidCollectionServiceImpl;
import org.codesofscar.bidbidbid.serviceImpl.BidServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<BidCollections> getCollection(@PathVariable Long collectionId) {
//        return collectionService.getCollectionById(collectionId);
        BidCollections response = collectionService.getCollectionById(collectionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/allcollections")
    List<BidCollections> allCollections () {
        return collectionService.getAllCollections();
    }

    @DeleteMapping("/deletecollection/{collectionId}")
    public ResponseEntity<String> deleteCollection(@PathVariable Long collectionId) {

        return collectionService.deleteCollectionById(collectionId);

    }

    @GetMapping("/bidsincollection/{collectionId}")
    List<Bids> bidsInCollection (@PathVariable Long collectionId) {
        return collectionService.getAllBidsInCollection(collectionId);
    }


    @PostMapping("/addcollection")
    public ResponseEntity<String> addCollection (@RequestBody BidCollectionsDTO collectionDTO) {
        return collectionService.addCollection(collectionDTO);
    }

    @PutMapping("/updatecollection/{collectionId}")
    public ResponseEntity<?> updateCollectionById(@PathVariable Long collectionId, @RequestBody BidCollectionsDTO collectionsDTO) {
        return collectionService.updateCollectionById(collectionId, collectionsDTO);
    }

}
