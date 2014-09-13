package lv.vdmakul.mindt.domain.test;

public class Request {

    public final String method;
    public final String path;

    public Request(String method, String path) {
        this.method = method;
        this.path = path;
    }

}
