package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        File read = new File("testData_Apartments.txt");

        Scanner input = null;

        Map<String, Integer> apartmentsPerCity = new HashMap<>();
        Set<String> validCities = new HashSet<>();
        validCities.add("София");
        validCities.add("Варна");
        validCities.add("Бургас");
        int numberOfValidApartments = 0;
        Map<Apartment, Integer> cheapestApartments = new HashMap<>();

        try {
            input = new Scanner(read);

            while (input.hasNext()) {
                String city = input.next();
                int numberOfRooms = input.nextInt();
                int area = input.nextInt();
                int price = input.nextInt();
                String number = input.next();

                if (area > 100 && numberOfRooms == 3 && validCities.contains(city)) {
                    numberOfValidApartments++;
                } else {
                    continue;
                }

                Apartment a = new Apartment(city, numberOfRooms, area, price, number);

                if (apartmentsPerCity.containsKey(city)) {
                    apartmentsPerCity.put(city, apartmentsPerCity.get(city) + 1);
                } else {
                    apartmentsPerCity.put(city, 1);
                }

                cheapestApartments.put(a, price);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            assert input != null;
            input.close();
        }

        try {
            if (numberOfValidApartments == 0) {
                throw new UnsuitableApartmentsException("None of the listed apartments are valid.");
            }
        } catch (UnsuitableApartmentsException e) {
            System.out.println(e.getMessage());
        }

        List<Map.Entry<String, Integer>> sortedApartmentsPerCity = new ArrayList<>(apartmentsPerCity.entrySet());
        sortedApartmentsPerCity.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        List<Map.Entry<Apartment, Integer>> sortedCheapestApartments = new ArrayList<>(cheapestApartments.entrySet());
        sortedCheapestApartments.sort(Map.Entry.comparingByValue());

        File write = new File("output_Apartments.txt");
        if (write.exists()) {
            System.out.println("File already exists.");
            System.exit(1);
        }
        PrintWriter output = null;

        // set с първите пет най - евтини оферти за апартамент (използвам го, за да отстраня дубликациите)
        Set<String> phoneNumbers = new HashSet<>();
        int i = 0;
        for(Map.Entry<Apartment, Integer> entry : sortedCheapestApartments) {
            if (i == 5) break;
            i++;
            phoneNumbers.add(entry.getKey().getPhoneNumber());
        }

        try {
            output = new PrintWriter(write);

            for(String phoneNumber : phoneNumbers) {
                output.println(phoneNumber);
            }
            output.println();

            i = 0;
            for (Map.Entry<String, Integer> entry : sortedApartmentsPerCity) {
                if (i == 5) break;
                i++;
                output.print(entry.getKey() + " " + entry.getValue());
                output.println();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            assert output != null;
            output.close();
        }
    }
}

/*
    1. Apartment class
    2. Exception class
    3. Read from file
        - read
        - map listings by city
        - map apartment and price
        - counter for valid apartments
    4. Sort listings by city
    5. Sort apartments by price
    6. Print to file
        - print first 5 cheapest apartments that fulfill the requirements (their phone numbers - no duplicates)
        - print the first 5 cities with the most listings

    Requirements:
        area > 100
        3 rooms
        from cities София, Варна, Бургас
*/