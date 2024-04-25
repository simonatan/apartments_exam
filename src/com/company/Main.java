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

                if (apartmentsPerCity.containsKey(city)) {
                    apartmentsPerCity.put(city, apartmentsPerCity.get(city) + 1);
                } else {
                    apartmentsPerCity.put(city, 1);
                }

                if (area > 100 && numberOfRooms == 3 && validCities.contains(city)) {
                    numberOfValidApartments++;
                    cheapestApartments.put(new Apartment(city, numberOfRooms, area, price, number), price);
                }
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
            output.println(sortedApartmentsPerCity.get(0).getKey()/* + " " + sortedApartmentsPerCity.get(0).getValue()*/);
            int prev = sortedApartmentsPerCity.get(0).getValue();
            while (sortedApartmentsPerCity.get(++i).getValue() == prev) {
                output.println(sortedApartmentsPerCity.get(i).getKey()/* + " " + sortedApartmentsPerCity.get(i).getValue()*/);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            assert output != null;
            output.close();
        }
    }
}