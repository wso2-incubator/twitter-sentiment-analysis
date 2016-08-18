from sklearn.decomposition import PCA
from sklearn.feature_extraction.text import TfidfVectorizer
import csv
outputFile = open('/home/anoukh/SentimentAnalysis/0.3uniqueTFIDFTweetVectorsSize200.csv', "wb")
writer = csv.writer(outputFile, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL, escapechar=',')
with open('/home/anoukh/SentimentAnalysis/GloVe-1.2/data/0.3uniqueTweets.csv', 'rb') as f:
    reader = csv.reader(f, delimiter=',', quoting=csv.QUOTE_NONE)
    corpus = []
    for row in reader:
        for sentence in row:
            corpus.append(sentence)
vectorizer = TfidfVectorizer(analyzer='word', min_df=0, max_features=500, stop_words='english',
                             use_idf=True)
X = vectorizer.fit_transform(corpus)
print("n_samples: %d, n_features: %d" % X.shape)
i = 0

print "Using PCA to reduce dimensions"
pca = PCA(n_components=20)
reducedDimensions = pca.fit_transform(X.toarray())
for tweet in reducedDimensions:
    writer.writerow(tweet)
