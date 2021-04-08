package com.bank.example.util;


import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class NameUtil {

    private static final int NAME_SIZE = 11;

    private static final List<String> firstNamesList = new ArrayList<>(NAME_SIZE);

    private static final List<String> lastNamesList = new ArrayList<>(NAME_SIZE);

    private static Map<String, NameGenerator> generatorMap = new HashMap<>();

    static {
        initFirstNames();
        initLastNames();
    }

    public static NameUtilGetter forSpace(String id) {
        if (!generatorMap.containsKey(id)) {
            generatorMap.put(id, new NameGenerator());
        }

        return new NameUtilGetter(generatorMap.get(id));
    }

    public static class NameUtilGetter {

        private NameGenerator generator;

        public NameUtilGetter(NameGenerator generator) {
            this.generator = generator;
        }

        public Pair<String, String> getNamePair() {
            return generator.getNamePair();
        }
    }

    private static class NameGenerator {

        private static final Map<Integer, Set<Integer>> usedNamesMap = new HashMap<>();

        private static int currentNameIndex;

        public NameGenerator() {
            initUsedNameMap();
        }

        public Pair<String, String> getNamePair() {

            int lastNameIndex = getLastNameIndex();

            if (lastNameIndex >= NAME_SIZE) {
                throw new RuntimeException("Число фамилий не может быть больше " + NAME_SIZE);
            }

            String firstName = firstNamesList.get(currentNameIndex);
            String lastName = lastNamesList.get(lastNameIndex);

            incrementOrResetCurrentName();

            return Pair.of(firstName, lastName);
        }

        private static void incrementOrResetCurrentName() {
            if (currentNameIndex < NAME_SIZE-1) {
                currentNameIndex++;
            } else {
                currentNameIndex = 0;
            }
        }

        private static int getLastNameIndex() {
            Set<Integer> lastNameSet = usedNamesMap.get(currentNameIndex);

            if (lastNameSet.size() == 0) {
                lastNameSet.add(currentNameIndex);
                return currentNameIndex;
            }

            int maxIndex = lastNameSet.stream().max(Integer::compareTo).get();

            if (maxIndex < NAME_SIZE - 1) {
                int nextIndex = maxIndex + 1;
                lastNameSet.add(nextIndex);
                return nextIndex;
            }

            int minIndex = lastNameSet.stream().min(Integer::compareTo).get();

            if (minIndex > -1) {
                int prevIndex = minIndex - 1;
                lastNameSet.add(prevIndex);
                return prevIndex;
            }

            throw new RuntimeException("Полученный индекс не вписывается в диапазон");
        }

        private static void initUsedNameMap() {
            for (int i = 0; i < NAME_SIZE; i++) {
                usedNamesMap.put(i, new HashSet<>());
            }
        }
    }

    private static void initFirstNames() {
        firstNamesList.add("Андрей");
        firstNamesList.add("Иван");
        firstNamesList.add("Антон");
        firstNamesList.add("Петр");
        firstNamesList.add("Алексей");
        firstNamesList.add("Денис");
        firstNamesList.add("Илья");
        firstNamesList.add("Владимир");
        firstNamesList.add("Дмитрий");
        firstNamesList.add("Александр");
        firstNamesList.add("Евгений");
    }

    private static void initLastNames() {
        lastNamesList.add("Александров");
        lastNamesList.add("Дмитриев");
        lastNamesList.add("Владимиров");
        lastNamesList.add("Ильин");
        lastNamesList.add("Денисов");
        lastNamesList.add("Алексеев");
        lastNamesList.add("Петров");
        lastNamesList.add("Антонов");
        lastNamesList.add("Иванов");
        lastNamesList.add("Андреев");
        lastNamesList.add("Максимов");
    }
}
