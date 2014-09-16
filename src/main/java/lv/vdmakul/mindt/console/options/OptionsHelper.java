package lv.vdmakul.mindt.console.options;

import org.apache.commons.cli.*;

import java.io.PrintStream;
import java.io.PrintWriter;

import static lv.vdmakul.mindt.console.options.MindtOption.*;

public class OptionsHelper {

    public static Options createDefaultOptions() {
        Options options = new Options();
        options.addOption(MINDMAP,  true,  "path to mindmap file to be used for test");
        options.addOption(SUITE,    true,  "path to exported suite file to be used for test");
        options.addOption(EXPORT,   true,  "export suite file");
        options.addOption(URL,      true,  "URL of Calculator to test");
        options.addOption(SKIPTEST, false, "skip Calculator test");
        options.addOption(HELP,     false, "show this help");
        return options;
    }

    public static CommandLine parseOptions(Options options, String[] args) {
        CommandLineParser parser = new BasicParser();
        CommandLine line;
        try {
            line = parser.parse(options, args);
        } catch (ParseException e) {
            throw new OptionsParsingException(e.getMessage());
        }

        if (line.hasOption(HELP) ) {
            return line;
        }

        if (line.hasOption(EXPORT) && !line.hasOption(MINDMAP)) {
            throw new OptionsParsingException("-mindmap option must be specified");
        }

        if (line.hasOption(EXPORT) && line.hasOption(SUITE)) {
            throw new OptionsParsingException("-export option is not compatible with -suite option");
        }

        if (line.hasOption(SKIPTEST)) {
            return line;
        }

        if (!line.hasOption(SUITE) && !line.hasOption(MINDMAP)) {
            throw new OptionsParsingException("either -mindmap or -suite option must be specified");
        }
        return line;
    }

    public static void printHelpMessage(Options options, PrintStream printStream) {
        HelpFormatter formatter = new HelpFormatter();
        PrintWriter printWriter = new PrintWriter(printStream);
        formatter.printHelp(printWriter,
                HelpFormatter.DEFAULT_WIDTH,
                "neueda-mindt",
                null,
                options,
                HelpFormatter.DEFAULT_LEFT_PAD,
                HelpFormatter.DEFAULT_DESC_PAD,
                null,
                false);
        printWriter.flush(); //thanks to commons-cli
    }
}



