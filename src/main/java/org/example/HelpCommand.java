package org.example;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "org.example.HelpCommand",
        description = "This command allows you to obtain information about all the commands of this console program.",
        aliases = "help",
        mixinStandardHelpOptions = true
)
class HelpCommand implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        new CommandLine(new MainCommand()).execute("-h");
        System.out.println("\n");
        new CommandLine(new HelpCommand()).execute("-h");
        System.out.println("\n");
        new CommandLine(new MemCommand()).execute("-h");
        return null;
    }
}