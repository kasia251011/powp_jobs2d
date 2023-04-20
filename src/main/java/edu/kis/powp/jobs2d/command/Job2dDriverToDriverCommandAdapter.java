package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

public class Job2dDriverToDriverCommandAdapter implements Job2dDriver  {
    private final ComplexCommand command = new ComplexCommand();

    @Override
    public void setPosition(int x, int y) {
        command.add(new SetPositionCommand(x, y));
    }

    @Override
    public void operateTo(int x, int y) {
        command.add(new OperateToCommand(x, y));
    }

    public DriverCommand getCommand() {
        return command;
    }
}