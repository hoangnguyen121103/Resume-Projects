from Document_Scoring import Document_Scoring

"""
The idea was to generate a "score"  that measures 
the closeness of a document counts to the language counts. 
For that reason, trigram counts will be considered as a vectors, then 
normalise (to avoid the magnitude difference) and the highest dot product
between the language counts and document counts will be selected (2 unit vectors that are
more aligned with each other will give a higher dot product)
"""
# Tolerance for detecting null predictions
TOL = 1e-10
# Main function
# Please use the path to the training file (.../Language Classifier/Files/TrainingFile/your-file-name)
# Please use the path to the document file (.../Language Classifier/Files/DocumentFile/your-file-name)
def classify_doc(trainingFile,document):
    document_score = Document_Scoring()
    document_score.training(trainingFile)
    scores = document_score.score_document(document)

    if len(scores) < 2:
        print("Insufficient data to classify.")
        return

    vals = sorted(scores.values(),reverse=True)
    if abs(vals[0] - vals[1]) <= TOL or vals[0] <= TOL:
        print("Ambiguous or very low scores; defaulting to English.")
    else:
        max_lang = max(scores,key=scores.get)
        print(max_lang)


