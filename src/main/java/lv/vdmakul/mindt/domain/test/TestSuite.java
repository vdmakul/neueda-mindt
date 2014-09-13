package lv.vdmakul.mindt.domain.test;

import java.util.Collections;
import java.util.List;

public class TestSuite {

    public final String name;
    public final Request request;
    public final List<Test> tests;

    public TestSuite(String name, Request request, List<Test> tests) {
        this.name = name;
        this.request = request;
        this.tests = Collections.unmodifiableList(tests);
    }
}
