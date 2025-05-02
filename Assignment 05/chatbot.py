import nltk
nltk.download("all", quiet=True)

from nltk.corpus import wordnet
from nltk.stem import WordNetLemmatizer
from nltk.tokenize import word_tokenize
from nltk import pos_tag
import random


# --- Helper function for Lemmatization with POS tagging ---
# WordNetLemmatizer works better if it knows the part-of-speech (POS)

def get_wordnet_pos(treebank_tag):
    if treebank_tag.startswith('J'):
        return wordnet.ADJ
    elif treebank_tag.startswith('V'):
        return wordnet.VERB
    elif treebank_tag.startswith('N'):
        return wordnet.NOUN
    elif treebank_tag.startswith('R'):
        return wordnet.ADV
    else:
        # Default
        return wordnet.NOUN
    

lemmatizer = WordNetLemmatizer()

# --- Preprocessing function ---
def preprocess_text(text:str):
    text = text.lower()
    tokens = word_tokenize(text)
    tagged_tokens = pos_tag(tokens)
    lemmas = [lemmatizer.lemmatize(word=word, pos=get_wordnet_pos(tag)) for word, tag in tagged_tokens]
    return lemmas


rules = {
    # --- Basic Greetings & Farewells ---
    ('hello', 'hi', 'hey', 'greeting', 'wassup'):
        ["Hello there! Welcome to our cafe. How can I help you today?", "Hi! What can I get for you?", "Hey! Good to see you."],
    ('thank', 'thanks', 'appreciate'):
        ["You're welcome!", "No problem! Enjoy your time here.", "Happy to help!"],
    ('bye', 'goodbye', 'exit', 'quit', 'see you'):
        ["Goodbye! Hope to see you again soon.", "Farewell! Have a wonderful day.", "Bye! Come back anytime."],

    # --- Cafe Specific ---
    ('menu', 'offer', 'serve', 'have', 'selection', 'list'):
        ["You can find our full menu on the board above the counter.", "Our menu includes various coffees, teas, pastries, sandwiches, and snacks.", "Please check the menu board for all our offerings and prices."],
    ('coffee', 'brew', 'drip', 'filter', 'cup'):
        ["We have a variety of freshly brewed coffees! Anything specific you'd like?", "Our coffee selection includes drip, pour-over, and espresso-based drinks. Check the menu!", "Yes, we serve delicious coffee. Our baristas can help you choose."],
    ('latte', 'cappuccino', 'espresso', 'americano', 'mocha', 'macchiato', 'flat white'): 
        ["Yes, we make those! Check our menu board for details and options like milk choices.", "Absolutely, that's on our menu.", "We can definitely make that for you."],
    ('tea', 'chai'):
        ["We have a selection of teas, including black, green, herbal infusions, and chai latte.", "Yes, we offer various types of tea. What kind are you in the mood for?", "Tea is available. Please ask the barista about today's selection or see the menu."],
    ('food', 'eat', 'hungry', 'snack', 'pastry', 'cake', 'muffin', 'croissant', 'sandwich', 'bite'):
        ["We have a delicious selection of pastries, sandwiches, cakes, and snacks. Check our display case or menu board!", "Feeling hungry? We offer freshly baked goods and light meals.", "Yes, we have food options available. See our menu or ask our staff."],
    ('wifi', 'internet', 'wi-fi', 'network', 'password'):
        ["Yes, we offer free WiFi for customers. The network name is 'CafeConnect_Guest' and the password is 'enjoyyourcoffee'.", "Free WiFi is available! The password should be on a sign near the counter, or just ask.", "Yes, WiFi is available for our guests. Network: CafeConnect_Guest, Password: enjoyyourcoffee"],
    ('pay', 'payment', 'card', 'cash', 'credit', 'upi', 'gpay', 'paytm', 'phonepe', 'scan'): 
        ["We accept cash, credit/debit cards, and major UPI payments (like GPay, PhonePe, Paytm).", "You can pay with cash, card, or UPI scan.", "Payment options include cash, all major cards, and UPI apps."],
    ('special', 'deal', 'offer', 'discount', 'promotion', 'combo'):
        ["Ask our barista about today's specials or any ongoing combos!", "We sometimes have seasonal offers. Please check the board or ask our staff.", "Check our board near the counter for any current deals or special offers."],
    ('vegan', 'vegetarian', 'gluten-free', 'allergy', 'dairy-free', 'milk', 'soy', 'oat', 'almond'): 
        ["We offer vegetarian options and alternative milks (like oat or almond). Please mention any allergies or dietary needs when ordering.", "We have options! Let the barista know your requirements when you order.", "Please inform our staff about any allergies. We have oat and almond milk available."],
    ('restroom', 'bathroom', 'toilet', 'washroom', 'loo'):
        ["The restroom is located at the back, just past the counter to the left.", "Yes, the washroom is available for customers. It's down that short hallway.", "The restroom is just around the corner there."],
    ('book', 'reservation', 'reserve', 'table'):
        ["We generally operate on a first-come, first-served basis, but you can call ahead for larger groups.", "Reservations aren't usually required, just come on in!", "We don't typically take reservations, but feel free to walk in!"],
    ('music', 'song', 'playlist', 'volume'):
        ["We play a curated playlist. Hope you enjoy the vibes!", "Glad you noticed the music! It's a mix we enjoy.", "If the music volume is uncomfortable, please let our staff know."],

    # --- General Info ---
    ('hour', 'open', 'close', 'time', 'timing'):
        ["Our cafe hours are 8 AM to 8 PM, seven days a week.", "We're open from 8 in the morning until 8 in the evening daily.", "Opening hours: 8:00 AM - 8:00 PM everyday."],
    ('location', 'address', 'where', 'find'):
        ["We're located at 15 Cafe Lane, Koregaon Park, Pune.", "You can find us at 15 Cafe Lane in KP.", "Our address is 15 Cafe Lane, Pune. Easy to find!"], # Example Pune Address
    ('help', 'assist', 'support', 'question'):
        ["Sure, I can try to help. What's your question about our cafe?", "How can I assist you regarding the cafe?", "I can answer questions about our menu, hours, WiFi, etc. What would you like to know?"]
}


def get_response(user_input:str):
    lemmas = preprocess_text(user_input)

    for keywords, responses in rules.items():
        if any(keyword in lemmas for keyword in keywords):
            return random.choice(responses)
    
    return random.choice([
        "I'm sorry, I didn't quite get that. Could you ask about our menu, coffee, hours, or WiFi perhaps?",
        "Hmm, I'm not sure about that. Try asking about our offerings or general cafe info.",
        "I can best answer questions about coffee, tea, food, hours, location, and WiFi. Could you rephrase?"
    ])


def run_chatbot():
    print("Chatbot: Welcome to our Cafe Assistant! Ask me about the menu, hours, WiFi, etc. (Type 'bye' to exit)")
    while True:
        try:
            user_input = input("You: ")
            if not user_input.strip():
                print("Chatbot: Please say something!")
                continue
            processed_exit_check = preprocess_text(user_input)

            if any(lemma in processed_exit_check for lemma in ('bye', 'goodbye', 'exit', 'quit')):
                response = "Goodbye! Hope to see you again soon." 
                for keywords, responses in rules.items():
                    if any(k in ('bye', 'goodbye', 'exit', 'quit') for k in keywords):
                        response = random.choice(responses)
                        break
                print(f"Chatbot: {response}")
                break

            response = get_response(user_input)
            print(f"Chatbot: {response}")

        except EOFError: # Handle Ctrl+D
            print("\nChatbot: Goodbye! Hope you enjoyed your virtual coffee.")
            break
        except KeyboardInterrupt: # Handle Ctrl+C
            print("\nChatbot: Okay, goodbye!")
            break

if __name__ == '__main__':
    run_chatbot()