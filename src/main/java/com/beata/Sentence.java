package com.beata;

import javax.xml.bind.annotation.*;
import java.util.*;

// hashCode and equals should be overwritten to allow
// usage of Sentence as a key in HashMap

@XmlRootElement(name = "sentence")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sentence {

    @XmlElement(name = "word")
    private ArrayList<String> words;


    public Sentence() {
        words = new ArrayList<>();
    }

    // unused method
    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return words;
    }

    @Override
    public String toString() {
        final String SEPARATOR = ",";
        StringBuilder stringBuilder = new StringBuilder();

        for (String word : words) {
            stringBuilder.append(SEPARATOR + word);
        }
        return stringBuilder.toString();
    }
}




