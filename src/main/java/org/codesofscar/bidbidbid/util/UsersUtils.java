package org.codesofscar.bidbidbid.util;

import jakarta.annotation.PostConstruct;
import org.codesofscar.bidbidbid.enums.Roles;
import org.codesofscar.bidbidbid.model.Bids;
import org.codesofscar.bidbidbid.model.Users;
import org.codesofscar.bidbidbid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class UsersUtils {
    private UserRepository userRepository;

    @Autowired
    public UsersUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void readUserCSV(){

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/java/org/codesofscar/bidbidbid/csv/users-base.csv"))) {
            String line;
            boolean lineOne = false;
            while ((line=bufferedReader.readLine())!=null){
                String[] users = line.split(",");
                if (lineOne) {
                    Users userInfo = Users.builder()
                            .id(Long.valueOf((users[0])))
                            .username(users[1])
                            .email(users[2])
                            .password(users[3])
                            .bidId(users[4])
                            .userRole(Roles.valueOf(users[5]))
                                    .build();
                    userRepository.save(userInfo);
                }
                lineOne = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
