import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.feature.Word2VecModel;
import org.apache.spark.mllib.linalg.Vector;
import utils.TwitterUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


public class Tweet2vecModel {
    public static void main(String[] args) {
        String logFile = "/home/anoukh/SentimentAnalysis/ml-projects-java/NewFiles/uniqueTweetsNoHold.csv"; // Should be some file on your system

        SparkConf conf = new SparkConf().setAppName("LKA").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> tweetText = TwitterUtils.loadTwitterData(sc, logFile);
        List<String> collectedList = tweetText.collect();

        Word2VecModel model = Word2VecModel.load(sc.sc(), "uniqueTweet.model");

        java.util.Map<String, List<Double>> finalVector = new HashMap<String, List<Double>>();
        long count = 0;
        for (String tweet : collectedList) { //For each tweet
            double[] totalVectorValue;
            List<Double> theList = new ArrayList<Double>();
            String[] tokens = tweet.split(" ");

            for (String word: tokens) { //For each word in the tweet
                count++;
                Vector vectorValues = null;
                try{
                    vectorValues = model.transform(word);
                }catch (IllegalStateException e){
//                    e.printStackTrace();
                    count++;
                }
                if (vectorValues != null){
                    totalVectorValue = vectorValues.toArray();
                    for (double temp: totalVectorValue) {
                        theList.add(temp);
                    }
                }
                if (count == 30){
                    break;
                }
            }

            finalVector.put(tweet, theList);
        }

        int max = 300;

        java.util.Iterator<java.util.Map.Entry<String, List<Double>>> iterate = finalVector.entrySet().iterator();

        while (iterate.hasNext()){
            java.util.Map.Entry entry = iterate.next();
            List<Double> val = (List<Double>) entry.getValue();

            for (int i = val.size(); i < max; i ++){
                val.add(0.0d);
            }
        }

        for (Object temp: finalVector.values()) {
            if (max < ((List<Double>)temp).size()){
                max = ((List<Double>)temp).size();
            }

        }

        File file = new File("/home/anoukh/SentimentAnalysis/ml-projects-java/NewFiles/agreedOnlyTweetsOutOf60KVectorsNoHold.csv"); //Output File
        FileWriter writer = null;
        // if file doesnt exists, then create it
        try{

            if (!file.exists()) {
                file.createNewFile();
            }

            writer = new FileWriter(file);


            java.util.Iterator<java.util.Map.Entry<String, List<Double>>> iterate2 = finalVector.entrySet().iterator();

            while (iterate2.hasNext()){
                java.util.Map.Entry entry = iterate2.next();
                List<Double> val = (List<Double>) entry.getValue();

                String key = (String)entry.getKey();
                writer.append(key);

                for (int i = 0; i < val.size(); i ++){
                    writer.append(",");
                    writer.append(Double.toString(val.get(i)));
                }
                writer.append("\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
