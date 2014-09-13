package lv.vdmakul.mindt.mindmap;


import lv.vdmakul.mindt.domain.test.TestPlan;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public interface MindMapParser {

    TestPlan parseMindMap(InputStream mindMapInputStream) throws ParserConfigurationException, SAXException, IOException; //todo remove these exceptions
}
