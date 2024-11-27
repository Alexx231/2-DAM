# crear o leer, mediante el módulo 'json'
import json

# Utilizamos un dicc, de estruct similar al json (tipo k:v)
dicciColor = {"1":"azul", "2":"rojo", "3":"verde", "4":"naranja"}

estructJson = json.dumps(dicciColor, sort_keys=True, indent=4) # sort-keys=True ordena los pares por la clave
# print(estructJson ["2"]) --> error!
# OJOJO .dumps devuelve una estructura json
# OJOJO si intentas acceder con clave, como un diccionario, casca!!!
print(estructJson) # imprime la estructura json con el parámetro indent par por línea; sin él, en lista
#{
#    "1": "azul",
#    "2": "rojo",
#    "3": "verde",
#    "4": "naranja"
#}
#{"1": "azul", "2": "rojo", "3": "verde", "4": "naranja"}


# OJOJO .loads devuelve un diccionario, de forma que ya podrás acceder con la clave

newEstructJson = json.loads(estructJson)
print(newEstructJson ["2"])