package edu.kis.powp.jobs2d.drivers.adapter;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.powp.jobs2d.Job2dDriver;

public class LineDrawerAdapter extends DrawPanelController implements Job2dDriver {
    private int startX = 0, startY = 0;
    private DrawPanelController drawPanelController;
    private ILine line;

    public LineDrawerAdapter(DrawPanelController drawPanelController, ILine iLine) {
        super();
        this.line = iLine;
        this.drawPanelController = drawPanelController;
    }

    @Override
    public void setPosition(int x, int y) {
        this.startX = x;
        this.startY = y;
    }

    @Override
    public void operateTo(int x, int y) {
        this.line.setStartCoordinates(this.startX, this.startY);
        this.line.setEndCoordinates(x, y);
        setPosition(x, y);
        this.drawPanelController.drawLine(this.line);
    }

    @Override
    public String toString() {
        return "LineDrawerAdapter";
    }
}
