package org.example.teatransport.service;

import lombok.RequiredArgsConstructor;
import org.example.teatransport.entity.Customer;
import org.example.teatransport.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final MonthlyRateRipocitory monthlyRateRipocitory;
    private final CustomerRipocitory customerRipocitory;
    private final AdvanceRipocitory advanceRipocitory;
    private final UserRepository userRepository;
    private final ProductpurchaseRipocitory productpurchaseRipocitory;
    private final PohorapurchasecustomerRepocitory pohorapurchasecustomerRepocitory;
    private final CustomerhigapriceRepocitory customerhigapriceRepocitory;

    public List<Integer> findByDateAndCustId(String date, String custID) {
//
//        String startDate = formattedDate + "-01";
//        String endDate = now.withDayOfMonth(now.toLocalDate().lengthOfMonth())
//                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

//        List<Integer> results = customerRipocitory.findDailyTotalsByMonth(date, custID);
        List<Integer> ss = new ArrayList<>();
        return ss;
    }

    public int[] getTeaLeaf(String name, String date){
        YearMonth ym = convertToYearMonth(date);
        int daysInMonth = ym.lengthOfMonth();

        String id = String.valueOf(customerRipocitory.findByIds(name));


        int[] array = new int[daysInMonth + 1];

        for (int i = 1; i <= daysInMonth; i++) {
            LocalDate ss = ym.atDay(i);
            int temp = monthlyRateRipocitory.getCountTeaLeaf(id, ss);
            array[i] = temp;
        }


        return array;
    }

    private String getStartDate1(String date) {
        YearMonth ym = convertToYearMonth(date);
        return ym.toString(); // start of month
    }


    public Double[] getDetails(String name, String date) {

        String year = date.replaceAll("[^0-9]", "");       // Extract digits → 2025
        String month = date.replaceAll("[0-9]", "");      // Extract letters → SEPTEMBER

        String newdate = year + month;

        String custId = "";
        String MID = "M001";
        String TID = "T001";


        String StrartDate = getStartDate(date);
        String endDate = getEndDate(date);

        try {
            custId = customerRipocitory.findByName(name)
                    .map(Customer::getId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        } catch (Exception e) {
            System.out.println("Error while fetching user: " + e.getMessage());
        }


        Double[] array = new Double[7];

        try {
            array[0] = advanceRipocitory.getAdvance(custId,date);
            array[1] = productpurchaseRipocitory.getcountProductM(custId,StrartDate,endDate,MID);
            array[2] = productpurchaseRipocitory.getcountProductT(custId,StrartDate,endDate,TID);
            array[3] = pohorapurchasecustomerRepocitory. getcountPohora(custId,date);
            array[4] = customerhigapriceRepocitory.gethiga(custId,date);
            array[5] = monthlyRateRipocitory.getgoldRate(newdate);
            array[6] = monthlyRateRipocitory.getgoodRate(newdate);


        }catch (Exception e) {
            System.out.println(e.getMessage());

        }

        return array;
    }




        public static String getStartDate(String input) {
            YearMonth ym = convertToYearMonth(input);
            return ym.atDay(1).toString(); // start of month
        }

        public static String getEndDate(String input) {
            YearMonth ym = convertToYearMonth(input);
            return ym.atEndOfMonth().toString(); // end of month
        }

        private static YearMonth convertToYearMonth(String input) {
            String year = input.substring(input.length() - 4); // "2025"
            String monthName = input.substring(0, input.length() - 4); // "September"

            Map<String, Integer> monthMap = new HashMap<>();
            monthMap.put("January", 1);
            monthMap.put("February", 2);
            monthMap.put("March", 3);
            monthMap.put("April", 4);
            monthMap.put("May", 5);
            monthMap.put("June", 6);
            monthMap.put("July", 7);
            monthMap.put("August", 8);
            monthMap.put("September", 9);
            monthMap.put("October", 10);
            monthMap.put("November", 11);
            monthMap.put("December", 12);

            Integer monthNumber = monthMap.get(monthName);
            if (monthNumber == null) {
                throw new IllegalArgumentException("Invalid month name: " + monthName);
            }

            return YearMonth.of(Integer.parseInt(year), monthNumber);
        }


    public int[] getTeaLeafCOunt(String name, String date) {
        String id = String.valueOf(customerRipocitory.findByIds(name));
        LocalDate StrartDate = LocalDate.parse(getStartDate(date));
        LocalDate endDate = LocalDate.parse(getEndDate(date));

        int[] array = new int[2];
        try {
            array[0] = monthlyRateRipocitory.getTealEafCount(id,StrartDate,endDate);
            array[1] = monthlyRateRipocitory.getTealEafCount1(id,StrartDate,endDate);
            return array;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
