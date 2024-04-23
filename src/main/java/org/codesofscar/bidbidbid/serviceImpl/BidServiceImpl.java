package org.codesofscar.bidbidbid.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.codesofscar.bidbidbid.dto.BidsDTO;
import org.codesofscar.bidbidbid.exception.ResourceNotFoundException;
import org.codesofscar.bidbidbid.model.BidCollections;
import org.codesofscar.bidbidbid.model.Bids;
import org.codesofscar.bidbidbid.repository.BidCollectionsRepository;
import org.codesofscar.bidbidbid.repository.BidsRepository;
import org.codesofscar.bidbidbid.repository.UserRepository;
import org.codesofscar.bidbidbid.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BidServiceImpl implements BidService {

    private final List<Bids> bidsToCollection = new ArrayList<>();

    private final BidsRepository bidsRepository;
    private final BidCollectionsRepository collectionsRepository;

    private final UserRepository userRepository;

    @Autowired
    public BidServiceImpl(BidsRepository bidsRepository, BidCollectionsRepository collectionsRepository, UserRepository userRepository) {
        this.bidsRepository = bidsRepository;
        this.collectionsRepository = collectionsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Bids> getAllBids() {
        return bidsRepository.findAll();
    }

    @Override
    public ResponseEntity<Bids> getBidById(Long bidId) {
        return new ResponseEntity<>(bidsRepository.
                findById(bidId).
                orElseThrow(() -> new ResourceNotFoundException("Bid not found")), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addBidToCollection(BidsDTO bidDto) {
//        NEED TO ADD BID TO USER HERE

        Bids bid = new ObjectMapper().convertValue(bidDto, Bids.class);
        Optional<Bids> bid1 = bidsRepository.findById(bid.getId());
        if(bid1.isPresent() ){
            return ResponseEntity.ok("Bid with ID " + bid.getId()+ " has already been added");
        }

        bidsToCollection.add(bid);

        BidCollections collection = collectionsRepository.findById(bid.getId()).
                orElseThrow(() -> new ResourceNotFoundException("Not Found"));

        collection.setBidsInCollection(bidsToCollection);

//        NEED TO SAVE ABOVE "bid" IN ITS OWN "collection"

        bidsRepository.save(bid);
        collectionsRepository.save(collection);


        return ResponseEntity.ok("Bid with ID " + bid.getId() + " added successfully");
    }

    @Override
    public ResponseEntity<?> updateBidById(Long bidId, BidsDTO bidsDTO) {
        Optional<Bids> bid = bidsRepository.findById(bidId);
        if(!bid.isPresent()){
            return new ResponseEntity<>("Bid with ID " + bidId + " not found", HttpStatus.BAD_REQUEST);
        }
        bid.get().setCollection(bidsDTO.getCollection());
        bid.get().setPrice(bidsDTO.getPrice());
        bid.get().setUser(bidsDTO.getUser());
        bid.get().setStatus(bidsDTO.getStatus());

        bidsRepository.save(bid.get());

        return ResponseEntity.ok("Bid updated successfully");
    }

    @Override
    public ResponseEntity<String> deleteBidById(Long bidId) {
        Optional<Bids> bid = bidsRepository.findById(bidId);
        if(!bid.isPresent()){
            return new ResponseEntity<>("No such item found", HttpStatus.BAD_REQUEST);
        }
        bidsRepository.delete(bid.get());
        return ResponseEntity.ok( "Bid with ID " + bidId + " is deleted successfully");
    }



}
