package main;

import ansScrapper.AnsScrapper;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnsScrapper scrapper = new AnsScrapper();
        List<String> zips = scrapper.iniciar();
    }

}
