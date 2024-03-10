from collections import defaultdict as dd

"""A class used to produce default dictionary with the frequency counts of the trigrams within the input string"""

class ProcessTrigrams:
    def __init__(self):
        self.dd_trigrams = dd(float);

    """ count_trigrams takes a string and returns a dictionary of the counts
            of trigrams within the document. """
    def count_trigrams(self,document):
        self.dd_trigrams.clear()
        for counter in range (len(document)-2):
            trigrams = document[counter:counter+3]
            self.dd_trigrams[trigrams] += 1
        return self.dd_trigrams
