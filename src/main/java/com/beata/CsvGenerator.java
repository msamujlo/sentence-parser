package com.beata;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;

public class CsvGenerator {

    private Path tempFilePath = Paths.get("temp.csv");
    private Path finalFilePath = Paths.get("out.csv");

    public void deleteIfCsvFilesAlreadyExist() {

        try {
            Files.deleteIfExists(tempFilePath);
            Files.deleteIfExists(finalFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToCsv(String line) throws IOException {

        Files.write(tempFilePath,line.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    private String generateCsvHeader(int sentenceLengthCounter) {

        StringBuilder stringBuilder = new StringBuilder();

        for(int i= 0; i < sentenceLengthCounter; i++ ) {
            int j = ++i;
            stringBuilder.append(", Word " + j);
        }

        stringBuilder.append(System.lineSeparator());

        return stringBuilder.toString();
    }

    public void createFinalCsvFile(int sentenceLengthCounter) throws IOException {

        Files.write(finalFilePath, generateCsvHeader(sentenceLengthCounter).getBytes(), StandardOpenOption.CREATE);

        String line;
        BufferedReader bufferedReader = Files.newBufferedReader(tempFilePath);

        while((line = bufferedReader.readLine()) != null) {
            if(line.length() > 0) {
                String lineWithEOL = line + System.lineSeparator();
                Files.write(finalFilePath, lineWithEOL.getBytes(), StandardOpenOption.APPEND);
            }
        }

        Files.delete(tempFilePath);
    }
}
