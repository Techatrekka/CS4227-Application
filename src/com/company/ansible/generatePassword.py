import random
import string
import json


# Generate Randome password of length 15
def passwordGenerator():
    passwordLen = 15
    lowerCase = string.ascii_lowercase
    upperCase = string.ascii_uppercase
    numbers = string.digits
    symbols = string.punctuation
    all = lowerCase + upperCase + numbers + symbols

    temp = random.sample(all, passwordLen)
    password = "".join(temp)
    print(password)

passwordGenerator()