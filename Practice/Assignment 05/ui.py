import tkinter as tk
from tkinter import scrolledtext
from chatbot import process_query

def say_hello():
    # Get the text entered by the user
    user_input = user_entry.get()  
    chat_window.insert(tk.END, "User >> " + user_input + '\n\n')
    response = process_query(user_input)
    chat_window.insert(tk.END, "Brew Buddy >> " + response + '\n\n')
    user_entry.delete(0, tk.END)


# Create a window
root = tk.Tk()

# Give a title
root.title("BrewBuddy Chatbot")

# Set a size
root.geometry("900x500")

# Create a input box
user_entry = tk.Entry(root, width=100, font=("Arial", 12))
user_entry.place(relx=0.0, rely=0.95, anchor='sw')
user_entry.bind('<Return>', lambda event: say_hello())
# Add a send button
btn = tk.Button(root, text="Send", command=say_hello, width=20, height=3)
btn.place(relx=1.0, rely=1.0, anchor='se')

# Create a chatwindow
chat_window = scrolledtext.ScrolledText(root, width=100)
chat_window.place(relx=0.0, rely=0.0, anchor='nw')
chat_window.insert(tk.END, "Bot: Welcome to our Cafe Assistant! Ask me anything. (Type 'bye' to exit)\n\n")


# Show the window
root.mainloop()
