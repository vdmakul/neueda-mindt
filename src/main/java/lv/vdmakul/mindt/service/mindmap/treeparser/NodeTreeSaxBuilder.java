package lv.vdmakul.mindt.service.mindmap.treeparser;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/*package*/ class NodeTreeSaxBuilder extends DefaultHandler {
    private static final String NODE_TAG = "node";
    private static final String TEXT_ATTRIBUTE = "TEXT";

    private Node root;
    private Node current;

    /*package*/ Node getRoot() {
        return root;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (NODE_TAG.equals(qName)) {
            visit(attributes.getValue(TEXT_ATTRIBUTE));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if ("node".equals(qName)) {
            leave();
        }
    }

    private void visit(String value) {
        if (root == null) {
            root = new Node(null, value);
            current = root;
        } else {
            Node newCurrent = new Node(current, value);
            current.children.add(newCurrent);
            current = newCurrent;
        }
    }

    private void leave() {
        current = current.parent;
    }

}
