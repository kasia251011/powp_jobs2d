package edu.kis.powp.jobs2d.command;

public class PrimitivesCommandFactory {
    public static DriverCommand createRectangle(int x, int y, int width, int height) {
        return new ComplexCommand()
                .add(new SetPositionCommand(x, y))
                .add(new OperateToCommand(x, y + height))
                .add(new OperateToCommand(x + height, y + height))
                .add(new OperateToCommand(x + height, y))
                .add(new OperateToCommand(x, y));
    }

    public static DriverCommand createCircle(int x, int y, int radius, int vertices) {
        if(vertices < 3)
            return new ComplexCommand();

        ComplexCommand commands = new ComplexCommand();
        commands.add(new SetPositionCommand(x + radius, y));

        final double angleStep = (Math.PI * 2) / vertices;
        double angle = angleStep;

        for(int i = 0; i < vertices; ++i) {
            int vx = x + (int)(Math.cos(angle) * radius);
            int vy = y + (int)(Math.sin(angle) * radius);
            commands.add(new OperateToCommand(vx, vy));

            angle += angleStep;
        }

        return commands;
    }
}