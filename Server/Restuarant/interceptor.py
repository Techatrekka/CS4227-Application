from abc import ABC, abstractmethod

class Interceptor:
    
    @abstractmethod
    def execute(self, request):
        print("test")
        
class TableInterceptor(Interceptor):
    
    def execute(self,request):
        IPlog = open("IP.txt", "w+")
        IPlog.write(request)
        IPlog.close() 
        
class RequestTypeInterceptor(Interceptor):
    
    def execute(self, request):
        RTlog = open("RT.txt", "w+")
        RTlog.write(request)
        RTlog.close()