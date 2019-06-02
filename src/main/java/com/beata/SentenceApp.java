package com.beata;

import java.io.BufferedReader;
import java.io.FileReader;


public class SentenceApp {

    // application should read from standard input and write to standard output
    public static void main(String[] args) throws Exception {

        String line;

        // hard coded configuration
        String filePath = "c:\\sample_data\\small.in";
        SentenceService sentenceService = new SentenceService();

        // in case SentenceService is used by other developers, the can
        // forget to call prepareFiles(), then processSentence will fail
        sentenceService.prepareFiles();

        BufferedReader fileReader = new BufferedReader(new FileReader(filePath));

        while ((line = fileReader.readLine()) != null) {
            if (line.length() > 0) {
                sentenceService.processSentence(line);
            }
        }

        // should be built into XmlGenerator and CSVGenerator
        sentenceService.closeFiles();
    }
}