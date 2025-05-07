'''
# Run the below once and install all libraries in the virtual environment
import nltk
nltk.download('all')
'''
from rules import rules
from nltk.corpus import wordnet, stopwords
from nltk.stem import WordNetLemmatizer
from nltk.tokenize import word_tokenize
from nltk.tag import pos_tag
import random

def get_pos(pos:str):
    if pos.startswith('N'):
        return wordnet.NOUN
    elif pos.startswith('J'):
        return wordnet.ADJ
    elif pos.startswith('V'):
        return wordnet.VERB
    elif pos.startswith('R'):
        return wordnet.ADV
    else:
        return wordnet.NOUN

def process_query(query: str):
    # Normalizing the input
    query = query.lower()
    # Tokenization
    query_tokens = word_tokenize(query)
    # Stop Word removal
    stop_words = set(stopwords.words('english'))
    query_tokens = [word for word in query_tokens if word not in stop_words]
    # POS Tagging
    pos_tokens = pos_tag(query_tokens)
    # Lemmatization
    lemmas = []
    lemmatizer = WordNetLemmatizer()
    for word, pos in pos_tokens:
        lemmas.append(lemmatizer.lemmatize(word=word, pos=get_pos(pos)))

    # Further enhancement -> give priority to verbs > nouns > adjectives > adverbs > others. I haven't done that but it could improve accuracy
    # Searching through the rules and selecting random choice
    for keywords, response in rules.items():
        for keyword in word_tokenize(query):
            if keyword in keywords:
                return random.choice(response)

    ## Fallback to the original tokens
    for keywords, response in rules.items():
        for keyword in lemmas:
            if keyword in keywords:
                return random.choice(response)


    return random.choice([
        "I'm sorry, I didn't quite get that.",
        "Hmm, I'm not sure about that.",
        "Could you rephrase?"
    ])


def main():
    print("Hello. I am BrewBuddy!")
    while(True):
        user_input = str(input(">> "))
        response = process_query(user_input)
        print("BrewBuddy >> " + response)

if __name__ == "__main__":
    main()