from django.db import models
from django.db.models.aggregates import Count
from django.db.models.base import Model
from django.db.models.deletion import SET_DEFAULT
from django.db.models.fields import AutoField, DecimalField
from django.db.models.fields.related import ForeignKey

class Users(models.Model):
    user_id = models.AutoField(primary_key=True)
    fullname = models.CharField(max_length=50)
    email = models.CharField(max_length=100)
    password = models.CharField(max_length=50)
    user_type = models.CharField(max_length=500)

class EmployeeSalary(models.Model):
    user_id = models.OneToOneField(Users, on_delete=models.CASCADE, primary_key=True)
    employee_type = models.CharField(max_length=50)
    salary = models.DecimalField(max_digits=5,decimal_places=2)

class Loyalty(models.Model):
    loyalty_id = models.AutoField(primary_key=True)
    user_id = models.ForeignKey(Users, default=1,on_delete=models.SET_DEFAULT)
    loyalty_points = models.IntegerField()

class Orders(models.Model):
    order_id = models.AutoField(primary_key=True)
    user_id = models.ForeignKey(Users, default=1,on_delete=models.SET_DEFAULT)
    total_cost = models.DecimalField(max_digits=10,decimal_places=10)

class Dishes(models.Model):
    dish_id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=50)
    description = models.CharField(max_length=500)
    price = models.DecimalField(max_digits=10,decimal_places=10)
    allergens = models.CharField(max_length=500)

class Menu(models.Model):
    menu_id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=50)
    description = models.CharField(max_length=500)
    date_created = models.DateField()
    set_menu_price = models.DecimalField(max_digits=10,decimal_places=10)
    discount = models.DecimalField(max_digits=10,decimal_places=10)
    two_for_one = models.BooleanField() 

class Beverages(models.Model):
    beverage_id = models.AutoField(primary_key=True)
    name =  models.CharField(max_length=50)
    price = models.DecimalField(max_digits=4,decimal_places=2)
    alcoholic = models.BooleanField()

class FoodItems(models.Model):
    food_id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=50)
    count = models.IntegerField()
    expiry_date = models.DateField()
    allergens = models.CharField(max_length=500)

class DrinkItems(models.Model):
    drink_id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=50)
    count = models.IntegerField()
    expiry_date = models.DateField()

class MenuItem(models.Model):
    menu_item = models.AutoField(primary_key=True)
    Dish_Bev_id = models.IntegerField()
    menu_id = models.ForeignKey(Menu,default=1,on_delete=models.SET_DEFAULT)
    food = models.BooleanField()

class OrderLineItems(models.Model):
    OrderLineItems = models.AutoField(primary_key=True)
    order_id = models.ForeignKey(Orders,default=1,on_delete=models.SET_DEFAULT)
    menu_item = models.ForeignKey(MenuItem,default=1,on_delete=models.SET_DEFAULT)
    food = models.BooleanField()

class Dishes_FoodItems(models.Model):
    food_id = models.ForeignKey(FoodItems,default=1,on_delete=models.SET_DEFAULT)
    dish_id = models.ForeignKey(Dishes,default=1,on_delete=models.SET_DEFAULT)