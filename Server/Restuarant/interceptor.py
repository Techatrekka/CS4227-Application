from abc import ABC, abstractmethod

class Interceptor(ABC):
    
    def __init__(self,level = 0):
        self.nextInterceptor = None
        self.level = level
    
    @abstractmethod
    def execute(self, request):
        print("test")
        
    def setNextInterceptor(self, Interceptor):
        self.nextInterceptor = Interceptor
    
    def executeIntercept(self, level, request):
        if self.level == level:
            self.execute(request)
        if self.nextInterceptor != None:
            self.nextInterceptor.executeIntercept(level,request)
        
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
        