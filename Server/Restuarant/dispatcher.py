class InterceptorManager:
    
    def __init__(self, application):
        self.application = application
        self.interceptors = set()
    
    def add(self,interceptor):
        self.interceptors.add(interceptor)
    
    def remove(self, interceptor):
        self.interceptors.remove(interceptor)
    
    def execute(self, level, request):
        for interceptor in self.interceptors:
            interceptor.executeIntercept(level, request)
        self.application.execute(request)