package lv.vdmakul.mindt.mindmap.treeparser;

import java.util.ArrayList;
import java.util.List;

public class NodeBuilder {
    private Node parent;
    private String value;
    private List<NodeBuilder> childrenBuilders = new ArrayList<>();

    private NodeBuilder() {
    }

    public static NodeBuilder aNode() {
        return new NodeBuilder();
    }

    public static NodeBuilder aNode(String value) {
        return new NodeBuilder().withValue(value);
    }

    private NodeBuilder withParent(Node parent) {
        this.parent = parent;
        return this;
    }

    public NodeBuilder withChild(NodeBuilder childBuilder) {
        childrenBuilders.add(childBuilder);
        return this;
    }

    public NodeBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public Node build() {
        Node node = new Node(parent, value);
        childrenBuilders.stream().forEach(childBuilder ->
                node.children.add(childBuilder.withParent(node).build()));
        return node;
    }
}
