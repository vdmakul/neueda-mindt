package lv.vdmakul.mindt.mindmap;


import lv.vdmakul.mindt.domain.TestPlan;

import java.io.InputStream;

public interface MindMapParser {

    TestPlan parseMindMap(InputStream mindMapInputStream);
}
