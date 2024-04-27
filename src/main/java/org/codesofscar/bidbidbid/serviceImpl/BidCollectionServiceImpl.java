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

//    private final BidsRepository bidsRepository;
//    private final BidCollectionsRepository collectionsRepository;
//
//    private final UserRepository userRepository;
//
//
//    @Autowired
//    public BidCollectionServiceImpl(BidsRepository bidsRepository, BidCollectionsRepository collectionsRepository, UserRepository userRepository) {
//        this.bidsRepository = bidsRepository;
//        this.collectionsRepository = collectionsRepository;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public List<BidCollections> getAllCollections() {
//        return collectionsRepository.findAll();
//    }
//
//    @Override
//    public ResponseEntity<BidCollections> getCollectionById(Long collectionsId) {
//        return new ResponseEntity<>(collectionsRepository.
//                findById(collectionsId).
//                orElseThrow(() -> new ResourceNotFoundException("Colleection not found")), HttpStatus.OK);
//    }
//
//    @Override
//    public List<Bids> getAllBidsInCollections(Long id) {
//        Optional<BidCollections> collectionOfBids = collectionsRepository.findById(id);
//        return collectionOfBids.get().getBidsInCollection();
//    }
//
//    public ResponseEntity<String> addCollection (BidCollectionsDTO collectionDTO) {
//
//        BidCollections collection = new ObjectMapper().convertValue(collectionDTO, BidCollections.class);
//        Optional<BidCollections> collection1 = collectionsRepository.findById(collection.getId());
//        if(collection1.isPresent() ){
//            return ResponseEntity.ok("Collection with name " + collection.getCollectionName()+ " has already been added");
//        }
//
////        collection.setCollectionName(user);
//        collectionsRepository.save(collection);
//        return ResponseEntity.ok(collection.getCollectionName() + " added successfully");
//    }
//
//
//    @Override
//    public ResponseEntity<?> updateCollectionById(Long collectionId, BidCollectionsDTO collectionsDTO) {
//        Optional<BidCollections> collection = collectionsRepository.findById(collectionId);
//        if(!collection.isPresent()){
//            return new ResponseEntity<>("Collection with ID " + collectionId + " not found", HttpStatus.BAD_REQUEST);
//        }
//        collection.get().setCollectionName(collectionsDTO.getCollectionName());
//        collection.get().setBidsInCollection(collectionsDTO.getBidsInCollection());
//        collection.get().setPrice(collectionsDTO.getPrice());
//        collection.get().setStocks(collectionsDTO.getStocks());
//        collection.get().setDescriptions(collectionsDTO.getDescriptions());
//
//        collectionsRepository.save(collection.get());
//
//        return ResponseEntity.ok("Collection updated successfully");
//    }
//
//    @Override
//    public ResponseEntity<String> deleteCollectionById(Long collectionId) {
//        Optional<BidCollections> collection = collectionsRepository.findById(collectionId);
//        if(!collection.isPresent()){
//            return new ResponseEntity<>("No such item found", HttpStatus.BAD_REQUEST);
//        }
//        collectionsRepository.delete(collection.get());
//        return ResponseEntity.ok(collection.get().getCollectionName() + "with ID " + collectionId + " is deleted successfully");
//    }

}
