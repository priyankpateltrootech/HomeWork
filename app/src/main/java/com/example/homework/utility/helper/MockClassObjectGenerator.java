package com.example.homework.utility.helper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockClassObjectGenerator {

    public final static String[] dummy_strings = {"Green House", "Blue House", "Maruti Nivas", "Umiya Nivas", "Mera Makan", "Tera Makan", "Duplex House", "Furniture House", "Dukan House", "Safal Profitier", "Shilp Shaligram", "Adani Shantigram", "Godrej Garden City"};

    public final static String[] dummy_firstNames = {"Abigail", "Adam", "Alexandra", "Adrian", "Alison", "Alan", "Amanda", "Alexander", "Amelia", "Andrew", "Amy", "Anthony", "Andrea", "Austin", "Angela", "Benjamin", "Anna", "Blake", "Anne", "Boris", "Audrey", "Brandon", "Ava", "Brian", "Bella", "Cameron", "Bernadette", "Carl", "Carol", "Charles", "Caroline", "Christian", "Carolyn", "Christopher", "Chloe", "Colin", "Claire", "Connor", "Deirdre", "Dan", "Diana", "David", "Diane", "Dominic", "Donna", "Dylan", "Dorothy", "Edward", "Elizabeth", "Eric", "Ella", "Evan", "Emily", "Frank", "Emma", "Gavin", "Faith", "Gordon", "Felicity", "Harry", "Fiona", "Ian", "Gabrielle", "Isaac", "Grace", "Jack", "Hannah", "Jacob", "Heather", "Jake", "Irene", "James", "Jan", "Jason", "Jane", "Joe", "Jasmine", "John", "Jennifer", "Jonathan", "Jessica", "Joseph", "Joan", "Joshua", "Joanne", "Julian", "Julia", "Justin", "Karen", "Keith", "Katherine", "Kevin", "Kimberly", "Leonard", "Kylie", "Liam", "Lauren", "Lucas", "Leah", "Luke", "Lillian", "Matt", "Lily", "Max", "Lisa", "Michael", "Madeleine", "Nathan", "Maria", "Neil", "Mary", "Nicholas", "Megan", "Oliver", "Melanie", "Owen", "Michelle", "Paul", "Molly", "Peter", "Natalie", "Phil", "Nicola", "Piers", "Olivia", "Richard", "Penelope", "Robert", "Pippa", "Ryan", "Rachel", "Sam", "Rebecca", "Sean", "Rose", "Sebastian", "Ruth", "Simon", "Sally", "Stephen", "Samantha", "Steven", "Sarah", "Stewart", "Sonia", "Thomas", "Sophie", "Tim", "Stephanie", "Trevor", "Sue", "Victor", "Theresa", "Warren", "Tracey", "William", "Una", "Vanessa", "Victoria", "Virginia", "Wanda", "Wendy", "Yvonne", "Zoe"};

    public final static String[] dummy_lastNames = {"Abraham", "Allan", "Alsop", "Anderson", "Arnold", "Avery", "Bailey", "Baker", "Ball", "Bell", "Berry", "Black", "Blake", "Bond", "Bower", "Brown", "Buckland", "Burgess", "Butler", "Cameron", "Campbell", "Carr", "Chapman", "Churchill", "Clark", "Clarkson", "Coleman", "Cornish", "Davidson", "Davies", "Dickens", "Dowd", "Duncan", "Dyer", "Edmunds", "Ellison", "Ferguson", "Fisher", "Forsyth", "Fraser", "Gibson", "Gill", "Glover", "Graham", "Grant", "Gray", "Greene", "Hamilton", "Hardacre", "Harris", "Hart", "Hemmings", "Henderson", "Hill", "Hodges", "Howard", "Hudson", "Hughes", "Hunter", "Ince", "Jackson", "James", "Johnston", "Jones", "Kelly", "Kerr", "King", "Knox", "Lambert", "Langdon", "Lawrence", "Lee", "Lewis", "Lyman", "MacDonald", "Mackay", "Mackenzie", "MacLeod", "Manning", "Marshall", "Martin", "Mathis", "May", "McDonald", "McLean", "McGrath", "Metcalfe", "Miller", "Mills", "Mitchell", "Morgan", "Morrison", "Murray", "Nash", "Newman", "Nolan", "North", "Ogden", "Oliver", "Paige", "Parr", "Parsons", "Paterson", "Payne", "Peake", "Peters", "Piper", "Poole", "Powell", "Pullman", "Quinn", "Rampling", "Randall", "Rees", "Reid", "Roberts", "Robertson", "Ross", "Russell", "Rutherford", "Sanderson", "Scott", "Sharp", "Short", "Simpson", "Skinner", "Slater", "Smith", "Springer", "Stewart", "Sutherland", "Taylor", "Terry", "Thomson", "Tucker", "Turner", "Underwood", "Vance", "Vaughan", "Walker", "Wallace", "Walsh", "Watson", "Welch", "White", "Wilkins", "Wilson", "Wright", "Young"};

    public final static String[] dummy_emailDomain = {"gmail.com", "yahoo.com", "ymail.com", "newmail.com"};

    public final static String[] dummy_countryCode = {"+91", "+94", "+1", "+61", "+72", "+12", "+80", "+8", "+55", "+99"};

    public final static String[] dummy_mobileCode = {"94", "98", "92", "04", "65", "70", "76", "84", "99", "81", "93", "95"};

    final static String[] dummy_streetName = {"Lewis Street", "Jeans Street", "Mata ni Sheri", "Pita ni Sheri", "Tari Sheri", "Mari Sheri", "Prahlad Nagar Road", "Shanti Bungalow Road", "Long path road", "Aone Mall Road"};

    final static String[] dummy_stateCode = {"GJ", "MH", "IL", "KB", "CA", "NY", "AD", "BE", "KA", "QA", "BD"};

    public static String[] dummy_propertyImageUrls = {
            "https://images.unsplash.com/photo-1448630360428-65456885c650?fit=crop&w=500",
            "https://images.unsplash.com/photo-1544984243-ec57ea16fe25??fit=crop&w=500",
            "https://images.unsplash.com/photo-1522050212171-61b01dd24579?fit=crop&w=500",
            "https://images.unsplash.com/photo-1484154218962-a197022b5858?fit=crop&w=500",
            "https://images.unsplash.com/photo-1524292691042-82ed9c62673b?fit=crop&w=500",
            "https://images.unsplash.com/photo-1449844908441-8829872d2607?fit=crop&w=500",
            "https://images.unsplash.com/photo-1489476518696-1d12316c4c81?fit=crop&w=500",
            "https://images.unsplash.com/photo-1553524803-2eec745b729e?fit=crop&w=500",
            "https://images.unsplash.com/photo-1501595685668-178fc57e6146?fit=crop&w=500",
            "https://images.unsplash.com/photo-1512915922686-57c11dde9b6b?fit=crop&w=500",
            "https://images.unsplash.com/photo-1523217582562-09d0def993a6?fit=crop&w=500",
            "https://images.unsplash.com/photo-1475855581690-80accde3ae2b?fit=crop&w=500",
            "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?fit=crop&w=500",
            "https://images.unsplash.com/photo-1492889971304-ac16ab4a4a5a?fit=crop&w=500",
            "https://images.unsplash.com/photo-1493895565436-93db25637518?fit=crop&w=500"};

    private Class<?> aClass;

    private int maxObjects;

    public MockClassObjectGenerator(Class<?> aClass, int maxObjects) {

        this.aClass = aClass;
        this.maxObjects = maxObjects;
    }

    public List generate(Random random) {

        List list = new ArrayList();

        for (int i = 0; i < maxObjects; i++) {

            try {

                Constructor constructor = aClass.getConstructor(Random.class);

                list.add(constructor.newInstance(random));

            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public static String getDummyAddress(Random random) {

        StringBuilder builder = new StringBuilder();

        builder.append(random.nextInt(150) + 1)
                .append(", ")
                .append(getRandomFrom(random, dummy_streetName))
                .append(", ")
                .append(getRandomFrom(random, dummy_streetName))
                .append(", ")
                .append(getRandomFrom(random, dummy_stateCode))
                .append(", ")
                .append(random.nextInt(99999 - 9999) + 9999);

        return builder.toString();
    }

    public static String getRandomFullName(Random random) {

        return getRandomFrom(random, dummy_firstNames) + " " + getRandomFrom(random, dummy_lastNames);
    }

    public static int getRandomInt(Random random, int from, int to) {

        if (from > to) throw new IllegalArgumentException("'From' can't be greater than 'to'.");

        return (random.nextInt(to) + from) - from;
    }

    public static String generateEmail(Random random, String fullName) {

        return (fullName.replaceAll(" ", "") + "@" + getRandomFrom(random, dummy_emailDomain)).toLowerCase();
    }

    public static String generateMobileNumber(Random random) {

        return getRandomFrom(random, dummy_countryCode) + " " + getRandomFrom(random, dummy_mobileCode) +

                getRandomInt(random, 10000000, 99999999);
    }

    public static String getRandomFrom(Random random, String[] array) {

        return array[random.nextInt(array.length)];
    }
}
