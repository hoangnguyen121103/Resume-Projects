
from collections import defaultdict as dd
from math import sqrt
from Trigrams import ProcessTrigrams
import csv

"""
Trains the classifier by giving it a document in CSV format ("Language","Text written in that langauge")
"""
class Classifier_training:
    def __init__(self):
        self.lang_dict = {}
        self.TrigramsProcessor = ProcessTrigrams()

    def normalise(self,counts_dict):
        """ normalise takes a dictionary of trigram counts counts_dict and
        normalises it by its length."""
        mag = sqrt(sum([x ** 2 for x in counts_dict.values()]))
        if mag == 0:
            return {key: 0 for key in counts_dict}
        return dd(int, {key: value / mag for (key, value) in counts_dict.items()})

    def train(self, training_data):
        """ train_classifier takes a csv filename training_set as a string and
            returns a dictionary of average trigram-counts per language. """
        """self.lang_dict = {} will reset everytime this function is called"""
        with open(training_data, 'r') as file:
            csv_reader = csv.reader(file)
            for row in csv_reader:
                (lang, text) = row
                if lang not in self.lang_dict:
                    self.lang_dict[lang] = dd(float)
                trigrams_count = self.TrigramsProcessor.count_trigrams(text)
                for trigram in trigrams_count:
                    self.lang_dict[lang][trigram] += trigrams_count[trigram]
            for lang in self.lang_dict:
                self.lang_dict[lang] = self.normalise(self.lang_dict[lang])
        return self.lang_dict
