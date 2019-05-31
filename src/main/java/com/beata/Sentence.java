package com.beata;

import javax.xml.bind.annotation.*;
import java.util.*;


@XmlRootElement(name = "sentence")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sentence {

    @XmlElement(name = "word")
    private ArrayList<String> words;


    public Sentence() {

        words = new ArrayList<>();
    }



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




