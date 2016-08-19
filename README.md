# sentiment-analysis
Accounted Datasets:

200K tweets continuously collected for 3 days regarding US Election 2016

100K tweets regarding Election 2016

In Twitter Sentiment Analysis we used 2/3 of unique tweets as the training set, and the rest as the test set. Initially this datasets went through 4 different sentiment analyzing approaches separately as follows after preprocessing:
    
    1.Afinn
    
    2.Stanford NLP
    
    3.MS Text Analytics
    
    4.Negative-Positive
    
Then the results were categorized into 3 classes based on the scale of [-1,0,1] . For the purpose of getting most preferred result out of 4 results below techniques had been used:
    
    1.Getting 3/4 chances considered as "Agreed", and the rest as "Conflict"
        For 200K -> Agreed : Conflict ratio was 69% : 31%
        For 100K -> Agreed : Conflict ratio was 71% : 29%

    2.By giving a weight for each approach according to a predefined criteria and getting the sum as :
        Sum = Afinn*0.2 + Stanford*0.35 + Negative-Positive*0.1 + MS*0.35
        then partitioning done by contemplating their combinations,permutations,lower boundaries and upper boundaries,
        sum<-0.45 --> -1 , sum>0.45 --> 1 , getting zero 3/4 chances and -0.1<=sum<=0.1 --> 0

        For 200K -> Agreed : Conflict ratio was 76% : 24%
        For 100K -> Agreed : Conflict ratio was 74% : 26%
        
    Then all agreed tweets ran through TweetToVec model by assuming
        Number of words per tweet = 140 characters per tweet (max) /4 characters --> Approximately 35 words
        Vector size per word = 10
        Total Vector size per tweet = 350

    Final outcome would be a file 200K/100K tweets * 350 tweet vector, and this method returns only the unique tweets with it's tweet vector. So these tweets were ran through the above 4 approaches again and attached the most preferred sentiment result in addition to it's tweet vector. In here you can verify the final sentiment result because it gives all the tweets as "Agreed".

    Finally,these results were plugged into Random Forest Classification Algorithm of wso2 Machine Learner to predict the accuracy. Following results will show you how the accuracy of prediction had been varied.
        200K --> 61.1% (1)
        200K --> 56% (2)

Final Results
https://drive.google.com/a/wso2.com/folderview?id=0ByBNSIHzVEPeZnNCVDlnaHE0QTg&usp=sharing
