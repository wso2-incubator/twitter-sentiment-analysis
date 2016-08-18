import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Afinn {
    static FileWriter writer;
    public String[] affinWordBucket;

    public static void main(String[] args) throws Exception {
        writer = new FileWriter("/home/chehara/100K/sentiment/AffinSet100k.csv");
        Afinn tt=new Afinn();
        try {
            tt.affinWordBucket = tt.getWordsBuckets("affinwords.txt");
            System.out.println(tt.affinWordBucket[7]);
        } catch (IOException e) {
            System.out.println("Failed to load affinwords.txt file ");
        }

        BufferedReader dataBuffer = null;
        String tweetRecord;
        dataBuffer = new BufferedReader(new FileReader("/home/chehara/100K/1By3Of100KDataSet.csv"));


       int count=1;
        while ((tweetRecord = dataBuffer.readLine()) != null) {
            ArrayList<String> receivedResult = new ArrayList<String>();
            receivedResult = CSVtoArrayList(tweetRecord);
            writer.write(Integer.toString(tt.getAffinSentimentRate(receivedResult.get(0))));
            writer.append('\n');
            count++;
        }
        writer.flush();
    }

    public int getAffinSentimentRate(String contentText){
        int rank=0;
        String[] split;
        try{
            for (int i = 0; i < affinWordBucket.length; i++) {
                split = affinWordBucket[i].split(" ");

                String word = split[0].trim();
                int val = Integer.parseInt(split[split.length-1].replaceAll("\\s+", " ").trim());
                Matcher m = Pattern.compile("\\b" + word + "\\b").matcher(contentText);
                while (m.find()) {
                    rank += val;
                }
            }
        }catch (NumberFormatException e)
        {
            e.printStackTrace();
        }

        return rank;
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
