from transformers import AutoTokenizer, AutoModelForCausalLM, BitsAndBytesConfig
import torch

class DeepSeekLocal:
    def __init__(self):
        # Usar modelo más pequeño
        self.model_name = "deepseek-ai/deepseek-coder-1.3b-base"
        
        try:
            # Cargar tokenizer
            self.tokenizer = AutoTokenizer.from_pretrained(self.model_name)
            
            # Configuración para CPU
            self.model = AutoModelForCausalLM.from_pretrained(
                self.model_name,
                torch_dtype=torch.float32,
                device_map="cpu",
                low_cpu_mem_usage=True
            )
        except Exception as e:
            print(f"Error al cargar el modelo: {e}")
            raise

    def get_response(self, prompt, max_length=1500):
        # Configurar para usar menos memoria
        inputs = self.tokenizer(
            prompt, 
            return_tensors="pt",
            truncation=True,
            max_length=512
        ).to(self.model.device)
        
        outputs = self.model.generate(
            **inputs,
            max_length=max_length,
            num_return_sequences=1,
            temperature=0.7,
            do_sample=True,
            pad_token_id=self.tokenizer.pad_token_id,
            num_beams=2,
            early_stopping=True,
            use_cache=True
        )
        
        response = self.tokenizer.decode(outputs[0], skip_special_tokens=True)
        return response