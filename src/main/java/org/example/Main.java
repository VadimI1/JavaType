package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<String> input(String file, String cod) {
        List<String> data = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File(file), cod)) { //Cp1252 "UTF-8"
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                data.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public static void output_txt(String data, String name, String path, String type){
        try(FileWriter writer = new FileWriter(path+name+type+".txt", true))
        {
            writer.write(data);
            writer.append('\n');
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    public static void clear_txt(String name){
        try(FileWriter writer = new FileWriter(name, false))
        {
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    public static void output(List<String> data, String name, String path){
        for (String i : data){
            try {
                double num = Double.parseDouble(i);
                if (i.matches("-?\\d+(\\.\\d+)?") && num % 1 == 0) {
                    output_txt(i, name, path, "integers");
                } else {
                    output_txt(i, name, path, "floats");
                }
            } catch (NumberFormatException e) {
                    output_txt(i, name, path, "strings");
            }
        }
    }

    public static int brief_statistics(String name){
        List<String> data = input(name, "Cp1252");
        return data.size();
    }

    public static void full_statistics_num_double(String name){
        List<String> data = input(name, "Cp1252");
        double min = Double.parseDouble(data.get(0));
        double max = Double.parseDouble(data.get(0));
        double sum = 0;
        double avg = 0;
        for (String i : data){
            double num = Double.parseDouble(i);
            sum += num;
            if (min>num){
                min = num;
            }
            if (max<num){
                max = num;
            }
        }
        avg = sum / data.size();
        System.out.println("Максимальное число: " + max);
        System.out.println("Минимальное число: " + min);
        System.out.println("Сумма всех чисел: " + sum);
        System.out.println("Среднее значение: " + avg);
    }

    public static void full_statistics_num_int(String name){
        List<String> data = input(name, "Cp1252");
        Long min = Long.parseLong(data.get(0));
        Long max = Long.parseLong(data.get(0));
        Long sum = Long.parseLong("0");
        double avg;
        for (String i : data){
            Long num = Long.parseLong(i);
            sum += num;
            if (min>num){
                min = num;
            }
            if (max<num){
                max = num;
            }
        }
        avg = (double) sum / data.size();
        System.out.println("Максимальное число: " + max);
        System.out.println("Минимальное число: " + min);
        System.out.println("Сумма всех чисел: " + sum);
        System.out.println("Среднее значение: " + avg);
    }

    public static void full_statistics_num_str(String name){
        List<String> data = input(name, "Cp1252");
        int max = 0;
        int min = data.get(0).length();
        for (String i : data) {
            if (max<i.length()){
                max = i.length();
            }
            if (min>i.length()){
                min = i.length();
            }
        }
        System.out.println("Максимальное кол-во символов: " + max);
        System.out.println("Минимальное кол-во символов: " + min);
    }

    public static void main(String[] args) throws IOException {
        int length = args.length;
        String name = "";
        String path = "";
        boolean flag = false;

        for (int i = 0; i<length; i++) {
            switch (args[i]) {
                case "-p":
                    if (!args[i + 1].contains(String.valueOf(".txt")) && !args[i + 1].contains(String.valueOf("-p")) &&
                            !args[i + 1].contains(String.valueOf("-a")) && !args[i + 1].contains(String.valueOf("-o")) &&
                            !args[i + 1].contains(String.valueOf("-s")) && !args[i + 1].contains(String.valueOf("-f"))) {
                        name = args[i + 1];
                    } else {
                        throw new IllegalArgumentException("Incorrect name");
                    }
                    break;
                case "-a":
                    flag = true;
                    break;
                case "-o":
                    if (!args[i + 1].contains(String.valueOf(".txt")) && !args[i + 1].contains(String.valueOf("-p")) &&
                            !args[i + 1].contains(String.valueOf("-a")) && !args[i + 1].contains(String.valueOf("-o")) &&
                            !args[i + 1].contains(String.valueOf("-s")) && !args[i + 1].contains(String.valueOf("-f"))) {
                        path = args[i + 1];
                        File file = new File(path);
                        if (!file.exists()) {
                            boolean created = file.mkdirs();

                            if (!created) {
                                throw new FileNotFoundException("Failed to create folder.");
                            }
                        }
                    } else {
                        throw new IllegalArgumentException("Incorrect path");
                    }
                    break;
            }
        }

        if (flag){
            clear_txt(path+name+"integers.txt");
            clear_txt(path+name+"floats.txt");
            clear_txt(path+name+"strings.txt");
        }

        for (String arg : args) {
            if (arg.contains(String.valueOf(".txt"))) {
                output(input(arg, "UTF-8"), name, path);
            }
        }

        for (int i = 0; i<4; i++) {
            switch (args[i]) {
                case "-s":
                    System.out.println("Краткая статистика:");
                    System.out.println("Кол-во элементов с integers: "+brief_statistics(path+name+"integers.txt"));
                    System.out.println("Кол-во элементов с floats: "+brief_statistics(path+name+"floats.txt"));
                    System.out.println("Кол-во элементов с strings: "+brief_statistics(path+name+"strings.txt"));
                    break;
                case "-f":
                    System.out.println("Полная статистика:");
                    System.out.println("Статистика по integers:");
                    System.out.println("Кол-во элементов с integers: "+brief_statistics(path+name+"integers.txt"));
                    full_statistics_num_int(path+name+"integers.txt");

                    System.out.println("Статистика по floats:");
                    System.out.println("Кол-во элементов с floats: "+brief_statistics(path+name+"floats.txt"));
                    full_statistics_num_double(path+name+"floats.txt");

                    System.out.println("Статистика по strings:");
                    System.out.println("Кол-во элементов с strings: "+brief_statistics(path+name+"strings.txt"));
                    full_statistics_num_str(path+name+"strings.txt");
                    break;
            }
        }
    }
}
