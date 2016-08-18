import csv
from gensim.models import Word2Vec
outputFile = open('/home/anoukh/SentimentAnalysis/vectors.csv', "wb")
writer = csv.writer(outputFile, delimiter=',', quotechar='"', quoting=csv.QUOTE_ALL, escapechar=',')
model = Word2Vec.load_word2vec_format('/home/anoukh/SentimentAnalysis/GoogleNews-vectors-negative300.bin', binary=True)
print "Begin"
with open('/home/anoukh/SentimentAnalysis/data.csv', 'rb') as f:
    reader = csv.reader(f, delimiter=',', quoting=csv.QUOTE_NONE)
    inputArray = []
    for row in reader:  # Each Row
        inputArray = []
        for tweet in row:    # Each Tweet (Assuming only one column in each row)
            inputArray = [tweet]
            count = 0
            for word in tweet.split():     # Each word in the tweet
                count += 1
                try:
                    for vector in model[word]:      # Each vector in word
                        inputArray.append(vector)
                except ValueError:
                    print "Value Error"
                except KeyError:
                    for num in range(0, 300):
                        inputArray.append(0.0)
            if count < 28:  # Loop through the remaining amount with zeros (Assuming max no. of words as 28)
                i = count + 1
                for i in range(count, 29):
                    i += 1
                    for num in range(0, 300):
                        inputArray.append(0.0)
        writer.writerow(inputArray)
