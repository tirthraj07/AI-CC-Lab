def send_message():
    user_input = user_entry.get()
    if not user_input.strip():
        return
    chat_window.insert(tk.END, f"You: {user_input}\n")
    response = get_response(user_input)
    chat_window.insert(tk.END, f"Bot: {response}\n\n")
    user_entry.delete(0, tk.END)
