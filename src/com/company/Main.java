package com.company;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String file = "test.txt";
        //создание файла
        Vocabulary tmp = new Vocabulary();
        try {
            tmp.add("Test", "KEKW"); //добавление
            tmp.add("Test1", "PUTUR");
            tmp.add("Test2", "MMM");
            tmp.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //чтение файла
        Vocabulary voc = new Vocabulary();
        try {
            voc.load(file);
            String test1 = "test";
            String test2 = "Test";
            String test3 = "Test2";
            System.out.printf("%s - %s\n", test1, voc.find(test1)); //поиск - не найдено
            System.out.printf("%s - %s\n", test2, voc.find(test2)); //поиск - найдено
            System.out.printf("%s - %s\n", test3, voc.find(test3)); //поиск - найдено
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
