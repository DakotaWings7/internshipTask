package org.example;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "MainCommand",
        description = "This is the main command of the program, where you can enter various commands provided by the program.",
        commandListHeading = "%nSubCommands are:%n",
        mixinStandardHelpOptions = true,
        subcommands = {
                HelpCommand.class,
                MemCommand.class
        }
)
class MainCommand implements Callable<Integer> {

    public static void main(String[] args) {
        new CommandLine(new MainCommand()).execute();
    }

    @Override
    public Integer call() throws Exception {
        return null;
    }
}