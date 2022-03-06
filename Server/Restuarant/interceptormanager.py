class InterceptorManager:
    
    def __init__(self, application):
        self.application = application
        self.interceptors = set()
    
    def add(self,interceptor):
        self.interceptors.add(interceptor)
    
    def remove(self, interceptor):
        self.interceptors.remove(interceptor)
    
    def execute(self, request):
        for interceptor in self.interceptors:
            interceptor.execute(request)
        self.application.execute(request)