from abc import ABC, abstractmethod

class Interceptor:
    
    @abstractmethod
    def execute(self, request):
        print("test")
        
class TableInterceptor(Interceptor):
    
    def execute(self,request):
        TableLog = open("TableLog.txt", "a")
        TableLog.write(request)
        TableLog.close() 
        
class RequestTypeInterceptor(Interceptor):
    
    def execute(self, request):
        RTlog = open("RTLog.txt", "a")
        RTlog.write(request)
        RTlog.close()