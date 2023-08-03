package com.phonecompany.billing;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TelephoneBillCalculatorImpl implements TelephoneBillCalculator{

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Override
    public BigDecimal calculate(String phoneLog) {

        Map<String, Integer> callFrequency = new HashMap<>();
        Map<String, BigDecimal> callCosts = new HashMap<>();

        String mostFrequentlyCalledNumber = null;
        int maxCallFrequency = 0;

        try(Scanner scanner = new Scanner(phoneLog)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineParts = line.split(",");

                String phoneNumber = lineParts[0];
                Date callStart = dateFormat.parse(lineParts[1]);
                Date callEnd = dateFormat.parse(lineParts[2]);

                int callDuration = calculateCallDuration(callStart, callEnd);
                BigDecimal callCost = calculateCallCost(callStart, callDuration);
                System.out.println("Phone number: "+ phoneNumber + " Call cost: " + callCost);

                callFrequency.put(phoneNumber, callFrequency.getOrDefault(phoneNumber, 0) + 1);
                callCosts.put(phoneNumber, callCosts.getOrDefault(phoneNumber, BigDecimal.ZERO).add(callCost));

                if (callFrequency.get(phoneNumber) > maxCallFrequency) {
                    maxCallFrequency = callFrequency.get(phoneNumber);
                    mostFrequentlyCalledNumber = phoneNumber;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Most frequently called number: "+ mostFrequentlyCalledNumber + " and max call frequency: " + maxCallFrequency);

        if (mostFrequentlyCalledNumber != null) {
            callCosts.put(mostFrequentlyCalledNumber, BigDecimal.ZERO);
        }

        BigDecimal totalCost = callCosts.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalCost;
        }


        private int calculateCallDuration(Date callStart, Date callEnd) {
            long durationInMilliseconds = callEnd.getTime() - callStart.getTime();
            int callDurationInSeconds = (int) (durationInMilliseconds / 1000);
            int callDurationInMinutes = (callDurationInSeconds / 60) + ((callDurationInSeconds % 60 > 0) ? 1 : 0); // každá započatá minuta se počítá za celou
            return callDurationInMinutes;
        }


    private BigDecimal calculateCallCost(Date callStart, int callDuration) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(callStart);

        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal peakHourRate = new BigDecimal("1.00"); // 8h - 16h (1.00 Kč za minutu)
        BigDecimal offPeakRate = new BigDecimal("0.50");  // 16h - 8h (0.50 Kč za minutu)
        BigDecimal reducedRate = new BigDecimal("0.20");  // 6. a další minuta hovoru (0.20 Kč za minutu)

        for (int i = 0; i < callDuration; i++) {
            int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
            BigDecimal currentRate = (hourOfDay >= 8 && hourOfDay < 16) ? peakHourRate : offPeakRate;

            if (i < 5) {
                totalCost = totalCost.add(currentRate);
            } else {
                totalCost = totalCost.add(reducedRate);
            }

            calendar.add(Calendar.MINUTE, 1);
        }
        return totalCost;
        }

        }
