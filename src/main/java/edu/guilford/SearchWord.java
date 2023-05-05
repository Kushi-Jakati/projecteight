package edu.guilford;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchWord implements Comparable<SearchWord> {

    private String word;
    private int count;

    public SearchWord(String word, int count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return this.word;
    }

    public int getCount() {
        return this.count;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /// toString
    @Override
    public String toString() {
        return this.word + " " + this.count;
    }

    // compareTo method that sorts the count in descending order
    @Override
    public int compareTo(SearchWord word) {
        return word.getCount() - this.getCount();
    }
}
