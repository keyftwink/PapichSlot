package com.papich.game;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Money {
    public static int getMoney(){
        try(BufferedReader fr = new BufferedReader(new FileReader("money.txt"))){
            return Integer.parseInt(fr.readLine());
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
            return 0;
        }
    }
    public static void setMoney(int money){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("money.txt"))){
            bw.write(String.valueOf(money));
            bw.flush();
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
