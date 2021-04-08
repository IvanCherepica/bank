package com.bank.example.util;

import org.springframework.data.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NumberUtil {

    private static final Map<String, NumberGenerator> generatorMap = new HashMap<>();

    public static NumberUtilGetter forSpace(String id) {
        if (!generatorMap.containsKey(id)) {
            generatorMap.put(id, new NumberGenerator());
        }

        return new NumberUtilGetter(generatorMap.get(id));
    }

    public static class NumberUtilGetter {

        private NumberGenerator generator;

        public NumberUtilGetter(NumberGenerator generator) {
            this.generator = generator;
        }

        public String getNextNumberForRank(int rank) {
            String value = String.valueOf(generator.getNextNumberForRank(rank));
            int additionalZeros = 0;
            StringBuilder builder = new StringBuilder();

            if (value.length() < rank) {
                additionalZeros = rank - value.length();
            }

            for (int i = 0; i < additionalZeros; i++) {
                builder.append("0");
            }

            builder.append(value);

            return builder.toString();
        }
    }

    private static class NumberGenerator {
        private static final Map<Integer, Integer> rankNumberMap = new HashMap<>();

        int getNextNumberForRank(int rank) {
            if (!rankNumberMap.containsKey(rank)) {
                rankNumberMap.put(rank, 0);
            }

            rankNumberMap.merge(rank, 1, Integer::sum);
            return rankNumberMap.get(rank);
        }

    }
}
