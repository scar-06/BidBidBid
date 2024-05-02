package org.codesofscar.bidbidbid.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.codesofscar.bidbidbid.dto.BidCollectionsDTO;
import org.codesofscar.bidbidbid.exception.ResourceNotFoundException;
import org.codesofscar.bidbidbid.model.BidCollections;
import org.codesofscar.bidbidbid.model.Bids;
import org.codesofscar.bidbidbid.repository.BidCollectionsRepository;
import org.codesofscar.bidbidbid.repository.BidsRepository;
import org.codesofscar.bidbidbid.repository.UserRepository;
import org.codesofscar.bidbidbid.service.BidCollectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidCollectionServiceImpl implements BidCollectionsService{

    private final BidsRepository bidsRepository;
    private final BidCollectionsRepository collectionsRepository;

    private final UserRepository userRepository;


    @Autowired
    public BidCollectionServiceImpl(BidsRepository bidsRepository, BidCollectionsRepository collectionsRepository, UserRepository userRepository) {
        this.bidsRepository = bidsRepository;
        this.collectionsRepository = collectionsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<BidCollections> getAllCollections() {
        return collectionsRepository.findAll();
    }

    @Override
    public BidCollections getCollectionById(Long collectionId) {
        return collectionsRepository.findById(collectionId).
                orElseThrow(() -> new ResourceNotFoundException("Collection not found"));
    }

    @Override
    public ResponseEntity<String> deleteCollectionById(Long collectionId) {
        Optional<BidCollections> collection = collectionsRepository.findById(collectionId);
        if(!collection.isPresent()){
            return new ResponseEntity<>("No such item found", HttpStatus.BAD_REQUEST);
        }
        collectionsRepository.delete(collection.get());
        return ResponseEntity.ok(collection.get().getCollectionName() + " with ID " + collectionId + " is deleted successfully");
    }

    @Override
    public List<Bids> getAllBidsInCollection(Long collectionId) {
        Optional<BidCollections> collectionOfBids = collectionsRepository.findById(collectionId);
        return collectionOfBids.get().getBidsInCollection();
    }

    public ResponseEntity<String> addCollection (BidCollectionsDTO collectionDTO) {

        BidCollections collection = new ObjectMapper().convertValue(collectionDTO, BidCollections.class);
        Optional<BidCollections> collection1 = collectionsRepository.findById(collection.getId());
        if(collection1.isPresent() ){
            return ResponseEntity.ok("Collection with name " + collection.getCollectionName()+ " has already been added");
        }

//        collection.setCollectionName(user);
        collectionsRepository.save(collection);

        List<Bids> bidsList = collectionDTO.getBidsInCollection();

        if (bidsList != null) {
            for (Bids bids : bidsList) {
                Bids saveBids = new Bids();
                saveBids.setCollectionId(bids.getCollectionId());
                saveBids.setUserId(bids.getUserId());
                saveBids.setId(bids.getId());
                saveBids.setStatus(bids.getStatus());
                saveBids.setPrice(bids.getPrice());

                bidsRepository.save(saveBids);
            }
        }
        return ResponseEntity.ok(collection.getCollectionName() + " added successfully");
    }


    @Override
    public ResponseEntity<?> updateCollectionById(Long collectionId, BidCollectionsDTO collectionsDTO) {
        Optional<BidCollections> collection = collectionsRepository.findById(collectionId);
        if(!collection.isPresent()){
            return new ResponseEntity<>("Collection with ID " + collectionId + " not found", HttpStatus.BAD_REQUEST);
        }
        collection.get().setCollectionName(collectionsDTO.getCollectionName());
        collection.get().setBidsInCollection(collectionsDTO.getBidsInCollection());
        collection.get().setPrice(collectionsDTO.getPrice());
        collection.get().setStocks(collectionsDTO.getStocks());
        collection.get().setDescriptions(collectionsDTO.getDescriptions());

        collectionsRepository.save(collection.get());

        return ResponseEntity.ok("Collection updated successfully");
    }



}
