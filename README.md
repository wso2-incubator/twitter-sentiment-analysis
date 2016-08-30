# sentiment-analysis
Accounted Datasets:

200K tweets continuously collected for 3 days regarding US Election 2016

100K tweets regarding Election 2016

In Twitter Sentiment Analysis we used 2/3 of unique tweets as the training set, and the rest as the test set. Initially this datasets went through 4 different sentiment analyzing approaches separately as follows after preprocessing:
   **1.Afinn**
   AFINN is a list of words rated for valence with an integer between minus five (negative) and plus five (positive).
    
  **2.Stanford NLP**  
    
  **3.MS Text Analytics**  
  This returns a numeric score between 0 and 1. Scores close to 1 indicate positive sentiment, while scores close to 0 indicate negative sentiment. Sentiment score is generated using classification techniques. The input features to the classifier include n-grams, features generated from part-of-speech tags, and word embeddings. 
      
  **4.Negative-Positive**
    
Then the results were categorized into 3 classes based on the scale of [-1,0,1] . For the purpose of getting most preferred result out of 4 results below techniques had been used:
    
    1.Getting 3/4 chances considered as "Agreed", and the rest as "Conflict"
        For 200K -> Agreed : Conflict ratio was 69.3% : 30.7%
        For 100K -> Agreed : Conflict ratio was 42.9% : 57.1%

    2.By giving a weight for each approach according to a predefined criteria and getting the sum as :
        Sum = Afinn*0.2 + Stanford*0.35 + Negative-Positive*0.1 + MS*0.35
        then partitioning done by contemplating their combinations,permutations,lower boundaries and upper boundaries,
        sum<-0.45 --> -1 , sum>0.45 --> 1 , getting zero 3/4 chances and -0.1<=sum<=0.1 --> 0

        For 200K -> Agreed : Conflict ratio was 86.9% : 13.1%
        For 100K -> Agreed : Conflict ratio was 70.77% : 29.22%
        
   We have now obtained a dataset with a collection of tweets and their corresponding sentiments. To create a prediction model for sentiments we are using WSO2's Machine Learner. 
   To use Random Forest Classification we used the following techniques to represent a tweet in a numerical manner
   
    1.  Word2Vec in Apache Spark ML Library
    2.  Word2Vec in Python's Gensim Library
    3.  Global Vector (Glove) Library
    4.  TFIDF in Python's sklearn Library
    
   **1. Word2Vec in Apache Spark ML Library** 
   
  Using the Word2Vec implementation, we created a Tweet2Vec feature that generates the vectors of a tweet instead of a word. Find this implementation in _ApacheSparkNLP_ folder.
  The LKATag class was used to create a word2vecmodel and the Tweet2vecModel was used to generate the vectors for each tweet.
  
  The following were the features of the tweets that were used in running the Tweet2Vec
           
           Number of words per tweet = 140 characters per tweet (max) /4 characters --> Approximately 35 words
           Vector size per word = 10
           Total Vector size per tweet = 350
   
  The output of the tweet2vec is the unique tweets and the corresponding vectors. The sentiment of each unique tweet was again found and a final dataset was created.
   
  Having represented each tweet in a vector format we plug the vectors and their respective sentiment into Random Forest Classification Algorithm of wso2 Machine Learner. Following results will show you how the accuracy of prediction had been varied.
            
            200K using method 1 to find sentiments was 61.1%
            200K using method 2 to find sentiments was 56%
            
            100K using method 1 to find sentiments was 61.1%
            100K using method 2 to find sentiments was 56%
   
   **2. Word2Vec in Python's Gensim Library**
   
   Another word2vec library we used was the gensim python library. Find the code in the python file named _GoogleNewsWithSklearnDecomposition.py_ in the folder _Glove_TFID_GoogleNews._
   For this method we used the already available _GoogleNews-vectors-negative300.bin_ model to generate the vectors.
   
   Following are the features of the vectors generated using gensim
   
           Vector size per word = 300
           Number of words per tweet = 28
           Vector size per tweet = 300*28 = 8400
   
   Such a vector size generated a large output file, the size of which was not supported by the machine learner. So therefore we used sklearns PCA to reduce the vector size from 8400 to 28 for each tweet.
   After this, the vectors and their corresponding sentiment was fed into the machine learner and the results are as follows 
            
            200k using method 1 to find sentiments was 59.26% accuracy
            200k using method 2 to find sentiments was 56.37% accuracy
      
   **3. Stanford's Global Vector (Glove) Library**
   
   The next method we used to represent the tweets in a numerical manner was generating vectors using the Glove library.
   Initially we used the glove twitter model provided in the stanford website but since our tweets were election tweets the accuracy rate was very minimum.
   Therefore we created our own model and used that to generate the tweet representation. Find the code in the python file _glove.py_ in the folder _Glove_TFID_GoogleNews_
   
   The accuracy of the random forest classification model generated using the machine learner using the Glove data set is 50.02%
   
   **4. TFIDF in Python's sklearn Library**
   
   The final method used to represent the tweets in a numerical manner is the TFIDF (Term Frequency Inverse Document Frequency)
   Find the code in the python file _tfid.py_ in the folder _Glove_TFID_GoogleNews_
   Using this method, a tweet is represented in a 500 numerical values which we reduced to 20 by using the PCA method.
   
   The accuracy of the random forest classification model generated using the machine learner using the TFIDF data set is 50.62%
   


Final Results
https://drive.google.com/a/wso2.com/folderview?id=0ByBNSIHzVEPeZnNCVDlnaHE0QTg&usp=sharing
