import json

# Adds new Data into JSON files
def addJson(new_data, jsonName):
    with open(jsonName, 'r+') as file:
        file_data = json.load(file)
        file_data.append(new_data)
        file.seek(0)
        json.dump(file_data, file, indent=4)
        file.close()

# Clears JSON file
def clearJson(jsonName):
    with open(jsonName, 'r+') as file:
        file.truncate()
        json.dump([], file)
        file.close()

def start():
    clearJson('accountInfo.json')
    fullName = input("Enter fullName: \n")
    email = input("Enter Email: \n")
    password = input("Enter Password: \n")
    dict = {
        "fullName": fullName,
        "email": email,
        "password": password
    }   
    addJson(dict, 'accountInfo.json')


start()