package org.codesofscar.bidbidbid.repository;

import org.codesofscar.bidbidbid.model.Bids;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidsRepository extends JpaRepository<Bids, Long> {

    Optional<Bids> findByCollectionId(Long collectionsId);


    List<Bids> findAllByCollectionId(Long collectionsId);

    Optional<Bids> findById (Long bidId);
}
