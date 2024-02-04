/*package com.zoroark.orderproducer.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    //private TransactionRepository transactionRepository;

    public void saveTransactionsFromJson(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonTransaction = jsonArray.getJSONObject(i);

            
            String transactionId = jsonTransaction.getString("transactionId");
            String transactionDate = jsonTransaction.getString("transactionDate");
            String document = jsonTransaction.getString("document");
            String name = jsonTransaction.getString("name");
            int age = jsonTransaction.getInt("age");
            double transactionAmount = jsonTransaction.getDouble("transactionAmount");
            int numberOfInstallments = jsonTransaction.getInt("numberOfInstallments");
                            
                    
            //transaction instance
            
            // save transaction transactionRepository.save(transaction);

        }
    }
}*/
