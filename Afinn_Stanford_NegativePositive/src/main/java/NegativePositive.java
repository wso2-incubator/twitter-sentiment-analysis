import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NegativePositive {
    static FileWriter writer;
    private static String Option = "COMMON";
    private String[] positiveWordBucket;
    private String[] negativeWordBucket;

    public static void main(String[] args) throws Exception {
        writer = new FileWriter("/home/chehara/100K/sentiment/CommonSet100K.csv");
        NegativePositive tt=new NegativePositive();
        try {
            tt.negativeWordBucket = tt.getWordsBuckets("negativewords.txt");
            System.out.println(tt.negativeWordBucket[7]);
        } catch (IOException e) {
            System.out.println("Failed to load  negativewords.txt  file ");
        }
        try {
            tt.positiveWordBucket = tt.getWordsBuckets("positivewords.txt");
            System.out.println(tt.positiveWordBucket[7]);
        } catch (IOException e) {
            System.out.println("Failed to load positivewords.txt  file ");
        }

        BufferedReader dataBuffer = null;
        String tweetRecord;
        dataBuffer = new BufferedReader(new FileReader("/home/chehara/100K/1By3Of100KDataSet.csv"));

        int count=0;
        while ((tweetRecord = dataBuffer.readLine()) != null) {
//            System.out.println(tt.getCommonSentimentRate(tweetRecord));
            writer.write(Integer.toString(tt.getCommonSentimentRate(tweetRecord)));
            writer.append('\n');
        }
        writer.flush();
    }

    public int getCommonSentimentRate(String contentText) {
        int positiveRank = 0;
        for (int i = 0; i < positiveWordBucket.length; i++) {
            Matcher m = Pattern.compile("\\b" + positiveWordBucket[i] + "\\b").matcher(contentText);
            while (m.find()) {
                positiveRank++;
            }
        }
        int negativeRank = 0;
        for (int i = 0; i < negativeWordBucket.length; i++) {
            Matcher m = Pattern.compile("\\b" + negativeWordBucket[i] + "\\b").matcher(contentText);
            while (m.find()) {
                negativeRank++;
            }
        }
        return positiveRank - negativeRank;

    }
    public String[] getWordsBuckets(String fileName) throws IOException {
        String[] wordList = null;
        StringBuilder textChunk = new StringBuilder();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                textChunk.append(line).append("\n");
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error reading "+fileName+" File");
        }
        wordList = textChunk.toString().split(",");
        return wordList;
    }

    public static ArrayList<String> CSVtoArrayList(String text) {
        ArrayList<String> result = new ArrayList<String>();
        if (text != null) {
            result.add(text);
        }
        return result;
    }
}
