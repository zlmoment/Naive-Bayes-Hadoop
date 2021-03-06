package com.hackecho.hadoop.nb.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {

    /**
     * Pre-process the tweet
     * 
     * @author: Zhaoyu
     * @param tweet
     * @return
     */
    public static String preProcessTweet(String tweet) {
        return tweet.replaceAll("((www\\.[\\s]+)|(https?://[^\\s]+))", "").replaceAll("@[^\\s]+", "")
                .replaceAll("#([^\\s]+)", "\1").replace('"', ' ').replaceAll("[0-9][a-zA-Z0-9]*", "").replace(',', ' ')
                .replace('.', ' ').replace('!', ' ').replace('?', ' ').replace(':', ' ').replace('*', ' ')
                .replace('\'', ' ').replace('-', ' ').replace('\\', ' ').replaceAll("&quot;", " ").trim();
    }

    /**
     * 
     * convert the training.csv file to an arff file
     * 
     * @author Zhaoyu
     * @param inputFileName
     * @param outputFileName
     */
    public static void convertToARFF(String inputFileName, String outputFileName) {
        // String inputFileName = "training.csv";
        // String outputFileName = "training.arff";
        String line = null;
        try {
            FileReader fileReader = new FileReader(inputFileName);
            FileWriter fileWriter = new FileWriter(outputFileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("@RELATION tweet\n\n@ATTRIBUTE text string\n@ATTRIBUTE class1 {pos,neg}\n\n@DATA\n");
            bufferedWriter.flush();
            while ((line = bufferedReader.readLine()) != null) {
                String temp = "";
                if (line.substring(0, 1).equals("0")) {
                    temp = "pos";
                } else {
                    temp = "neg";
                }
                line = preProcessTweet(line);
                String str = "\"" + line + "\"," + temp + "\n";
                bufferedWriter.write(str);
                bufferedWriter.flush();
            }
            bufferedReader.close();
            bufferedWriter.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("The convertion is done!");
    }
}
