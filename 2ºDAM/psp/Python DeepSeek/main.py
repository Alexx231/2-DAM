import os
import shutil

from chatbot import Chatbot

def limpiar_cache():
    # Limpiar cache de transformers
    cache_dir = os.path.expanduser("~/.cache/huggingface/hub")
    if os.path.exists(cache_dir):
        shutil.rmtree(cache_dir)
        print("Cache de modelos limpiado correctamente")

def main():
    print("Iniciando DeepSeek Chat local... (puede tomar unos minutos la primera vez)")
    chatbot = Chatbot()
    print("¡Listo! Escribe 'salir' para terminar.")
    
    while True:
        user_input = input("Tú: ")
        if user_input.lower() == "salir":
            opcion = input("¿Deseas limpiar el cache de modelos? (s/n): ")
            if opcion.lower() == 's':
                limpiar_cache()
            break
        response = chatbot.chat(user_input)
        print(f"Bot: {response}")

if __name__ == "__main__":
    main()