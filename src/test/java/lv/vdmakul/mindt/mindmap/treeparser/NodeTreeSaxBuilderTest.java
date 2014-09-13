package lv.vdmakul.mindt.mindmap.treeparser;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NodeTreeSaxBuilderTest {

    private NodeTreeSaxBuilder treeSaxBuilder;
    private SAXParser parser;

    @Before
    public void setUp() throws Exception {
        parser = SAXParserFactory.newInstance().newSAXParser();
        treeSaxBuilder = new NodeTreeSaxBuilder();
    }

    private Node parse(String xml) throws IOException, SAXException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        parser.parse(inputStream, treeSaxBuilder);
        return treeSaxBuilder.getRoot();
    }

    @Test
    public void testName() throws Exception {
        Node root = parse("<map version=\"1.0.1\">\n" +
                "    <node TEXT=\"Calculator tests\">\n" +
                "        <node TEXT=\"Multiply\">\n" +
                "            <node TEXT=\"Request\">\n" +
                "                <node TEXT=\"Method: POST\"/>\n" +
                "                <node TEXT=\"Path: /rest/multiply\"/>\n" +
                "            </node>\n" +
                "            <node TEXT=\"Test: simple multiplication\">\n" +
                "                <node TEXT=\"variableOne: 5\"/>\n" +
                "                <node TEXT=\"variableTwo: 9\"/>\n" +
                "                <node TEXT=\"result: 45\"/>\n" +
                "            </node>\n" +
                "        </node>\n" +
                "    </node>\n" +
                "</map>");

        assertNotNull(root);
        assertEquals("Calculator tests", root.value);
        assertEquals(1, root.children.size());

        Node child = root.children.get(0);
        assertEquals("Multiply", child.value);

        assertEquals(2, child.children.size());

        Node child1 = child.children.get(0);
        assertEquals("Request", child1.value);
        assertEquals(2, child1.children.size());
        assertEquals("Method: POST", child1.children.get(0).value);
        assertEquals("Path: /rest/multiply", child1.children.get(1).value);

        Node child2 = child.children.get(1);
        assertEquals("Test: simple multiplication", child2.value);
        assertEquals(3, child2.children.size());
        assertEquals("variableOne: 5", child2.children.get(0).value);
        assertEquals("variableTwo: 9", child2.children.get(1).value);
        assertEquals("result: 45", child2.children.get(2).value);
    }
}