package utils;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.util.*;

/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

public class TwitterUtils {
    private static Set stopWords = new HashSet<String[]>();

    static {
        try {
            String wordsAsStr = FileUtils.readFile("resources/stopwords.txt");
            String[] stopwordsArray = wordsAsStr.split("\\s+");
            Collections.addAll(stopWords, stopwordsArray);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static boolean isStopWord(String word) {
        return stopWords.contains(word) || word.trim().length() < 2;
    }


    public static String[] removeStopWords(String[] words) {
        List<String> filteredWords = new ArrayList<String>();
        for (String w : words) {
            if (!isStopWord(w)) {
                String cleanupWord = w.replaceAll("[^A-z0-9#]", "");
                if (cleanupWord.length() > 1) {
                    filteredWords.add(cleanupWord);
                }

            }
        }
        return filteredWords.toArray(new String[0]);
    }


    public static JavaRDD<String> loadTwitterData(JavaSparkContext sc, String file) {
        JavaRDD<String> logData = sc.textFile(file).cache();

        JavaRDD<String> tweetText = logData.map(new Function<String, String>() {
            @Override
            public String call(String s) {
                String[] tokens = s.split(" ");
                String t = new String();
                for (String token:
                     tokens) {
                    t += token.replaceAll("[\"')(]", "").toLowerCase();
                    t = t.replaceAll("http[^\\s,]*", "");
                    t = t.replaceAll("https[^\\s,]*", "");
                    t = t.replaceAll("#", "");
                    t = t.replaceAll("@[A-z0-9]+", "");
                    t = t.replaceAll("[:!,.<]", "");
                    t = t.replaceAll("[0-9]*", "");
                    t = t.replaceAll("&amp;", "");
                    t = t.replaceAll("…", "");
                    t = t.replaceAll("\\n", " ");
                    t = t.replaceAll("rt ", "");
                    t += " ";
                }
                System.out.println(t);
                    return t;
            }
        });

        tweetText = tweetText.filter(new Function<String, Boolean>() {
            public Boolean call(String s) {
                return s != null;
            }
        });
        return tweetText;
    }

    public static float format(double value) {
        return Math.round(1000 * value) / 1000f;
    }
}
