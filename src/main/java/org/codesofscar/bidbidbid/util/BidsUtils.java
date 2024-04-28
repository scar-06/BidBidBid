package org.codesofscar.bidbidbid.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.codesofscar.bidbidbid.enums.BidStatus;
import org.codesofscar.bidbidbid.model.BidCollections;
import org.codesofscar.bidbidbid.model.Bids;
import org.codesofscar.bidbidbid.model.Users;
import org.codesofscar.bidbidbid.repository.BidCollectionsRepository;
import org.codesofscar.bidbidbid.repository.BidsRepository;
import org.codesofscar.bidbidbid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@Component
@Slf4j
public class BidsUtils {

    private final BidsRepository bidsRepository;
    private final UserRepository userRepository;
    private final BidCollectionsRepository collectionsRepository;



    @Autowired
    public BidsUtils(BidsRepository bidsRepository, UserRepository userRepository, BidCollectionsRepository collectionsRepository) {
        this.bidsRepository = bidsRepository;
        this.userRepository = userRepository;
        this.collectionsRepository = collectionsRepository;
    }

    @PostConstruct
    public void readBidsCSV(){

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/java/org/codesofscar/bidbidbid/csv/bids-base.csv"))) {
            String line;
            boolean lineOne = false;


            while ((line=bufferedReader.readLine())!=null){
                String[] bids = line.split(",");
                if (lineOne) {
//                    Optional<BidCollections> collectionsOptional = collectionsRepository.findById(Long.valueOf(bids[0]));
//                    Optional<Users> usersOptional = userRepository.findById(Long.valueOf(bids[3]));
//                    System.out.println(collectionsOptional.get());


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
