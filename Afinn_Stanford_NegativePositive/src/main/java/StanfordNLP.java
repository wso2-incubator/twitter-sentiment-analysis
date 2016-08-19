import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class StanfordNLP {
    static FileWriter writer;
    public static void main(String[] args) throws Exception {
        writer = new FileWriter("/home/chehara/100K/sentiment/StanfordSet100K.csv");
        StanfordNLP tt=new StanfordNLP();

        BufferedReader buffer = null;
        String line;
        buffer = new BufferedReader(new FileReader("/home/chehara/100K/1By3Of100KDataSet.csv"));

        int count=0;
        while ((line = buffer.readLine()) != null) {
            writer.write(Integer.toString(tt.getStanfordSentimentRate(line)));
            writer.append('\n');
        }
        writer.flush();
    }

    public int getStanfordSentimentRate(String sentimentText) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        //StanfordCoreNLP
        int totalRate = 0;
        String[] linesArr = sentimentText.split("\\.");
        for (int i = 0; i < linesArr.length; i++) {
            if (linesArr[i] != null) {
                Annotation annotation = pipeline.process(linesArr[i]);
                for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                    Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                    int score = RNNCoreAnnotations.getPredictedClass(tree);
                    totalRate = totalRate + (score - 2);
                }
            }
        }
        return totalRate;
    }

    public static ArrayList<String> CSVtoArrayList(String text) {
        ArrayList<String> result = new ArrayList<String>();
        if (text != null) {
            result.add(text);
        }
        return result;
    }
}
