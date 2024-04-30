package org.codesofscar.bidbidbid.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.codesofscar.bidbidbid.dto.BidsDTO;
import org.codesofscar.bidbidbid.enums.BidStatus;
import org.codesofscar.bidbidbid.enums.Roles;
import org.codesofscar.bidbidbid.exception.ResourceNotFoundException;
import org.codesofscar.bidbidbid.model.BidCollections;
import org.codesofscar.bidbidbid.model.Bids;
import org.codesofscar.bidbidbid.model.Users;
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
        return new ResponseEntity<>(bidsRepository.findById(bidId).
                orElseThrow(() -> new ResourceNotFoundException("Bid not found")), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addBidToCollection(BidsDTO bidDto) {
        Users user = userRepository.findById(bidDto.getUser().getId()).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));

        if (user == null) {
            throw new ResourceNotFoundException("Admin must be Logged In to Continue");
        }
        if (!user.getUserRole().equals(Roles.ADMIN)) {
            throw new ResourceNotFoundException("You are not allowed to Add Bid To Collection");
        }

        Bids bid = new ObjectMapper().convertValue(bidDto, Bids.class);
        Optional<Bids> bid1 = bidsRepository.findById(bid.getId());
        if(bid1.isPresent() ){
            return ResponseEntity.ok("Bid with ID " + bid.getId()+ " has already been added");
        }

        bidsToCollection.add(bid);

        BidCollections collection = collectionsRepository.findById(bid.getCollectionId()).
                orElseThrow(() -> new ResourceNotFoundException("Not Found"));

        collection.setBidsInCollection(bidsToCollection);


        bidsRepository.save(bid);
        collectionsRepository.save(collection);


        return ResponseEntity.ok("Bid with ID " + bid.getId() + " added successfully");
    }

    @Override
    public ResponseEntity<String> deleteBidById(Long bidId) {
        Optional<Bids> bid = bidsRepository.findById(bidId);
        if(!bid.isPresent()){
            return new ResponseEntity<>("No such item found", HttpStatus.BAD_REQUEST);
        }

        BidCollections collection = collectionsRepository.findById(bid.get().getCollectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found"));
        List<Bids> bidListInCollection = collection.getBidsInCollection();

        for (Bids collectionBid : bidListInCollection) {
            if (collectionBid.getId().equals(bid.get().getId())) {
                bidListInCollection.remove(collectionBid);
            }
        }


        bidsRepository.delete(bid.get());
        return ResponseEntity.ok( "Bid with ID " + bidId + " is deleted successfully");
    }

    @Override
    public ResponseEntity<String> acceptBid(Long bidId) {

        Users user = userRepository.findById(bidId).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));

        if (user == null) {
            throw new ResourceNotFoundException("Admin must be Logged In to Continue");
        }
        if (!user.getUserRole().equals(Roles.ADMIN)) {
            throw new ResourceNotFoundException("You are not allowed to Accept Bid");
        }

        Optional<Bids> bid = bidsRepository.findById(bidId);
        if(!bid.isPresent()){
            return new ResponseEntity<>("No such item found", HttpStatus.BAD_REQUEST);
        }

        if (!bid.get().getStatus().equals(BidStatus.ACCEPTED)){
            throw new ResourceNotFoundException("Bid already accepted");
        }

        bid.get().setStatus(BidStatus.ACCEPTED);

        BidCollections collection = collectionsRepository.findById(bid.get().getCollectionId()).
                orElseThrow(() -> new ResourceNotFoundException("Not Found"));

        List<Bids> bidsOfSameCollection = collection.getBidsInCollection();
        for (Bids singularBids : bidsOfSameCollection) {
            if (!singularBids.getId().equals(bid.get().getId())) {
                bid.get().setStatus(BidStatus.REJECTED);
            }
        }

        return new ResponseEntity<>("Bid with ID " +bid.get().getId()+" Accepted", HttpStatus.OK);


    }

    @Override
    public ResponseEntity<?> updateBidById(Long bidId, BidsDTO bidsDTO) {

        Users user = userRepository.findById(bidId).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        if (user == null) {
            throw new ResourceNotFoundException("Admin must be Logged In to Continue");
        }
        if (!user.getUserRole().equals(Roles.ADMIN)) {
            throw new ResourceNotFoundException("You are not allowed to Add Bid To Collection");
        }

        Optional<Bids> bid = bidsRepository.findById(bidId);
        if(!bid.isPresent()){
            return new ResponseEntity<>("Bid with ID " + bidId + " not found", HttpStatus.BAD_REQUEST);
        }
//        bid.get().setCollectionId(bidsDTO.getCollectionsId());
        bid.get().setPrice(bidsDTO.getPrice());
//        bid.get().setUserId(bidsDTO.getUserId());
//        bid.get().setStatus(bidsDTO.getStatus());

        bidsRepository.save(bid.get());

        return ResponseEntity.ok("Bid updated successfully");
    }





}
