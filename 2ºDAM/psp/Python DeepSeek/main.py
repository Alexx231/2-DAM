from chatbot import Chatbot

def main():
    # Inicializar el chatbot
    print("Iniciando chatbot...")
    bot = Chatbot()
    
    print("Chatbot listo! (Escribe 'salir' para terminar)")
    
    # Bucle principal de chat
    while True:
        # Obtener input del usuario
        user_input = input("\nTú: ")
        
        # Verificar si el usuario quiere salir
        if user_input.lower() in ['salir', 'exit', 'quit']:
            print("¡Hasta luego!")
            break
            
        try:
            # Obtener respuesta del chatbot
            response = bot.chat(user_input)
            print("\nAsistente:", response)
            
        except Exception as e:
            print(f"\nError: {e}")
            break

if __name__ == "__main__":
    main()