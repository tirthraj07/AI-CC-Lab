import nltk
from nltk.tag import pos_tag
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer
from nltk.corpus import wordnet
from rules import rules
import random

def get_pos(pos: str):
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
    # Normalize the query
    query = query.lower()

    # Tokenize the words:
    query_tokens = word_tokenize(query)

    # Remove Stop Words
    stop_words = set(stopwords.words('english'))
    query_tokens = [word for word in query_tokens if word not in stop_words]

    # Get POS Tags
    token_pos_tags = pos_tag(query_tokens)

    # Lemmatize
    lemmatizer = WordNetLemmatizer()
    lemmas = []
    for word , pos in token_pos_tags:
        lemmas.append(lemmatizer.lemmatize(word=word, pos=get_pos(pos)))

    for keywords, responses in rules.items():
        for keyword in lemmas:
            if keyword in keywords:
                return random.choice(responses)

    return random.choice([
        "I'm sorry, I didn't quite get that.",
        "Hmm, I'm not sure about that.",
        "Could you rephrase?"
    ])


def main():
    print("Welcome to TJM's Coffee Place. I am BrewBuddy, an AI Assistant Chatbot!")

    while(True):
        query = str(input(">>"))
        response = process_query(query)
        print("BrewBuddy >> " + response)


if __name__ == '__main__':
    main()