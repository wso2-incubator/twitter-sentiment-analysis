import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.feature.Word2Vec;
import org.apache.spark.mllib.feature.Word2VecModel;
import utils.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

public class LKATag {


    public static void main(String[] args) {

        String logFile = "/home/anoukh/SentimentAnalysis/New Files/distinctTweetChunk.csv"; // Should be some file on your system
        SparkConf conf = new SparkConf().setAppName("TwiiterSentiment").setMaster("local").set("spark.executor.memory", "8G")
                .set("spark.driver.maxResultSize", "16G");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> tweetText = TwitterUtils.loadTwitterData(sc, logFile);
//        JavaRDD<String> tweetText = sc.textFile(logFile).cache();
        List<String> collectedList = tweetText.collect();

        for (String value : collectedList) {
            System.out.println(value);
        }

        JavaRDD<List> splittedTokens = tweetText.map(new Function<String, List>() {
            @Override
            public List call(String s) {
                ArrayList<String> list = new ArrayList<String>();
                Collections.addAll(list, s.split(" "));
                return list;
            }
        });


        Word2Vec word2vec = new Word2Vec().setVectorSize(10);

        Word2VecModel model = word2vec.fit(splittedTokens);

        System.out.println(model.getVectors().size());

        model.save(sc.sc(), "uniqueTweet.model" + System.currentTimeMillis());

    }

}
