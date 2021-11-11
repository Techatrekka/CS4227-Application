from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt
from rest_framework.parsers import JSONParser
from django.http.response import JsonResponse

from Restuarant.models import *
from Restuarant.serializers import *

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
        users_serializer=UsersSerializer.objects.get(users_id=users_data['user_id'])
        users_serializer=UsersSerializer(Users,data=users_data)
        if users_serializer.is_valid():
            users_serializer.save()
            return JsonResponse("Success",safe=False)
        return JsonResponse("failed",safe=True)
    elif request.method=='DELETE':
        users=Users.objects.get(users_id=id)
        users.delete()
        return JsonResponse("Delete succesful",safe=False)

@csrf_exempt
def employeesalaryApi(request,id=0):
    if request.method=='GET':
        employeesalary = EmployeeSalary.objects.all()
        employeesalarySerializer = EmployeeSalarySerializer(employeesalary,many=True)
        return JsonResponse(employeesalarySerializer.data, safe=False)
    elif request.method=='POST':
        employeesalary_data=JSONParser().parse(request)
        employeesalarySerializer =EmployeeSalarySerializer(data=employeesalary_data)
        if (employeesalarySerializer.is_valid()):
            employeesalarySerializer.save()
            return JsonResponse("Added Succesfully",safe=False)
        return JsonResponse("Unsuccessful",safe=False)
    elif request.method=='PUT':
        employeesalary_data=JSONParser().parse(request)
        employeesalarySerializer=EmployeeSalarySerializer.objects.get(user_id=employeesalary_data['user_id'])
        employeesalarySerializer=EmployeeSalarySerializer(EmployeeSalary,data=employeesalary_data)
        if employeesalarySerializer.is_valid():
            employeesalarySerializer.save()
            return JsonResponse("Success",safe=False)
        return JsonResponse("failed",safe=True)
    elif request.method=='DELETE':
        employeesalary=EmployeeSalary.objects.get(users_id=id)
        employeesalary.delete()
        return JsonResponse("Delete succesful",safe=False) 

@csrf_exempt
def loyaltyApi(request,id=0):
    if request.method=='GET':
        loyalty = Loyalty.objects.all()
        loyaltySerializer = LoyaltySerializer(loyalty,many=True)
        return JsonResponse(loyaltySerializer.data, safe=False)
    elif request.method=='POST':
        loyalty_data=JSONParser().parse(request)
        loyaltySerializer =LoyaltySerializer(data=loyalty_data)
        if (loyaltySerializer.is_valid()):
            loyaltySerializer.save()
            return JsonResponse("Added Succesfully",safe=False)
        return JsonResponse("Unsuccessful",safe=False)
    elif request.method=='PUT':
        loyalty_data=JSONParser().parse(request)
        loyaltySerializer=LoyaltySerializer.objects.get(loyalty_id=loyalty_data['loyalty_id'])
        loyaltySerializer=LoyaltySerializer(Loyalty,data=loyalty_data)
        if loyaltySerializer.is_valid():
            loyaltySerializer.save()
            return JsonResponse("Success",safe=False)
        return JsonResponse("failed",safe=True)
    elif request.method=='DELETE':
        loyalty=Loyalty.objects.get(loyalty_id=id)
        loyalty.delete()
        return JsonResponse("Delete succesful",safe=False)

@csrf_exempt
def ordersApi(request,id=0):
    if request.method=='GET':
        orders = Orders.objects.all()
        ordersSerializer = OrdersSerializer(orders,many=True)
        return JsonResponse(ordersSerializer.data, safe=False)
    elif request.method=='POST':
        orders=JSONParser().parse(request)
        ordersSerializer =OrdersSerializer(data=orders)
        if (ordersSerializer.is_valid()):
            ordersSerializer.save()
            return JsonResponse("Added Succesfully",safe=False)
        return JsonResponse("Unsuccessful",safe=False)
    elif request.method=='PUT':
        orders=JSONParser().parse(request)
        ordersSerializer=OrdersSerializer.objects.get(order_id=orders['order_id'])
        ordersSerializer=OrdersSerializer(Orders,data=orders)
        if ordersSerializer.is_valid():
            ordersSerializer.save()
            return JsonResponse("Success",safe=False)
        return JsonResponse("failed",safe=True)
    elif request.method=='DELETE':
        users=Users.objects.get(users_id=id)
        users.delete()
        return JsonResponse("Delete succesful",safe=False)

@csrf_exempt
def menuApi(request,id=0):
    if request.method=='GET':
        menu = Menu.objects.all()
        menuSerializer = MenuSerializer(menu,many=True)
        return JsonResponse(menuSerializer.data, safe=False)
    elif request.method=='POST':
        menu_data=JSONParser().parse(request)
        menuSerializer =MenuSerializer(data=menu_data)
        if (menuSerializer.is_valid()):
            menuSerializer.save()
            return JsonResponse("Added Succesfully",safe=False)
        return JsonResponse("Unsuccessful",safe=False)
    elif request.method=='PUT':
        menu_data=JSONParser().parse(request)
        menuSerializer=MenuSerializer.objects.get(menu_id=menu_data['menu_id'])
        menuSerializer=MenuSerializer(Menu,data=menu_data)
        if menuSerializer.is_valid():
            menuSerializer.save()
            return JsonResponse("Success",safe=False)
        return JsonResponse("failed",safe=True)
    elif request.method=='DELETE':
        menu=Menu.objects.get(menu_id=id)
        menu.delete()
        return JsonResponse("Delete succesful",safe=False)

@csrf_exempt
def dishesApi(request,id=0):
    if request.method=='GET':
        dishes = Dishes.objects.all()
        dishesSerializer = DishesSerializer(dishes,many=True)
        return JsonResponse(dishesSerializer.data, safe=False)
    elif request.method=='POST':
        dishes_data=JSONParser().parse(request)
        dishesSerializer =DishesSerializer(data=dishes_data)
        if (dishesSerializer.is_valid()):
            dishesSerializer.save()
            return JsonResponse("Added Succesfully",safe=False)
        return JsonResponse("Unsuccessful",safe=False)
    elif request.method=='PUT':
        dishes_data=JSONParser().parse(request)
        dishesSerializer=DishesSerializer.objects.get(dishes_id=dishes_data['dishes_id'])
        dishesSerializer=DishesSerializer(Dishes,data=dishes_data)
        if dishesSerializer.is_valid():
            dishesSerializer.save()
            return JsonResponse("Success",safe=False)
        return JsonResponse("failed",safe=True)
    elif request.method=='DELETE':
        dishes=Dishes.objects.get(dishes_id=id)
        dishes.delete()
        return JsonResponse("Delete succesful",safe=False)

@csrf_exempt
def beveragesApi(request,id=0):
    if request.method=='GET':
        beverages = Beverages.objects.all()
        beveragesSerializer = BeveragesSerializer(beverages,many=True)
        return JsonResponse(beveragesSerializer.data, safe=False)
    elif request.method=='POST':
        beverages_data=JSONParser().parse(request)
        beveragesSerializer =BeveragesSerializer(data=beverages_data)
        if (beveragesSerializer.is_valid()):
            beveragesSerializer.save()
            return JsonResponse("Added Succesfully",safe=False)
        print(beveragesSerializer.error_messages)
        print(beveragesSerializer.field_name)
        print(beverages_data)
        print(beveragesSerializer)
        return JsonResponse("Unsuccessful",safe=False)
    elif request.method=='PUT':
        beverages_data=JSONParser().parse(request)
        beveragesSerializer=BeveragesSerializer.objects.get(beverages_id=beverages_data['beverage_id'])
        beveragesSerializer=BeveragesSerializer(Beverages,data=beverages_data)
        if beveragesSerializer.is_valid():
            beveragesSerializer.save()
            return JsonResponse("Success",safe=False)
        return JsonResponse("failed",safe=True)
    elif request.method=='DELETE':
        beverages=Beverages.objects.get(beverage_id=id)
        beverages.delete()
        return JsonResponse("Delete succesful",safe=False)

@csrf_exempt
def fooditemsApi(request,id=0):
    if request.method=='GET':
        fooditems = Users.objects.all()
        foodItemsSerializers = FoodItemsSerializers(fooditems,many=True)
        return JsonResponse(foodItemsSerializers.data, safe=False)
    elif request.method=='POST':
        fooditems_data=JSONParser().parse(request)
        foodItemsSerializers=FoodItemsSerializers(data=fooditems_data)
        if (foodItemsSerializers.is_valid()):
            foodItemsSerializers.save()
            return JsonResponse("Added Succesfully",safe=False)
        return JsonResponse("Unsuccessful",safe=False)
    elif request.method=='PUT':
        fooditems_data=JSONParser().parse(request)
        foodItemsSerializers=FoodItemsSerializers.objects.get(fooditems_id=fooditems_data['food_id'])
        foodItemsSerializers=FoodItemsSerializers(FoodItems,data=fooditems_data)
        if foodItemsSerializers.is_valid():
            foodItemsSerializers.save()
            return JsonResponse("Success",safe=False)
        return JsonResponse("failed",safe=True)
    elif request.method=='DELETE':
        fooditems=FoodItems.objects.get(fooditems_id=id)
        fooditems.delete()
        return JsonResponse("Delete succesful",safe=False)

@csrf_exempt
def drinkitemsApi(request,id=0):
    if request.method=='GET':
        drinkitems = DrinkItems.objects.all()
        drinkitems_serializers = DrinkItemsSerializers(drinkitems,many=True)
        return JsonResponse(drinkitems_serializers.data, safe=False)
    elif request.method=='POST':
        drinkitems_data=JSONParser().parse(request)
        drinkitems_serializers =DrinkItemsSerializers(data=drinkitems_data)
        if (drinkitems_serializers.is_valid()):
            drinkitems_serializers.save()
            return JsonResponse("Added Succesfully",safe=False)
        return JsonResponse("Unsuccessful",safe=False)
    elif request.method=='PUT':
        drinkitems_data=JSONParser().parse(request)
        drinkitems_serializers=DrinkItemsSerializers.objects.get(drinkitems_id=drinkitems_data['drinkitems_id'])
        drinkitems_serializers=DrinkItemsSerializers(DrinkItems,data=drinkitems_data)
        if drinkitems_serializers.is_valid():
            drinkitems_serializers.save()
            return JsonResponse("Success",safe=False)
        return JsonResponse("failed",safe=True)
    elif request.method=='DELETE':
        drinkitems_data=DrinkItems.objects.get(drinkitems_id=id)
        drinkitems_data.delete()
        return JsonResponse("Delete succesful",safe=False)

@csrf_exempt
def menuitemApi(request,id=0):
    if request.method=='GET':
        menuitem = MenuItem.objects.all()
        menuitemSerializer = MenuSerializer(menuitem,many=True)
        return JsonResponse(menuitemSerializer.data, safe=False)
    elif request.method=='POST':
        menuitem_data=JSONParser().parse(request)
        menuitemSerializer =MenuItemSerializer(data=menuitem_data)
        if (menuitemSerializer.is_valid()):
            menuitemSerializer.save()
            return JsonResponse("Added Succesfully",safe=False)
        return JsonResponse("Unsuccessful",safe=False)
    elif request.method=='PUT':
        menuitem_data=JSONParser().parse(request)
        menuitemSerializer=MenuItemSerializer.objects.get(menuitem_id=menuitem_data['menuitem_id'])
        menuitemSerializer=MenuItemSerializer(Menu,data=menuitem_data)
        if menuitemSerializer.is_valid():
            menuitemSerializer.save()
            return JsonResponse("Success",safe=False)
        return JsonResponse("failed",safe=True)
    elif request.method=='DELETE':
        menuitem=Menu.objects.get(menuitem_id=id)
        menuitem.delete()
        return JsonResponse("Delete succesful",safe=False)

@csrf_exempt
def orderlineitemsApi(request,id=0):
    if request.method=='GET':
        orderlineitems = OrderLineItems.objects.all()
        orderLineItemsSerializer = OrderLineItemsSerializer(orderlineitems,many=True)
        return JsonResponse(orderLineItemsSerializer.data, safe=False)
    elif request.method=='POST':
        orderlineitems=JSONParser().parse(request)
        orderLineItemsSerializer =OrderLineItemsSerializer(data=orderlineitems)
        if (orderLineItemsSerializer.is_valid()):
            orderLineItemsSerializer.save()
            return JsonResponse("Added Succesfully",safe=False)
        return JsonResponse("Unsuccessful",safe=False)
    elif request.method=='PUT':
        orderlineitems_data=JSONParser().parse(request)
        orderLineItemsSerializer=OrderLineItemsSerializer.objects.get(orderlineitems_id=orderlineitems_data['orderlineitems_id'])
        orderLineItemsSerializer=OrderLineItemsSerializer(OrderLineItems,data=orderlineitems_data)
        if orderLineItemsSerializer.is_valid():
            orderLineItemsSerializer.save()
            return JsonResponse("Success",safe=False)
        return JsonResponse("failed",safe=True)
    elif request.method=='DELETE':
        orderlineitems=Menu.objects.get(orderlineitems_id=id)
        orderlineitems.delete()
        return JsonResponse("Delete succesful",safe=False)