package com.beata;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.*;

public class SentenceService {

    private Integer sentenceCounter;
    private Integer sentenceLengthCounter;
    private Sentence sentence;
    private XmlGenerator xmlGenerator;
    private CsvGenerator csvGenerator;

    public SentenceService() throws Exception {

        sentenceCounter = 0;
        sentenceLengthCounter = 0;
        sentence = new Sentence();
        xmlGenerator = new XmlGenerator();
        csvGenerator = new CsvGenerator();
    }

    public void prepareFiles() throws IOException {

        xmlGenerator.createDocumentHeader();
        csvGenerator.deleteIfCsvFilesAlreadyExist();
    }

    public void processSentence(String line) throws JAXBException {

        List<String> wordsList = Arrays.asList(splitLineIntoWords(cleanLine(line)));

        for(String word : wordsList) {
            buildSentenceOutput(word);
        }
    }


    private String[] splitLineIntoWords(String line ){

        return line.split(" ");

    }

    private String cleanLine(String line) {

        final String REGEX_WHITESPACE_BEGINNING_END = "(^\\s+)(.+)(\\s$)";
        final String REGEX_REDUNDANT_WHITESPACES = "\\s+";
        final String REGEX_WHITESPACES_BEFORE_PUNCTUATION = "(\\w)(\\s+)([.!?])";
        final String REGEX_OTHER_CHARACTERS_TO_REMOVE = "[()\\-:,]";

        return line.replaceAll(REGEX_WHITESPACE_BEGINNING_END, "$2")
                .replaceAll(REGEX_OTHER_CHARACTERS_TO_REMOVE, " " )
                .replaceAll(REGEX_WHITESPACES_BEFORE_PUNCTUATION, "$1$3")
                .replaceAll(REGEX_REDUNDANT_WHITESPACES, " ");

    }

    private void buildSentenceOutput(String word) throws JAXBException{

        final String REGEX_END_OF_SENTENCE = "(.+)([.!?])";

        if(!word.matches(REGEX_END_OF_SENTENCE)){
            sentence.getWords().add(word);
        } else if(isWordAbbreviation(word)) {
            sentence.getWords().add(word);
        } else {
            sentence.getWords().add(word.replaceAll(REGEX_END_OF_SENTENCE, "$1"));
            finalizeSentence();
        }
    }


    private void finalizeSentence() throws JAXBException{

        Collections.sort(sentence.getWords(), String.CASE_INSENSITIVE_ORDER);
        setCounters();
        saveSentenceToXml(sentence);
        saveSentenceToCsv(sentence);
        sentence.getWords().clear();
    }

    private void setCounters(){
        sentenceCounter++;
        setSentenceLengthCounter(sentence.getWords().size());
    }

    private boolean isWordAbbreviation(String word){

        final String REGEX_ABBREVIATIONS = "[A-Z]{1}+[a-z]{1,2}+\\.+";

        if(word.matches(REGEX_ABBREVIATIONS)) {
            return true;
        } else {
            return false;
        }

    }

    private void setSentenceLengthCounter(Integer sentenceLength) {

        if(sentenceLength > sentenceLengthCounter) {
            sentenceLengthCounter = sentenceLength;
        }
    }

    private void saveSentenceToXml(Sentence sentence) throws JAXBException{

        xmlGenerator.marshalSentence(sentence);

    }

    private void saveSentenceToCsv(Sentence sentece) {

        final String SENTENCE_IDENTIFIER = "Sentence";
        String line = (SENTENCE_IDENTIFIER + " " + sentenceCounter + sentence.toString() + System.lineSeparator());

        try {
            csvGenerator.saveToCsv(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFiles() throws IOException {

        xmlGenerator.closeXmlDocument();
        csvGenerator.createFinalCsvFile(sentenceLengthCounter);
    }
}


