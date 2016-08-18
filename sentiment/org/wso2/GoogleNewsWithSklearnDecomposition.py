# -*- coding: utf-8 -*-
import csv
from gensim.models import Word2Vec
from sklearn.decomposition import PCA

outputFile = open('/home/chehara/Videos/Approach1/unique/GoogleNewsModel/vectorsReduced.csv', "wb")
writer = csv.writer(outputFile, delimiter=',', quotechar='"', quoting=csv.QUOTE_ALL, escapechar=',')
word2VecModel = Word2Vec.load_word2vec_format('/home/chehara/Videos/Approach1/unique/GoogleNewsModel/GoogleNews-vectors-negative300.bin', binary=True)

with open('/home/chehara/Videos/Approach1/unique/TweetSentiments.csv', 'rb') as f:
    reader = csv.reader(f, delimiter=',', quoting=csv.QUOTE_NONE)
    tweetArray = []
    for row in reader:  # Loop through csv to get 1st row in csv
        for sentence in row:    # Loop through Row Array to get each sentence
            # inputArray = [word]
            count = 0
            tweetArray = []
            for word in sentence.split():     # Loop Through the Sentence Array to get each word
                wordArray = []
                count += 1
                try:
                    for single in word2VecModel[word]:      # Loop through the Vector
                        wordArray.append(single)
                except ValueError:
                    print "Value Error Baba"
                except KeyError:
                    for num in range(0, 300):
                        wordArray.append(0.1)
                tweetArray.append(wordArray)
            if count < 28:  # Loop through the remaining amount with zeros
                i = count + 1
                for i in range(count, 29):
                    wordArray = []
                    i += 1
                    for num in range(0, 300):
                        wordArray.append(0.1)
                    tweetArray.append(wordArray)
        # DO Sklearn here for each sentence (tweet)
        pca = PCA(n_components=28)
        pca.fit_transform(tweetArray)
        PCA(copy=True, n_components=2, whiten=True)
        #print(pca.explained_variance_ratio_)
        writer.writerow(pca.explained_variance_ratio_)
