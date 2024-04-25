package org.codesofscar.bidbidbid.util;

import jakarta.annotation.PostConstruct;
import org.codesofscar.bidbidbid.enums.BidStatus;
import org.codesofscar.bidbidbid.model.Bids;
import org.codesofscar.bidbidbid.repository.BidCollectionsRepository;
import org.codesofscar.bidbidbid.repository.BidsRepository;
import org.codesofscar.bidbidbid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

@Component
public class BidsUtils {

    private final BidsRepository bidsRepository;


    @Autowired
    public BidsUtils(BidsRepository bidsRepository) {
        this.bidsRepository = bidsRepository;

    }

    @PostConstruct
    public void readBidsCSV(){

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/java/org/codesofscar/bidbidbid/csv/bids-base.csv"))) {
            String line;
            boolean lineOne = false;
            while ((line=bufferedReader.readLine())!=null){
                String[] bids = line.split(",");
                if (lineOne) {
                    Bids bidsInfo = Bids.builder()
                            .id(Long.valueOf(bids[0]))
                            .collectionId(Long.valueOf(bids[1]))
                            .price(new BigDecimal(bids[2]))
                            .userId(Long.valueOf(bids[3]))
                            .status(BidStatus.valueOf(bids[4]))
                            .build();
                    bidsRepository.save(bidsInfo);
                }
                lineOne = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
