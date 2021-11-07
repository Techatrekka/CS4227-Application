from django.conf.urls import url
from Restuarant import views

urlpatterns=[
    url(r'^user$',views.usersApi),
    url(r'^user/([0-9]+)$',views.usersApi)
]# this is a test