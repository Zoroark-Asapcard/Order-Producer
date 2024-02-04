package com.zoroark.orderproducer.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.*;
import java.nio.file.*;
import java.util.StringTokenizer;

@Component
public class CsvReaderUtil {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void processExistingFiles(String directoryPath) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directoryPath), "*.csv")) {
            for (Path entry : stream) {
                readCSVFile(entry.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void watchDirectory(String directoryPath) {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(directoryPath);

            path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY
            );

            while (true) {
                WatchKey key = watchService.take();

                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE ||
                            event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                        Path filePath = (Path) event.context();
                        readCSVFile(path.resolve(filePath).toString());
                    }
                }

                boolean reset = key.reset();
                if (!reset) {
                    break;
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void readCSVFile(String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            JSONArray jsonArray = new JSONArray();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Split the line into fields using the ';' delimiter
                StringTokenizer tokenizer = new StringTokenizer(line, ";");

                // Extract the fields from the line
                String transactionId = tokenizer.nextToken();
                String transactionDate = tokenizer.nextToken();
                String document = tokenizer.nextToken();
                String name = tokenizer.nextToken();
                int age = Integer.parseInt(tokenizer.nextToken());
                double transactionAmount = Double.parseDouble(tokenizer.nextToken());
                int numberOfInstallments = Integer.parseInt(tokenizer.nextToken());

                // Create a JSONObject for each line
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("transactionId", transactionId);
                jsonObject.put("transactionDate", transactionDate);
                jsonObject.put("document", document);
                jsonObject.put("name", name);
                jsonObject.put("age", age);
                jsonObject.put("transactionAmount", transactionAmount);
                jsonObject.put("numberOfInstallments", numberOfInstallments);

                // Add the JSONObject to the JSONArray
                jsonArray.put(jsonObject);

                // Send the JSON message to RabbitMQ queue using RabbitMQProducer
                rabbitTemplate.convertAndSend("orders", jsonObject.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
