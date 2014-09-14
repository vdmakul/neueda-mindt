package lv.vdmakul.mindt.console;

public class MindtActionParameters {

    public final String exportFileName;
    public final String mindMapFileName;
    public final String suiteFileName;
    public final String url;

    public MindtActionParameters(String exportFileName, String mindMapFileName, String suiteFileName, String url) {
        this.exportFileName = exportFileName;
        this.mindMapFileName = mindMapFileName;
        this.suiteFileName = suiteFileName;
        this.url = url;
    }
}
