package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.util.ArrayList;
import java.util.List;

public class ComplexCommand implements DriverCommand {
    private final List<DriverCommand> commands;

    public ComplexCommand() {
        this.commands = new ArrayList<>();
    }

    public ComplexCommand add(DriverCommand command) {
        commands.add(command);
        return this;
    }

    @Override
    public void execute(Job2dDriver driver) {
        for(DriverCommand command : commands) {
            command.execute(driver);
        }
    }
}