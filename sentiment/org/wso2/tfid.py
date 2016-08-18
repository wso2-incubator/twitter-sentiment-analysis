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
# corpus = ["This is very strange indeed",
#           "This is very nice"]
vectorizer = TfidfVectorizer(analyzer='word', min_df=0, max_features=500, stop_words='english',
                             use_idf=True)
# vectorizer = TfidfVectorizer(min_df=1)
X = vectorizer.fit_transform(corpus)
# print X.shape
print("n_samples: %d, n_features: %d" % X.shape)
# print X.data
# X.todense()
# print X[0].toarray()
i = 0
pca = PCA(n_components=20)

print "Using PCA to reduce dimensions"
reducedDimensions = pca.fit_transform(X.toarray())
    # PCA(copy=True, n_components=20, whiten=True)
    # print(pca.explained_variance_ratio_)
for tweet in reducedDimensions:
    writer.writerow(tweet)

# idf = vectorizer._tfidf
# dictionary = dict(zip(vectorizer.get_feature_names(), idf))
# print dictionary.get('cool')

# with open('/home/anoukh/SentimentAnalysis/GloVe-1.2/data/0.3uniqueTweets.csv', 'rb') as g:
# # with open('/home/anoukh/SentimentAnalysis/sexy2.csv', 'rb') as f:
#     reader = csv.reader(g, delimiter=',', quoting=csv.QUOTE_NONE)
#     tweetArray = []
#     for row in reader:  # Loop through csv to get 1st row in csv
#         for sentence in row:    # Loop through Row Array to get each sentence
#             count = 0
#             tweetArray = []
#             tweetArray = [sentence]
#             for word in sentence.split():     # Loop Through the Sentence Array to get each word
#                 wordArray = []
#                 count += 1
#                 try:
#                     vectorlist = dictionary.get(word)
#                     # if not vectorlist:
#                     #     for num in range(0, 1):
#                     #         wordArray.append(0.0)
#                     # else:
#                     #     for single in vectorlist:
#                     #         wordArray.append(single)
#                     if not vectorlist:
#                         wordArray.append(0.0)
#                     else:
#                         wordArray.append(vectorlist)
#                 except ValueError:
#                     print "Value Error Baba"
#                 # except KeyError:
#                 #     for num in range(0, 300):
#                 #         wordArray.append(0.0)
#                 for w in wordArray:
#                     tweetArray.append(w)
#                 # tweetArray.append(wordArray)
#             if count < 28:  # Loop through the remaining amount with zeros
#                 i = count + 1
#                 for i in range(count, 29):  # For each word
#                     i += 1
#                     wordArray = []
#                     # for num in range(0, 1):
#                     #     tweetArray.append(0.0)
#                     tweetArray.append(0.0)
#                     # tweetArray.append(wordArray)
#         # DO Sklearn here for each sentence (tweet)
#         # print tweetArray
#         writer.writerow(tweetArray)
#
# print dictionary.get('hi')
