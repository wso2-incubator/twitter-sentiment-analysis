import argparse
import csv
outputFile = open('/home/anoukh/SentimentAnalysis/0.3uniqueGloveTweetVectors.csv', "wb")
writer = csv.writer(outputFile, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL, escapechar=',')
parser = argparse.ArgumentParser()
parser.add_argument('--vectors_file', default='/home/anoukh/SentimentAnalysis/GloVe-1.2/build/vectors.txt', type=str)
args = parser.parse_args()

with open(args.vectors_file, 'r') as f:
    vectors = {}
    for line in f:
        vals = line.rstrip().split(' ')
        vectors[vals[0]] = map(float, vals[1:])

print "Loaded Vectors"

with open('/home/anoukh/SentimentAnalysis/GloVe-1.2/data/0.3uniqueTweets.csv', 'rb') as f:
    reader = csv.reader(f, delimiter=',', quoting=csv.QUOTE_NONE)
    tweetArray = []
    for row in reader:  # Loop through csv to get 1st row in csv
        for sentence in row:    # Loop through Row Array to get each sentence
            count = 0
            tweetArray = []
            tweetArray = [sentence]
            for word in sentence.split():     # Loop Through the Sentence Array to get each word
                wordArray = []
                count += 1
                try:
                    vectorlist = vectors.get(word)
                    if not vectorlist:
                        for num in range(0, 25):
                            wordArray.append(0.0)
                    else:
                        for single in vectorlist:
                            wordArray.append(single)
                except ValueError:
                    print "Value Error"
                for w in wordArray:
                    tweetArray.append(w)
            if count < 28:  # Loop through the remaining amount with zeros
                i = count + 1
                for i in range(count, 29):  # For each word
                    i += 1
                    wordArray = []
                    for num in range(0, 25):
                        tweetArray.append(0.0)
        writer.writerow(tweetArray)