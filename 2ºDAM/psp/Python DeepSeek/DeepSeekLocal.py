"""
Módulo que implementa la clase DeepSeekLocal para interactuar con el modelo deepseek-coder
de forma local usando transformers.
"""
from transformers import AutoTokenizer, AutoModelForCausalLM
import torch

class DeepSeekLocal:
    def __init__(self):
        self.model_name = "deepseek-ai/deepseek-coder-6.7b-base"
        self.tokenizer = AutoTokenizer.from_pretrained(self.model_name)
        self.model = AutoModelForCausalLM.from_pretrained(
            self.model_name,
            torch_dtype=torch.float16,
            device_map="auto"
        )

    def get_response(self, prompt, max_length=1024):
        inputs = self.tokenizer(prompt, return_tensors="pt").to(self.model.device)
        
        outputs = self.model.generate(
            **inputs,
            max_length=max_length,
            num_return_sequences=1,
            temperature=0.7,
            do_sample=True
        )
        
        response = self.tokenizer.decode(outputs[0], skip_special_tokens=True)
        return response.replace(prompt, "").strip()