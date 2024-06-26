package org.codesofscar.bidbidbid.service;

import org.codesofscar.bidbidbid.dto.BidCollectionsDTO;
import org.codesofscar.bidbidbid.model.BidCollections;
import org.codesofscar.bidbidbid.model.Bids;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BidCollectionsService {
    List<BidCollections> getAllCollections ();

    BidCollections getCollectionById (Long collectionsId);


    List<Bids> getAllBidsInCollection(Long collectionId);

    ResponseEntity<String> addCollection (BidCollectionsDTO collectionsDTO);


    ResponseEntity<?> updateCollectionById(Long collectionId, BidCollectionsDTO collectionsDTO);

    ResponseEntity<String> deleteCollectionById(Long collectionId);
}
