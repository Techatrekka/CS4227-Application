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
    
class OrderLineItems(models.Model):
    line_item = models.AutoField(primary_key=True)
    order_id = models.ForeignKey(Orders, default=1, on_delete=models.CASCADE)
    menu_item = models.IntegerField()

class MenuItem(models.Model):
    menu_item = models.AutoField(primary_key=True)
    name = models.CharField(max_length=500)
    Description = models.CharField(max_length=500)
    Price = models.DecimalField(max_digits=5,decimal_places=2)
    Alcoholic = models.BooleanField()
    Ingredients=models.CharField(max_length=500)
    isFood = models.BooleanField()
    
class Menu(models.Model):
    menu_id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=50)
    description = models.CharField(max_length=500)
    date_created = models.DateField()
    menu_items = models.CharField(max_length=500)
    set_menu_price = models.CharField(max_length=50)
    discount = models.CharField(max_length=50)

class StockItems(models.Model):
    stock_item_id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=500)
    count=models.IntegerField()
    expiry_date=models.DateField()
    allergens=models.CharField(max_length=500)
    isFood=models.BooleanField()