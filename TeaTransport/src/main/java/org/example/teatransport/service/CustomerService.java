package org.example.teatransport.service;

import lombok.RequiredArgsConstructor;
import org.example.teatransport.repository.CustomerRipocitory;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRipocitory customerRipocitory;

    public List<Integer> findByDateAndCustId(String date, String custID) {
//
//        String startDate = formattedDate + "-01";
//        String endDate = now.withDayOfMonth(now.toLocalDate().lengthOfMonth())
//                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

//        List<Integer> results = customerRipocitory.findDailyTotalsByMonth(date, custID);
        List<Integer> ss = new ArrayList<>();
        return ss;
    }
}
