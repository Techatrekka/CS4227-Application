from django.db import models
from django.db.models.aggregates import Count
from django.db.models.base import Model
from django.db.models.fields import AutoField
from django.db.models.fields.related import ForeignKey

class Users(models.Model):
    user_id = models.AutoField(primary_key=True)
    username = models.CharField(max_length=50)
    password = models.CharField(max_length=50)
    user_type = models.CharField(max_length=500)

class Loyalty(models.Model):
    loyalty_id = models.AutoField(primary_key=True)
    user_id = models.ForeignKey(Users, default=1,on_delete=models.SET_DEFAULT)
    loyalty_points = models.IntegerField()

class Dishes(models.Model):
    dish_id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=50)
    description = models.CharField(max_length=500)
    price = models.DecimalField(max_digits=10,decimal_places=10)
    #ingredients = models.Fiel
    allergens = models.CharField(max_length=500)

class Menu(models.Model):
    menu_id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=50)
    description = models.CharField(max_length=500)
    #menu_items =
    date_created = models.DateField()
    set_menu_price = models.DecimalField(max_digits=10,decimal_places=10)
    discount = models.DecimalField(max_digits=10,decimal_places=10)
    two_for_one = models.BooleanField() 

class Beverages(models.Model):
    beverage_id = AutoField(primary_key=True)
    name =  models.CharField(max_length=50)
    description = models.CharField(max_length=500)
    price = models.DecimalField(max_digits=10,decimal_places=10)
    alcoholic = models.BooleanField()

class FoodItems(models.Model):
    food_id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=50)
    description = models.CharField(max_length=500)
    count = models.IntegerField()
    expiry_date = models.DateField()
    allergens = models.CharField(max_length=500)

class DrinkItems(models.Model):
    drink_id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=50)
    description = models.CharField(max_length=500)
    count = models.IntegerField()
    expiry_date = models.DateField()
    alcohol_content = models.BooleanField()

class Orders(models.Model):
    order_id = models.AutoField(primary_key=True)
    user_id = models.ForeignKey(Users, default=1,on_delete=models.SET_DEFAULT)
    #menu_items = models.Field
    total_cost = models.DecimalField(max_digits=10,decimal_places=10)

