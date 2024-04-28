package org.codesofscar.bidbidbid.util;

import jakarta.annotation.PostConstruct;
import org.codesofscar.bidbidbid.enums.BidStatus;
import org.codesofscar.bidbidbid.model.BidCollections;
import org.codesofscar.bidbidbid.model.Bids;
import org.codesofscar.bidbidbid.repository.BidCollectionsRepository;
import org.codesofscar.bidbidbid.repository.BidsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Component
public class CollectionsUtils {

    private final BidCollectionsRepository collectionsRepository;

    private final BidsRepository bidsRepository;


    @Autowired
    public CollectionsUtils(BidCollectionsRepository collectionsRepository, BidsRepository bidsRepository) {
        this.collectionsRepository = collectionsRepository;
        this.bidsRepository = bidsRepository;
    }

    @PostConstruct
    public void readBidsCSV(){

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/java/org/codesofscar/bidbidbid/csv/collections-base.csv"))) {

            String line;
            boolean lineOne = false;
            while ((line=bufferedReader.readLine())!=null){
                String[] collections = line.split(",");
                if (lineOne) {
                    BidCollections collectionInfo = BidCollections.builder()
                            .id(Long.valueOf(collections[0]))
                            .collectionName(collections[1])
                            .descriptions(collections[2])
                            .stocks(Integer.valueOf(collections[3]))
                            .price(new BigDecimal(Long.parseLong(collections[4])))
                            .bidsInCollection(
                                    bidsRepository.findAllByCollectionId(Long.valueOf(collections[0]))
                            )
                            .build();
                    collectionsRepository.save(collectionInfo);
                }
                lineOne = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
