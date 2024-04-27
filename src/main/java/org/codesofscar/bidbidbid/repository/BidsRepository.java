package org.codesofscar.bidbidbid.repository;

import org.codesofscar.bidbidbid.model.BidCollections;
import org.codesofscar.bidbidbid.model.Bids;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidsRepository extends JpaRepository<Bids, Long> {

//    Optional<Bids> findByCollection_Id (Long collectionsId);


//    List<Bids> findAllByCollection_Id (Long collectionsId);
}
