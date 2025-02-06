from DeepSeekLocal import DeepSeekLocal

class Chatbot:
    def __init__(self):
        self.client = DeepSeekLocal()
        self.conversation_history = []

    def chat(self, user_input):
        context = "\n".join([f"{msg['role']}: {msg['content']}" 
                        for msg in self.conversation_history])
        
        full_prompt = f"{context}\nuser: {user_input}\nassistant:"
        response = self.client.get_response(full_prompt)
        
        self.conversation_history.append({"role": "user", "content": user_input})
        self.conversation_history.append({"role": "assistant", "content": response})
        
        return response