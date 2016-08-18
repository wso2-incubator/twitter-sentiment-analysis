import csv
from gensim.models import Word2Vec
outputFile = open('/home/anoukh/SentimentAnalysis/vectorsReduced.csv', "wb")
writer = csv.writer(outputFile, delimiter=',', quotechar='"', quoting=csv.QUOTE_ALL, escapechar=',')
word2VecModel = \
    Word2Vec.load_word2vec_format('/home/anoukh/SentimentAnalysis/GoogleNews-vectors-negative300.bin', binary=True)
print "Begin"

with open('/home/anoukh/SentimentAnalysis/data.csv', 'rb') as f:
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
                    print "Value Error"
                except KeyError:
                    for num in range(0, 300):
                        wordArray.append(0.0)
                tweetArray.append(wordArray)
            if count < 28:  # Loop through the remaining amount with zeros
                i = count + 1
                for i in range(count, 29):  # For each word
                    i += 1
                    wordArray = []
                    for num in range(0, 300):
                        wordArray.append(0.0)
                    tweetArray.append(wordArray)
        print tweetArray
        writer.writerow(tweetArray)
