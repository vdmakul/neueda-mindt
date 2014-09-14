package lv.vdmakul.mindt.domain.builder;


import lv.vdmakul.mindt.domain.Request;

public class RequestBuilder {
    public String method;
    public String path;

    private RequestBuilder() {
    }

    public static RequestBuilder aRequest() {
        return new RequestBuilder();
    }

    public RequestBuilder withMethod(String method) {
        this.method = method;
        return this;
    }

    public RequestBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public Request build() {
        Request request = new Request(method, path);
        return request;
    }
}
