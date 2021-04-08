package com.bank.example.util;



import com.bank.example.dto.OtherProfitDto;
import com.bank.example.model.operation.Transaction;
import com.bank.example.util.dto.transaction.AtmTransactionUtilDto;
import com.bank.example.util.dto.transaction.OtherProfitUtilDto;
import com.bank.example.util.dto.transaction.TransactionUtilDto;
import com.bank.example.util.dto.transaction.TransactionUtilType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TransactionHolder {

    private static final Map<Long, AtmTransactionHolder> atmTransactionMap = new HashMap<>();
    private static final Map<Long, OtherProfitHolder> otherProfitMap = new HashMap<>();
    private static final Map<Long, List<TransactionUtilDto>> transactionMap = new HashMap<>();


    /***
     * Получение/добавлние AtmTransaction
     * */
    public static void addAtmTransactionDto(long accountId, AtmTransactionUtilDto dto) {
        if (!atmTransactionMap.containsKey(accountId)) {
            atmTransactionMap.put(accountId, new AtmTransactionHolder());
        }

        atmTransactionMap.get(accountId).add(dto);
    }

    public static List<AtmTransactionUtilDto> getAtmTransactionDtoList(long accountId) {
        return atmTransactionMap.get(accountId).getAtmTransactionDtoList();
    }


    /**
     * Получение/добавление OtherProfitDto
     * */
    public static void addOtherProfitDto(long accountId, OtherProfitUtilDto dto) {
        if (!otherProfitMap.containsKey(accountId)) {
            otherProfitMap.put(accountId, new OtherProfitHolder());
        }

        otherProfitMap.get(accountId).add(dto);
    }

    public static List<OtherProfitUtilDto> getOtherProfitDtoList(long accountId) {
        return otherProfitMap.get(accountId).getOtherProfitDtoList();
    }


    /**
     * Получение/добавление TransactionDto
     * */
    public static void addTransactionDto(long accountId, TransactionUtilDto dto) {
        if (!transactionMap.containsKey(accountId)) {
            transactionMap.put(accountId, new ArrayList<>());
        }

        transactionMap.get(accountId).add(dto);
    }

    public static List<TransactionUtilDto> getTransactionDtoList(long accountId) {
        return transactionMap.get(accountId);
    }

    /**
     * Получение отдельных TransactionDto по типам
     * */
    public static List<TransactionUtilDto> getRefillDto(long accountId) {
        return atmTransactionMap.get(accountId).getRefillDtoList();
    }

    public static List<TransactionUtilDto> getWithdrawDto(long accountId) {
        return atmTransactionMap.get(accountId).getWithdrawDtoList();
    }

    public static List<TransactionUtilDto> getInterestsDto(long accountId) {
        return otherProfitMap.get(accountId).getInterestsDtoList();
    }

    public static List<TransactionUtilDto> getCashBackDto(long accountId) {
        return otherProfitMap.get(accountId).getCashBackDtoList();
    }


    private static class OtherProfitHolder {

        private List<OtherProfitUtilDto> interestList = new ArrayList<>();

        private List<OtherProfitUtilDto> cashBackList = new ArrayList<>();

        public void add(OtherProfitUtilDto dto) {
            switch (dto.getOtherProfitType()) {
                case INTERESTS:
                    interestList.add(dto);
                    break;
                case CASH_BACK:
                    cashBackList.add(dto);
                    break;
                default:
                    throw new RuntimeException("Подан не верный тип dto!");
            }
        }

        public List<TransactionUtilDto> getInterestsDtoList() {
            return castAndCopyFrom(interestList);
        }

        public List<TransactionUtilDto> getCashBackDtoList() {
            return castAndCopyFrom(cashBackList);
        }

        private List<TransactionUtilDto> castAndCopyFrom(List<OtherProfitUtilDto> dtoList) {
            List<TransactionUtilDto> resultLust = new ArrayList<>(dtoList.size());

            for (OtherProfitUtilDto dto : dtoList) {
                resultLust.add(
                        TransactionUtilDto.builder()
                                .accountId(dto.getAccountId())
                                .amount(dto.getAmount())
                                .dateTime(dto.getDateTime())
                                .transactionType(dto.getOtherProfitType())
                                .id(dto.getId())
                                .build()
                );
            }

            return resultLust;
        }

        public List<OtherProfitUtilDto> getOtherProfitDtoList() {
            List<OtherProfitUtilDto> resultList = new ArrayList<>(interestList.size() + cashBackList.size());
            resultList.addAll(interestList);
            resultList.addAll(cashBackList);
            return resultList;
        }
    }



    private static class AtmTransactionHolder {

        private List<AtmTransactionUtilDto> refillList = new ArrayList<>();

        private List<AtmTransactionUtilDto> withdrawList = new ArrayList<>();

        public void add(AtmTransactionUtilDto dto) {
            switch (dto.getAtmTransactionType()) {
                case REFILL:
                    refillList.add(dto);
                    break;
                case WITHDRAW:
                    withdrawList.add(dto);
                    break;
                default:
                    throw new RuntimeException("Подан не верный тип dto!");
            }
        }

        public List<TransactionUtilDto> getRefillDtoList() {
            return castAndCopyFrom(refillList);
        }

        public List<TransactionUtilDto> getWithdrawDtoList() {
            return castAndCopyFrom(withdrawList);
        }

        private List<TransactionUtilDto> castAndCopyFrom(List<AtmTransactionUtilDto> dtoList) {
            List<TransactionUtilDto> resultLust = new ArrayList<>(dtoList.size());

            for (AtmTransactionUtilDto dto : dtoList) {
                resultLust.add(
                        TransactionUtilDto.builder()
                                .accountId(dto.getAccountId())
                                .amount(dto.getAmount())
                                .dateTime(dto.getDateTime())
                                .transactionType(dto.getAtmTransactionType())
                                .id(dto.getId())
                                .build()
                );
            }

            return resultLust;
        }

        public List<AtmTransactionUtilDto> getAtmTransactionDtoList() {
            List<AtmTransactionUtilDto> resultList = new ArrayList<>(refillList.size() + withdrawList.size());
            resultList.addAll(refillList);
            resultList.addAll(withdrawList);
            return resultList;
        }
    }
}
