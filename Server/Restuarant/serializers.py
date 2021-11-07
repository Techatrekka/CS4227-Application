from django.db.models import fields
from rest_framework import serializers
from Restuarant.models import *

class UsersSerializer(serializers.ModelSerializer):
    class Meta:
        model = Users
        fields = ('user_id','username','password','user_type')
'''
class OrdersSerializer(serializers.ModelSerializer):
    class Meta:
        model = Orders
        fields = ('order_id','user_id','total_cost')

class LoyaltySerializer(serializers.ModelSerializer):
    class Meta:
        model = Loyalty
        fields = ('loyalty_id','user_id','loyalty_points')

class OrderLineItemsSerializer(serializers.ModelSerializer):
    class Meta:
        model = Orders
        fields = ('orderlineitems_id','order_id','menu_item','count')

class MenuItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = MenuItem
        fields = ('menu_item','dish_bev_id','menu_id','food')

class MenuSerializer(serializers.ModelSerializer):
    class Meta:
        model = Menu
        fields = ('menu_id','name','description','date_created','set_menu_price','discount','two_for_one')

class DishesSerializer(serializers.ModelSerializer):
    class Meta:
        model = Dishes
        fields = ('dish_id','name','description','price','allergens')

class BeveragesSerializer(serializers.ModelSerializer):
    class Meta:
        model = Beverages
        fields = ('beverage_id','name','price','alcoholic')

class DrinkItemsSerializers(serializers.ModelSerializer):
    class Meta:
        model = DrinkItems
        fields = ('drink_id','beverage_id','description','count','expiry_date')

class FoodItemsSerializers(serializers.ModelSerializer):
    class Meta:
        model = FoodItems
        fields = ('food_id','name','description','count','expiry_date','allergens')

class DishesFoodItemsSerializer(serializers.ModelSerializer):
    class Meta:
        model = Dishes_FoodItems
        fields = ('food_id','dish_id')'''