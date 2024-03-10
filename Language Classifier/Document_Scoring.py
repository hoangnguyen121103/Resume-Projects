from Classifier_training import Classifier_training
from Trigrams import ProcessTrigrams

"""
Generating a similarity score between the input document and the document containing language
the classifier learned from.
Note: the length of the frequency vector for the test document is not normalised as the aim is only to
find the language with highest score. If the document is to be normalised as well, the language scores 
will be divided by a constant value, which will impact the run - time of the program, hence unnecessary 
"""
class Document_Scoring:
    def __init__(self):
        self.classifier = Classifier_training()
        self.trigramsProcessor = ProcessTrigrams()

    def training(self,CSVFile):
        self.default_lang_counts = self.classifier.train(CSVFile)

    def score_document(self,document):
        """
        score_document takes a string document and a dictionary of language
        counts per language stored in lang_counts. It returns a dictionary of
        scores for the document for each language.
        """
        with open(document, 'r') as file:
            content = file.read()
            content_triCount = self.trigramsProcessor.count_trigrams(content)
            score_dict = {}
            for lang in self.default_lang_counts:
                score = 0.0
                for triagram in content_triCount:
                    score += content_triCount[triagram] * self.default_lang_counts[lang][triagram]
                score_dict[lang] = score
        return score_dict
