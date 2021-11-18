from django.db import models
from django.db.models.aggregates import Count

class Users(models.Model):
    user_id = models.AutoField(primary_key=True)
    fullname = models.CharField(max_length=50)
    email = models.CharField(max_length=100)
    password = models.CharField(max_length=50)
    user_type = models.CharField(max_length=500)

class EmployeeSalary(models.Model):
    user_id = models.OneToOneField(Users, primary_key=True, on_delete=models.CASCADE)
    employee_type = models.CharField(max_length=50)
    salary = models.CharField(max_length=50)

class Loyalty(models.Model):
    loyalty_id = models.AutoField(primary_key=True)
    user_id = models.ForeignKey(Users, default=1,on_delete=models.CASCADE)
    loyalty_points = models.IntegerField()

class Orders(models.Model):
    order_id = models.AutoField(primary_key=True)
    user_id = models.ForeignKey(Users, default=1,on_delete=models.CASCADE)
    total_cost = models.CharField(max_length=50)

class Dishes(models.Model):
    dish_id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=50)
    description = models.CharField(max_length=500)
    price = models.CharField(max_length=50)
    allergens = models.CharField(max_length=500)

class Menu(models.Model):
    menu_id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=50)
    description = models.CharField(max_length=500)
    date_created = models.DateField()
    set_menu_price = models.CharField(max_length=50)
    discount = models.CharField(max_length=50)

class Beverages(models.Model):
    beverage_id = models.AutoField(primary_key=True)
    name =  models.CharField(max_length=50)
    price = models.CharField(max_length=50)
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
    dish_bev_id = models.IntegerField()
    menu_id = models.ForeignKey(Menu,default=1,on_delete=models.CASCADE)
    food = models.BooleanField()

class OrderLineItems(models.Model):
    OrderLineItems = models.AutoField(primary_key=True)
    order_id = models.ForeignKey(Orders,default=1,on_delete=models.CASCADE)
    menu_item = models.ForeignKey(MenuItem,default=1,on_delete=models.CASCADE)
    food = models.BooleanField()

class Dishes_FoodItems(models.Model):
    food_id = models.ForeignKey(FoodItems,default=1,on_delete=models.CASCADE)
    dish_id = models.ForeignKey(Dishes,default=1,on_delete=models.CASCADE)