package ru.itis.bukarev.homework2;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        while (true) {
            System.out.println("Введите 1 если хотите добавить игрока. \nВведите 2 если хотите заключить сделку с " +
                    "игроками из списка");
            Scanner sc = new Scanner(System.in);
            int type = sc.nextInt();
            if (type == 1) {
                PlayerProducer.produce();

            } else if(type == 2) {
                DocumentProducer.produce();
            }
        }
    }
}
