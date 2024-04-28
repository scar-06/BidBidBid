package org.codesofscar.bidbidbid.repository;

import org.codesofscar.bidbidbid.model.BidCollections;
import org.codesofscar.bidbidbid.model.Bids;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidCollectionsRepository extends JpaRepository<BidCollections, Long> {
    List<BidCollections> findAllById (Long Id);

    Optional<BidCollections> findById (Long Id);
}
