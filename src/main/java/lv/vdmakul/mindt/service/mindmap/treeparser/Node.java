package lv.vdmakul.mindt.service.mindmap.treeparser;

import java.util.ArrayList;
import java.util.List;

/*package*/ class Node {

    /*package*/ final Node parent;
    /*package*/ final String value;
    /*package*/ final List<Node> children = new ArrayList<>();

    /*package*/ Node(Node parent, String value) {
        this.parent = parent;
        this.value = value;
    }

    /*package*/ String attribute() {
        if (value.contains(":")) {
            return value.substring(0, value.indexOf(':'));
        } else {
            return value;
        }
    }

    /*package*/ boolean contains(String prefix) {
        return value.startsWith(prefix);
    }

    /*package*/ String get(String prefix) {
        return value.substring(prefix.length() + 1).trim();
    }
}
