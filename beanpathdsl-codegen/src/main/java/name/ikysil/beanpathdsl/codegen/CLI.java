/*
 * Copyright 2016 Illya Kysil <ikysil@ikysil.name>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package name.ikysil.beanpathdsl.codegen;

import java.nio.charset.Charset;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author Illya Kysil <ikysil@ikysil.name>
 */
public class CLI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final Options options = buildOptions();
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmdLine = parser.parse(options, args);
            if (cmdLine.hasOption("h")) {
                usage(options);
                System.exit(1);
            }
            Configuration configuration = new Configuration();
            if (cmdLine.hasOption("s")) {
                configuration.setOutputDirectory(cmdLine.getOptionValue("s"));
            }
            if (cmdLine.hasOption("c")) {
                configuration.setOutputCharset(Charset.forName(cmdLine.getOptionValue("c")));
            }
            if (cmdLine.hasOption("cnp")) {
                configuration.setClassNamePrefix(cmdLine.getOptionValue("cnp"));
            }
            if (cmdLine.hasOption("cns")) {
                configuration.setClassNameSuffix(cmdLine.getOptionValue("cns"));
            }
            if (cmdLine.hasOption("pns")) {
                configuration.setPackageNameSuffix(cmdLine.getOptionValue("pns"));
            }
            new CodeGen(configuration).process();
        }
        catch (ParseException ex) {
            System.err.println(String.format("Can not parse arguments: %s", ex.getMessage()));
            usage(options);
        }
    }

    private static Options buildOptions() {
        Option help = Option.builder("h")
                .longOpt("help")
                .desc("Show this help")
                .build();
        Option outputDirectory = Option.builder("s")
                .longOpt("output-directory")
                .hasArg()
                .argName("directory")
                .desc("Specify where to place generated source files")
                .build();
        Option outputCharset = Option.builder("c")
                .longOpt("output-charset")
                .hasArg()
                .argName("charset")
                .desc("Specify the charset to generate source files")
                .build();
        Option classNamePrefix = Option.builder("cnp")
                .longOpt("class-name-prefix")
                .hasArg()
                .desc("Specify the prefix for generated classes names")
                .build();
        Option classNameSuffix = Option.builder("cns")
                .longOpt("class-name-suffix")
                .hasArg()
                .desc("Specify the suffix for generated classes names")
                .build();
        Option packageNameSuffix = Option.builder("pns")
                .longOpt("package-name-suffix")
                .hasArg()
                .desc("Specify the suffix for generated package names")
                .build();
        Options result = new Options();
        result.addOption(help);
        result.addOption(outputDirectory);
        result.addOption(outputCharset);
        result.addOption(classNamePrefix);
        result.addOption(classNameSuffix);
        result.addOption(packageNameSuffix);
        return result;
    }

    private static void usage(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java " + CLI.class.getName(), options);
    }

}
