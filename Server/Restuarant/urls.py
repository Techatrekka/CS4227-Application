from django.conf.urls import url
from Restuarant import views

urlpatterns=[
    url(r'^user$',views.usersApi),
    url(r'^user/([0-9]+)$',views.usersApi),

    url(r'^order$',views.ordersApi),
    url(r'^order/([0-9]+)$',views.ordersApi),

    url(r'^loyalty$',views.loyaltyApi),
    url(r'^loyalty/([0-9]+)$',views.loyaltyApi),

    url(r'^menu$',views.menuApi),
    url(r'^menu/([0-9]+)$',views.menuApi),

    url(r'^fooditems$',views.fooditemsApi),
    url(r'^fooditems/([0-9]+)$',views.fooditemsApi),
    
    url(r'^drinkitems$',views.drinkitemsApi),
    url(r'^drinkitems/([0-9]+)$',views.drinkitemsApi),

    url(r'^employeesalary$',views.employeesalaryApi),
    url(r'^employeesalary/([0-9]+)$',views.employeesalaryApi),

    url(r'^beverages$',views.beveragesApi),
    url(r'^beverages/([0-9]+)$',views.beveragesApi),
    
    url(r'^dishes$',views.dishesApi),
    url(r'^dishes/([0-9]+)$',views.dishesApi),

    url(r'^orderlineitem$',views.orderlineitemsApi),
    url(r'^orderlineitem/([0-9]+)$',views.orderlineitemsApi),

    url(r'^menuitem$',views.menuitemApi),
    url(r'^menuitem/([0-9]+)$',views.menuitemApi),
]