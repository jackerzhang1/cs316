/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author heten
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Tokenizer {
    private static List<String> reservedWords;
    private static List<String> operator;
      private static Set<String> punctuators ;
    private static Set<Character> variable;
    private static final String Characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_";
    private static final String constant = "0123456789";
  

    static {
        reservedWords = Arrays.asList("while", "boolean", "if", "else", "for", "int", "float", "char", "return", "void","double","dbl");
        operator= Arrays.asList("=", "= =", "<", ">", "<=", ">=", "+", "-", "*", "/");
        variable= new HashSet<>();
        punctuators= new HashSet<>(Arrays.asList(";", ",", "{", "}", "(", ")",".",">","<"));
       
    }

    public static void main(String[] args) throws IOException {
        File inputFile = new File("input.txt");
        String statement;
        try {
            BufferedReader input = new BufferedReader(new FileReader(inputFile));
            

            while ((statement = input.readLine()) != null) {
                if (statement.trim().startsWith("/***") || statement.trim().startsWith("/*") ||statement.trim().startsWith(" ")
                        || statement.trim().endsWith("*/")) {
                    continue;
                }
                System.out.println("\n Statement: " + statement);
                Recursive(statement);
            }

            input.close();
       
        } catch (FileNotFoundException e) {
             System.out.println("professor, you may use wrong input file: " + inputFile.getName());
        }
    }

    private static void Recursive(String line) {
       
        String[] tokens = line.split("(?<=\\W)|(?=\\W)");

        for (String token : tokens) {
            token = token.trim();
            if (token.isEmpty()) {
                continue;
            }

            if (ReservedWord(token)) {
                System.out.println("Next Token is: reserved words, Next Lexeme is: " + token);
            } 
            else if (Operator(token)) {
                System.out.println("Next Token is: operators ASSIGN_OP, Next Lexeme is: " + token);
            } 
            else if (PUNCTUATOR(token)) {
                System.out.println("Next Token is punctuator, Next Lexeme is: " + token);
            } else if (isValidVariable(token)) {
                System.out.println("Next Token is: variable, Next Lexeme is: " + token);
            } else if (isValidConstant(token)) {
                System.out.println("Token: constants number, Lexeme: " + token);
            } else {
                System.out.println("Next Token is:unknown, Next Lexeme is: " + token);
            }
        }
    }



   private static boolean isValidVariable(String word) {
        if (word.isEmpty() || !Character.isLetter(word.charAt(0))) {
            return false;
        }
        for (char c : word.toCharArray()) {
            if (! Characters.contains(Character.toString(c))) {
                return false;
            }
        }
        return true;
    }

    // Method to check if a string is a valid constant
    private static boolean isValidConstant(String word) {
        if (word.isEmpty()) {
            return false;
        }
        for (char c : word.toCharArray()) {
            if (!constant.contains(Character.toString(c))) {
                return false;
            }
        }
        return true;
    }

 private static boolean ReservedWord(String token) {
        return reservedWords.contains(token);
    }

    private static boolean Operator(String token) {
        return operator.contains(token);
    }

    private static boolean PUNCTUATOR(String token) {
        return punctuators.contains(token);
    }
}