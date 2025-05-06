from chatbot import process_query
import tkinter as tk
from tkinter import scrolledtext

def send_message():
    query = user_input.get()
    chat_window.insert(tk.END, "User >> " + query + '\n\n')
    response = process_query(query)
    chat_window.insert(tk.END, "BrewBuddy >> " + response + '\n\n')
    user_input.delete(0, tk.END)


root = tk.Tk()
root.title("Brew Buddy")
root.geometry("500x500")

btn = tk.Button(root, text="Send Button", width=20, height=3, command=send_message)
btn.place(relx=1.0, rely=1.0, anchor='se')

user_input = tk.Entry(root, width=50)
user_input.place(relx=0.0, rely=0.95, anchor='sw')

chat_window = scrolledtext.ScrolledText(root, width=100)
chat_window.place(relx=0.0, rely=0.0, anchor='nw')
chat_window.insert(tk.END, 'Brew Buddy >> Hello, I\'m BrewBuddy. How can I help you today?\n\n')

user_input.bind('<Return>', lambda event: send_message())

root.mainloop()