
from Restuarant.interceptormanager import InterceptorManager
from Restuarant.interceptor import TableInterceptor, RequestTypeInterceptor
from Restuarant import apps

class SimpleMiddleware:
    def __init__(self, get_response):
        self.get_response = get_response
        # One-time configuration and initialization.

    def __call__(self, request):
        # Code to be executed for each request before
        # the view (and later middleware) are called.

        response = self.get_response(request)
        
        interceptorManager = InterceptorManager(SimpleMiddleware)
        tableInterceptor = TableInterceptor()
        requestTypeInterceptor = RequestTypeInterceptor()
        interceptorManager.add(tableInterceptor)
        interceptorManager.add(requestTypeInterceptor)
        interceptorManager.execute((str)(request))
        # Code to be executed for each request/response after
        # the view is called.

        return response
    
    def execute(request):
        pass