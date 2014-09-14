package lv.vdmakul.mindt.mindmap;


import lv.vdmakul.mindt.domain.test.TestPlan;

import java.io.InputStream;

public interface MindMapParser {

    TestPlan parseMindMap(InputStream mindMapInputStream);
}
