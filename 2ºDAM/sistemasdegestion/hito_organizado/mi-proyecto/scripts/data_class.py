# mi-proyecto/scripts/data_class.py
class DataRow:
    def __init__(self, **kwargs):
        for key, value in kwargs.items():
            setattr(self, key, value)

    def __str__(self):
        return ', '.join(f'{key}: {value}' for key, value in self.__dict__.items())

    def modify_attribute(self, attr, value):
        if hasattr(self, attr):
            setattr(self, attr, value)

    def __eq__(self, other):
        return self.__dict__.get('second_column') == other.__dict__.get('second_column')

    def __add__(self, other):
        result = {}
        for key in self.__dict__.keys():
            result[key] = getattr(self, key, 0) + getattr(other, key, 0)
        return DataRow(**result)

    def __sub__(self, other):
        result = {}
        for key in self.__dict__.keys():
            result[key] = getattr(self, key, 0) - getattr(other, key, 0)
        return DataRow(**result)