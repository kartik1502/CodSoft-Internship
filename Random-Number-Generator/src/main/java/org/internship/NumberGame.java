package org.internship;

import java.util.Random;
import java.util.Scanner;

public class NumberGame {

    public static void main(String[] args) {

        int chances = 5;
        int round = 0;
        int won = 0;
        boolean play = true;
        while(play) {
            round++;
            System.out.println("Guess the number between 1 to 100"+"\nYou have "+chances+" to guess the number"+"\nThis is round "+round);
            int number = new Random().nextInt(100) + 1;
            Scanner scanner = new Scanner(System.in);

            while (chances > 0) {
                System.out.println("This is your "+(5 - chances + 1)+" chance, Enter your guess: ");
                int guess = scanner.nextInt();
                if (guess == number) {
                    won++;
                    System.out.println("You guessed the number in "+chances+" chances");
                    break;
                } else if (guess < number) {
                    System.out.println("Your guess is too low");
                    chances--;
                    System.out.println("You have "+chances+" chances left");
                } else {
                    System.out.println("Your guess is too high");
                    chances--;
                    System.out.println("You have "+chances+" chances left");
                }
            }
            System.out.println("Wanna play again?? (y/n)");
            String answer = scanner.next();
            play = answer.equals("y");
            if (play)
                chances = 5;
        }
        System.out.println(
            "Thank you for playing"+ "\n You played "+round+" rounds"+"\n You won "+won+" times"
        );
    }
}
