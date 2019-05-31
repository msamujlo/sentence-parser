package com.beata;

import java.io.BufferedReader;
import java.io.FileReader;


public class SentenceApp {

    public static void main(String[] args) throws Exception {

        String line;
        String filePath = "c:\\sample_data\\small.in";
        SentenceService sentenceService = new SentenceService();

        sentenceService.prepareFiles();

        BufferedReader fileReader = new BufferedReader(new FileReader(filePath));

        while((line = fileReader.readLine()) != null) {
            if(line.length() > 0) {
                sentenceService.processSentence(line);
            }
        }

        sentenceService.closeFiles();
    }
}