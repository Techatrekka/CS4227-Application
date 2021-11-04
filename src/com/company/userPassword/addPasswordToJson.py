import json

# Adds new Data into JSON files
def add_json(new_data, jsonName):
    with open(jsonName, 'r+') as file:
        file_data = json.load(file)
        file_data.append(new_data)
        file.seek(0)
        json.dump(file_data, file, indent=4)
        file.close()

# Clears JSON file
def clear_json(jsonName):
    with open(jsonName, 'r+') as file:
        file.truncate()
        json.dump([], file)
        file.close()

def start():
    clear_json('account_info.json')
    username = input("Enter Username: \n")
    email = input("Enter Email: \n")
    password = input("Enter Password: \n")
    dict = {
        "username": username,
        "email": email,
        "password": password
    }   
    add_json(dict, 'account_info.json')


start()