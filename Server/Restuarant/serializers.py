from rest_framework import serializers
from Restuarant.models import *

class UsersSerializer(serializers.ModelSerializer):
    class Meta:
        model = Users
        fields = ('user_id','fullname','email','password','user_type')

class EmployeeSalarySerializer(serializers.ModelSerializer):
    class Meta:
        model = EmployeeSalary
        fields = ('user_id','employee_type','salary')

class OrdersSerializer(serializers.ModelSerializer):
    class Meta:
        model = Orders
        fields = ('order_id','user_id','total_cost')

class LoyaltySerializer(serializers.ModelSerializer):
    class Meta:
        model = Loyalty
        fields = ('loyalty_id','user_id','loyalty_points')

class MenuSerializer(serializers.ModelSerializer):
    class Meta:
        model = Menu
        fields = ('menu_id','name','description','date_created','set_menu_price','discount')

class OrderLineItemsSerializer(serializers.ModelSerializer):
    class Meta:
        model = OrderLineItems
        fields = ('orderlineitems_id','order_id','menu_item','food')

class MenuItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = MenuItem
        fields = ('menu_item','dish_bev_id','menu_id','food')
        
class StockItemsSerializer(serializers.ModelSerializer):
    class Meta:
        model = StockItems
        fields = ('stock_item_id','name','count','expiry_date','allergens','isFood')