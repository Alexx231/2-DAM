class DataRow:
    def __init__(self, **kwargs):
        for key, value in kwargs.items():
            setattr(self, key, value)
    
    def __str__(self):
        return ', '.join(f"{key}: {value}" for key, value in self.__dict__.items())
    
    def modify_attribute(self, attr, value):
        if hasattr(self, attr):
            setattr(self, attr, value)
    
    def __eq__(self, other):
        return getattr(self, list(self.__dict__.keys())[1]) == getattr(other, list(other.__dict__.keys())[1])
    
    def __lt__(self, other):
        return getattr(self, list(self.__dict__.keys())[1]) < getattr(other, list(other.__dict__.keys())[1])
    
    def __gt__(self, other):
        return getattr(self, list(self.__dict__.keys())[1]) > getattr(other, list(other.__dict__.keys())[1])
    
    def __add__(self, other):
        result = {}
        for key in self.__dict__.keys():
            result[key] = getattr(self, key) + getattr(other, key)
        return DataRow(**result)
    
    def __sub__(self, other):
        result = {}
        for key in self.__dict__.keys():
            result[key] = getattr(self, key) - getattr(other, key)
        return DataRow(**result)