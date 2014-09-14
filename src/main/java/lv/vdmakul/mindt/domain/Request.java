package lv.vdmakul.mindt.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Request {

    public final String method;
    public final String path;

    public Request(String method, String path) {
        this.method = method;
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request that = (Request) o;
        return new EqualsBuilder().append(this.method, that.method).append(this.path, that.path).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(method).append(path).toHashCode();
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
