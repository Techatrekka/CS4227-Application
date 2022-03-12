from abc import ABC, abstractmethod
from asyncio.windows_events import NULL

class Interceptor:
    
    def __init__(self):
        self.nextInterceptor = None
        self.level = 0
    
    @abstractmethod
    def execute(self, request):
        print("test")
        
    def setNextInterceptor(self, Interceptor):
        self.nextInterceptor = Interceptor
    
    def executeIntercept(self, level, message):
        if self.level < level:
            self.execute(message)
        if self.nextInterceptor != None:
            self.nextInterceptor.executeIntercept(level,message)
        
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