package lv.vdmakul.mindt.service.mindmap.treeparser;

import lv.vdmakul.mindt.domain.TestPlan;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static lv.vdmakul.mindt.domain.ReferenceTestPlanHelper.aRequest;
import static lv.vdmakul.mindt.domain.ReferenceTestPlanHelper.aTest;
import static lv.vdmakul.mindt.domain.builder.TestPlanBuilder.aTestPlan;
import static lv.vdmakul.mindt.domain.builder.TestSuiteBuilder.aTestSuite;
import static org.junit.Assert.assertEquals;

public class MindMapTreeParserE2ETest {

    private MindMapTreeParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new MindMapTreeParser();
    }

    @Test
    public void shouldParseMindMap() throws Exception {
        InputStream inputStream = new ByteArrayInputStream(MINDMAP_CONTENT.getBytes(StandardCharsets.UTF_8));
        TestPlan actualTestPlan = parser.parseMindMap(inputStream);

        assertEquals(aTestPlan()
                        .withTestSuite(aTestSuite()
                                .withName("Multiply")
                                .withRequest(aRequest("POST", "/rest/multiply"))
                                .withTest(aTest("simple multiplication", "5", "9", "45"))
                                .withTest(aTest("multiplying negatives", "-2.3", "-6.76", "15.548")))
                        .withTestSuite(aTestSuite()
                                .withName("Divide")
                                .withRequest(aRequest("POST", "/rest/divide"))
                                .withTest(aTest("simple division", "5", "2", "2.5"))
                                .withTest(aTest("division by negative number", "22.36", "-5", "-4.47"))
                                .withTest(aTest("division by zero", "10", "0", "DIV/0")))
                        .withTestSuite(aTestSuite()
                                .withName("Add")
                                .withRequest(aRequest("POST", "/rest/add"))
                                .withTest(aTest("simple addition", "6", "8", "14"))
                                .withTest(aTest("adding a negative number", "-5.34", "3.95", "-1.39")))
                        .withTestSuite(aTestSuite()
                                .withName("Subtract")
                                .withRequest(aRequest("POST", "/rest/subtract"))
                                .withTest(aTest("simple subtraction", "97", "58", "39"))
                                .withTest(aTest("subtracting a negative number", "-34.12", "-55.7", "21.58")))
                        .build(),
                actualTestPlan);
    }

    // same content as in src/test/resources/calc_tests.mm
    private final static String MINDMAP_CONTENT = "<map version=\"1.0.1\">\n" +
            "<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->\n" +
            "<node CREATED=\"1383556086469\" ID=\"ID_1910973038\" MODIFIED=\"1385401924836\" TEXT=\"Calculator tests\">\n" +
            "<node CREATED=\"1383556132250\" ID=\"ID_1382933124\" MODIFIED=\"1383559189945\" POSITION=\"right\" TEXT=\"Multiply\">\n" +
            "<node CREATED=\"1383557074323\" ID=\"ID_718358945\" MODIFIED=\"1383557341919\" TEXT=\"Request\">\n" +
            "<node CREATED=\"1383557231134\" ID=\"ID_853378638\" MODIFIED=\"1383840685716\" TEXT=\"Method: POST\"/>\n" +
            "<node CREATED=\"1383557247559\" ID=\"ID_85326941\" MODIFIED=\"1383557262579\" TEXT=\"Path: /rest/multiply\"/>\n" +
            "</node>\n" +
            "<node CREATED=\"1383556253543\" ID=\"ID_229403878\" MODIFIED=\"1383912928682\" TEXT=\"Test: simple multiplication\">\n" +
            "<arrowlink DESTINATION=\"ID_229403878\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1282835981\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<arrowlink DESTINATION=\"ID_229403878\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_531141105\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<linktarget COLOR=\"#b0b0b0\" DESTINATION=\"ID_229403878\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1282835981\" SOURCE=\"ID_229403878\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<linktarget COLOR=\"#b0b0b0\" DESTINATION=\"ID_229403878\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_531141105\" SOURCE=\"ID_229403878\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<node CREATED=\"1383556561811\" ID=\"ID_567263367\" MODIFIED=\"1383918441078\" TEXT=\"variableOne: 5\"/>\n" +
            "<node CREATED=\"1383556604234\" ID=\"ID_234568798\" MODIFIED=\"1383918490601\" TEXT=\"variableTwo: 9\"/>\n" +
            "<node CREATED=\"1383556627506\" ID=\"ID_1349204512\" MODIFIED=\"1383918526346\" TEXT=\"result: 45\"/>\n" +
            "</node>\n" +
            "<node CREATED=\"1383556253543\" ID=\"ID_1325001281\" MODIFIED=\"1383912945372\" TEXT=\"Test: multiplying negatives\">\n" +
            "<arrowlink DESTINATION=\"ID_1325001281\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1040273979\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<arrowlink DESTINATION=\"ID_1325001281\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1155841062\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<linktarget COLOR=\"#b0b0b0\" DESTINATION=\"ID_1325001281\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1040273979\" SOURCE=\"ID_1325001281\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<linktarget COLOR=\"#b0b0b0\" DESTINATION=\"ID_1325001281\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1155841062\" SOURCE=\"ID_1325001281\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<node CREATED=\"1383556561811\" ID=\"ID_737874719\" MODIFIED=\"1383918441081\" TEXT=\"variableOne: -2.3\"/>\n" +
            "<node CREATED=\"1383556604234\" ID=\"ID_141399130\" MODIFIED=\"1383918490604\" TEXT=\"variableTwo: -6.76\"/>\n" +
            "<node CREATED=\"1383556627506\" ID=\"ID_910072858\" MODIFIED=\"1383918526346\" TEXT=\"result: 15.548\"/>\n" +
            "</node>\n" +
            "</node>\n" +
            "<node CREATED=\"1383556132250\" ID=\"ID_298396215\" MODIFIED=\"1383559207519\" POSITION=\"right\" TEXT=\"Divide\">\n" +
            "<node CREATED=\"1383557074323\" ID=\"ID_232050061\" MODIFIED=\"1383557341919\" TEXT=\"Request\">\n" +
            "<node CREATED=\"1383557231134\" ID=\"ID_1505052581\" MODIFIED=\"1383840688596\" TEXT=\"Method: POST\"/>\n" +
            "<node CREATED=\"1383557247559\" ID=\"ID_627584772\" MODIFIED=\"1383559212551\" TEXT=\"Path: /rest/divide\"/>\n" +
            "</node>\n" +
            "<node CREATED=\"1383556253543\" ID=\"ID_1435955592\" MODIFIED=\"1383913219962\" TEXT=\"Test: simple division\">\n" +
            "<arrowlink DESTINATION=\"ID_1435955592\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1599669204\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<arrowlink DESTINATION=\"ID_1435955592\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_866381071\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<linktarget COLOR=\"#b0b0b0\" DESTINATION=\"ID_1435955592\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1599669204\" SOURCE=\"ID_1435955592\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<linktarget COLOR=\"#b0b0b0\" DESTINATION=\"ID_1435955592\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_866381071\" SOURCE=\"ID_1435955592\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<node CREATED=\"1383556561811\" ID=\"ID_1199767139\" MODIFIED=\"1383918441086\" TEXT=\"variableOne: 5\"/>\n" +
            "<node CREATED=\"1383556604234\" ID=\"ID_943506077\" MODIFIED=\"1383918490607\" TEXT=\"variableTwo: 2\"/>\n" +
            "<node CREATED=\"1383556627506\" ID=\"ID_1432241508\" MODIFIED=\"1383918526349\" TEXT=\"result: 2.5\"/>\n" +
            "</node>\n" +
            "<node CREATED=\"1383556253543\" ID=\"ID_1367433067\" MODIFIED=\"1383913449143\" TEXT=\"Test: division by negative number\">\n" +
            "<arrowlink DESTINATION=\"ID_1367433067\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1371366770\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<arrowlink DESTINATION=\"ID_1367433067\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_35693089\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<linktarget COLOR=\"#b0b0b0\" DESTINATION=\"ID_1367433067\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1371366770\" SOURCE=\"ID_1367433067\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<linktarget COLOR=\"#b0b0b0\" DESTINATION=\"ID_1367433067\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_35693089\" SOURCE=\"ID_1367433067\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<node CREATED=\"1383556561811\" ID=\"ID_5220535\" MODIFIED=\"1383918441086\" TEXT=\"variableOne: 22.36\"/>\n" +
            "<node CREATED=\"1383556604234\" ID=\"ID_1895459947\" MODIFIED=\"1383918490608\" TEXT=\"variableTwo: -5\"/>\n" +
            "<node CREATED=\"1383556627506\" ID=\"ID_106232882\" MODIFIED=\"1383918526349\" TEXT=\"result: -4.47\"/>\n" +
            "</node>\n" +
            "<node CREATED=\"1383556253543\" ID=\"ID_883037101\" MODIFIED=\"1383913261777\" TEXT=\"Test: division by zero\">\n" +
            "<arrowlink DESTINATION=\"ID_883037101\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_56281175\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<arrowlink DESTINATION=\"ID_883037101\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_280011135\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<linktarget COLOR=\"#b0b0b0\" DESTINATION=\"ID_883037101\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_56281175\" SOURCE=\"ID_883037101\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<linktarget COLOR=\"#b0b0b0\" DESTINATION=\"ID_883037101\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_280011135\" SOURCE=\"ID_883037101\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<node CREATED=\"1383556561811\" ID=\"ID_1971191635\" MODIFIED=\"1383918441089\" TEXT=\"variableOne: 10\"/>\n" +
            "<node CREATED=\"1383556604234\" ID=\"ID_1520362735\" MODIFIED=\"1383918490608\" TEXT=\"variableTwo: 0\"/>\n" +
            "<node CREATED=\"1383556627506\" ID=\"ID_1539630039\" MODIFIED=\"1383918526349\" TEXT=\"result: DIV/0\">\n" +
            "<font NAME=\"SansSerif\" SIZE=\"12\"/>\n" +
            "</node>\n" +
            "</node>\n" +
            "</node>\n" +
            "<node CREATED=\"1383556132250\" ID=\"ID_1085987640\" MODIFIED=\"1385401548956\" POSITION=\"left\" TEXT=\"Add\">\n" +
            "<node CREATED=\"1383557074323\" ID=\"ID_256521276\" MODIFIED=\"1383557341919\" TEXT=\"Request\">\n" +
            "<node CREATED=\"1383557231134\" ID=\"ID_751972787\" MODIFIED=\"1383840678932\" TEXT=\"Method: POST\"/>\n" +
            "<node CREATED=\"1383557247559\" ID=\"ID_1192914768\" MODIFIED=\"1383558922707\" TEXT=\"Path: /rest/add\"/>\n" +
            "</node>\n" +
            "<node CREATED=\"1383556253543\" ID=\"ID_1217429996\" MODIFIED=\"1383913382537\" TEXT=\"Test: simple addition\">\n" +
            "<arrowlink DESTINATION=\"ID_1217429996\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1028536754\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<arrowlink DESTINATION=\"ID_1217429996\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1521236405\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<linktarget COLOR=\"#b0b0b0\" DESTINATION=\"ID_1217429996\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1028536754\" SOURCE=\"ID_1217429996\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<linktarget COLOR=\"#b0b0b0\" DESTINATION=\"ID_1217429996\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1521236405\" SOURCE=\"ID_1217429996\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<node CREATED=\"1383556561811\" ID=\"ID_370003045\" MODIFIED=\"1383918441082\" TEXT=\"variableOne: 6\"/>\n" +
            "<node CREATED=\"1383556604234\" ID=\"ID_903348920\" MODIFIED=\"1383918490605\" TEXT=\"variableTwo: 8\"/>\n" +
            "<node CREATED=\"1383556627506\" ID=\"ID_1890021932\" MODIFIED=\"1383918526346\" TEXT=\"result: 14\"/>\n" +
            "</node>\n" +
            "<node CREATED=\"1383556253543\" ID=\"ID_1711781633\" MODIFIED=\"1383913657654\" TEXT=\"Test: adding a negative number\">\n" +
            "<arrowlink DESTINATION=\"ID_1711781633\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_275813346\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<arrowlink DESTINATION=\"ID_1711781633\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1280041278\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<linktarget COLOR=\"#b0b0b0\" DESTINATION=\"ID_1711781633\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_275813346\" SOURCE=\"ID_1711781633\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<linktarget COLOR=\"#b0b0b0\" DESTINATION=\"ID_1711781633\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1280041278\" SOURCE=\"ID_1711781633\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<node CREATED=\"1383556561811\" ID=\"ID_1954824635\" MODIFIED=\"1383918441082\" TEXT=\"variableOne: -5.34\"/>\n" +
            "<node CREATED=\"1383556604234\" ID=\"ID_1080484905\" MODIFIED=\"1383918490605\" TEXT=\"variableTwo: 3.95\"/>\n" +
            "<node CREATED=\"1383556627506\" ID=\"ID_161301730\" MODIFIED=\"1383918526347\" TEXT=\"result: -1.39\"/>\n" +
            "</node>\n" +
            "</node>\n" +
            "<node CREATED=\"1383556132250\" ID=\"ID_1633382110\" MODIFIED=\"1383559036168\" POSITION=\"left\" TEXT=\"Subtract\">\n" +
            "<node CREATED=\"1383557074323\" ID=\"ID_1100659764\" MODIFIED=\"1383557341919\" TEXT=\"Request\">\n" +
            "<node CREATED=\"1383557231134\" ID=\"ID_946482544\" MODIFIED=\"1383840681824\" TEXT=\"Method: POST\"/>\n" +
            "<node CREATED=\"1383557247559\" ID=\"ID_1894875686\" MODIFIED=\"1383559047962\" TEXT=\"Path: /rest/subtract\"/>\n" +
            "</node>\n" +
            "<node CREATED=\"1383556253543\" ID=\"ID_114272730\" MODIFIED=\"1383913787147\" TEXT=\"Test: simple subtraction\">\n" +
            "<arrowlink DESTINATION=\"ID_114272730\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1902525830\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<arrowlink DESTINATION=\"ID_114272730\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1285151126\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<linktarget COLOR=\"#b0b0b0\" DESTINATION=\"ID_114272730\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1902525830\" SOURCE=\"ID_114272730\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<linktarget COLOR=\"#b0b0b0\" DESTINATION=\"ID_114272730\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1285151126\" SOURCE=\"ID_114272730\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<node CREATED=\"1383556561811\" ID=\"ID_1368430721\" MODIFIED=\"1383918441083\" TEXT=\"variableOne: 97\"/>\n" +
            "<node CREATED=\"1383556604234\" ID=\"ID_785803419\" MODIFIED=\"1383918490606\" TEXT=\"variableTwo: 58\"/>\n" +
            "<node CREATED=\"1383556627506\" ID=\"ID_584638763\" MODIFIED=\"1383918526348\" TEXT=\"result: 39\"/>\n" +
            "</node>\n" +
            "<node CREATED=\"1383556253543\" ID=\"ID_1936241265\" MODIFIED=\"1383913767689\" TEXT=\"Test: subtracting a negative number\">\n" +
            "<arrowlink DESTINATION=\"ID_1936241265\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_255880712\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<arrowlink DESTINATION=\"ID_1936241265\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1332817707\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<linktarget COLOR=\"#b0b0b0\" DESTINATION=\"ID_1936241265\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_255880712\" SOURCE=\"ID_1936241265\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<linktarget COLOR=\"#b0b0b0\" DESTINATION=\"ID_1936241265\" ENDARROW=\"Default\" ENDINCLINATION=\"0;0;\" ID=\"Arrow_ID_1332817707\" SOURCE=\"ID_1936241265\" STARTARROW=\"None\" STARTINCLINATION=\"0;0;\"/>\n" +
            "<node CREATED=\"1383556561811\" ID=\"ID_1695558587\" MODIFIED=\"1383918441084\" TEXT=\"variableOne: -34.12\"/>\n" +
            "<node CREATED=\"1383556604234\" ID=\"ID_1189480963\" MODIFIED=\"1383918490607\" TEXT=\"variableTwo: -55.7\"/>\n" +
            "<node CREATED=\"1383556627506\" ID=\"ID_1450551952\" MODIFIED=\"1383918526348\" TEXT=\"result: 21.58\"/>\n" +
            "</node>\n" +
            "</node>\n" +
            "</node>\n" +
            "</map>";
}
