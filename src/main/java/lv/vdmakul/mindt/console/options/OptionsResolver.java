package lv.vdmakul.mindt.console.options;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class OptionsResolver {

    private final Options options;
    private final CommandLine line;

    public OptionsResolver(String[] args) {
        this.options = OptionsHelper.createDefaultOptions();
        this.line = OptionsHelper.parseOptions(options, args);
    }

    public boolean isSet(String option) {
        return line.hasOption(option);
    }

    public String get(String option) {
        return line.getOptionValue(option);
    }

    public Options getOptions() {
        return options;
    }
}
