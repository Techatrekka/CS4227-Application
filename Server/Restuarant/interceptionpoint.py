from Restuarant.dispatcher import Dispatcher
from Restuarant.interceptor import TableInterceptor, RequestTypeInterceptor

class SimpleMiddleware:
    def __init__(self, get_response):
        self.get_response = get_response
        # One-time configuration and initialization.

    def __call__(self, request):
        # Code to be executed for each request before
        # the view (and later middleware) are called.

        response = self.get_response(request)
        StrRequest = (str)(request)
        request1 = StrRequest.split()[1]
        request2 = StrRequest.split()[2]
        interceptorManager = Dispatcher(SimpleMiddleware)
        tableInterceptor = TableInterceptor(1)
        requestTypeInterceptor = RequestTypeInterceptor(2)
        #context object is the api for the application
        #create an instance of the context object
        #invoke setter to pass a reference to the context object
        interceptorManager.add(tableInterceptor)
        interceptorManager.add(requestTypeInterceptor)
        #due to poor forethought this implementation needs to be refactored to correct this error
        interceptorManager.execute(2,request1)
        interceptorManager.execute(1,request2)

        return response
    
    def execute(request):
        pass