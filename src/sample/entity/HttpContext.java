package sample.entity;

public class HttpContext {
    private HttpRequest Request;
    private HttpResponse Response;

    public HttpRequest getRequest() {
        return Request;
    }

    public void setRequest(HttpRequest request) {
        Request = request;
    }

    public HttpResponse getResponse() {
        return Response;
    }

    public void setResponse(HttpResponse response) {
        Response = response;
    }

    public HttpContext(String requestText)
    {
        Request = new HttpRequest(requestText);
        Response = new HttpResponse();
    }
}
