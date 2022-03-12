
from Restuarant.interceptormanager import InterceptorManager
from Restuarant.interceptor import TableInterceptor, RequestTypeInterceptor

class SimpleMiddleware:
    def __init__(self, get_response):
        self.get_response = get_response
        # One-time configuration and initialization.

    def __call__(self, request):
        # Code to be executed for each request before
        # the view (and later middleware) are called.

        response = self.get_response(request)
        if len((str)(request)) > 10:
            level = 1
        else:
            level = 2
        interceptorManager = InterceptorManager(SimpleMiddleware)
        tableInterceptor = TableInterceptor()
        requestTypeInterceptor = RequestTypeInterceptor()
        #context object is the api for the application
        #create an instance of the context object
        #invoke setter to pass a reference to the context object
        interceptorManager.add(tableInterceptor)
        interceptorManager.add(requestTypeInterceptor)
        interceptorManager.execute((int)(level),(str)(request))

        return response
    
    def execute(request):
        pass