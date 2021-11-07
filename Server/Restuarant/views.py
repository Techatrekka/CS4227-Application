from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt
from rest_framework.parsers import JSONParser
from django.http.response import JsonResponse

from Restuarant.models import Users
from Restuarant.serializers import UsersSerializer

@csrf_exempt
def usersApi(request,id=0):
    if request.method=='GET':
        users = Users.objects.all()
        users_serializer = UsersSerializer(users,many=True)
        return JsonResponse(users_serializer.data, safe=False)
    elif request.method=='POST':
        users_data=JSONParser().parse(request)
        users_serializer =UsersSerializer(data=users_data)
        if (users_serializer.is_valid()):
            users_serializer.save()
            return JsonResponse("Added Succesfully",safe=False)
        return JsonResponse("Unsuccessful",safe=False)
    elif request.method=='PUT':
        users_data=JSONParser().parse(request)
        users_serializer=UsersSerializer.objects.get(users_id=users_data['users_id'])
        users_serializer=UsersSerializer(Users,data=users_data)
        if users_serializer.is_valid():
            users_serializer.save()
            return JsonResponse("Success",safe=False)
        return JsonResponse("failed",safe=True)
    elif request.method=='DELETE':
        users=Users.objects.get(users_id=id)
        users.delete()
        return JsonResponse("Delete succesful",safe=False)
