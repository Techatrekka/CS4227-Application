from django.db.models import fields
from rest_framework import serializers
from Restuarant.models import *

class UsersSerializer(serializers.ModelSerializer):
    class Meta:
        model = Users
        fields = ('user_id','username','password','user_type')

class OrdersSerializer(serializers.ModelSerializer):
    class Meta:
        model = Orders
        fields = ('order_id','user_id','staff_id','user_type')

class UsersSerializer(serializers.ModelSerializer):
    class Meta:
        model = Users
        fields = ('user_id','username','password','user_type')

class UsersSerializer(serializers.ModelSerializer):
    class Meta:
        model = Users
        fields = ('user_id','username','password','user_type')

class UsersSerializer(serializers.ModelSerializer):
    class Meta:
        model = Users
        fields = ('user_id','username','password','user_type')

