package edu.kis.powp.jobs2d;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.legacy.drawer.panel.DefaultDrawerFrame;
import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.PrimitivesCommandFactory;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.adapter.DrawerAdapter;
import edu.kis.powp.jobs2d.drivers.adapter.LineDrawerAdapter;
import edu.kis.powp.jobs2d.events.SelectChangeVisibleOptionListener;
import edu.kis.powp.jobs2d.events.SelectTestFigureOptionListener;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;

public class TestJobs2dPatterns {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Setup test concerning preset figures in context.
	 * 
	 * @param application Application context.
	 */
	private static void setupPresetTests(Application application) {
		final DriverManager driverManager = DriverFeature.getDriverManager();
		SelectTestFigureOptionListener selectTestFigureOptionListener = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager());

		application.addTest("Figure Joe 1", selectTestFigureOptionListener);
		application.addTest("Figure Joe 2", selectTestFigureOptionListener);

		application.addTest("Primitives: Circle ", new ActionListener() {
			private int radius = 5;
			private int segments = 3;

			@Override
			public void actionPerformed(ActionEvent e) {
				DriverCommand cmd = PrimitivesCommandFactory.createCircle(0, 0, radius, segments);
				radius += 10;
				segments += 1;

				cmd.execute(driverManager.getCurrentDriver());
			}
		});

		application.addTest("Primitives: Rectangle ", new ActionListener() {
			private int pos = 5;

			@Override
			public void actionPerformed(ActionEvent e) {
				DriverCommand cmd = PrimitivesCommandFactory.createRectangle(pos, pos, 50, 30);
				pos += 10;
				cmd.execute(driverManager.getCurrentDriver());
			}
		});
	}

	/**
	 * Setup driver manager, and set default driver for application.
	 * 
	 * @param application Application context.
	 */
	private static void setupDrivers(Application application) {
		DrawPanelController drawPanelController = DrawerFeature.getDrawerController();

		Job2dDriver loggerDriver = new LoggerDriver();
		Job2dDriver testDriver = new DrawerAdapter(drawPanelController);
		Job2dDriver lineAdapterDotted = new LineDrawerAdapter(drawPanelController, LineFactory.getDottedLine());
		Job2dDriver lineAdapterBasic = new LineDrawerAdapter(drawPanelController, LineFactory.getBasicLine());
		Job2dDriver lineAdapterSpecial = new LineDrawerAdapter(drawPanelController, LineFactory.getSpecialLine());

		DriverFeature.addDriver("Logger Driver", loggerDriver);
		DriverFeature.addDriver("Buggy Simulator", testDriver);
		DriverFeature.addDriver("Drawer simulator - basic line", lineAdapterBasic);
		DriverFeature.addDriver("Drawer simulator - dotted line", lineAdapterDotted);
		DriverFeature.addDriver("Drawer simulator - special line", lineAdapterSpecial);

		DriverFeature.getDriverManager().setCurrentDriver(loggerDriver);
		DriverFeature.updateDriverInfo();
	}

	/**
	 * Auxiliary routines to enable using Buggy Simulator.
	 * 
	 * @param application Application context.
	 */
	private static void setupDefaultDrawerVisibilityManagement(Application application) {
		DefaultDrawerFrame defaultDrawerWindow = DefaultDrawerFrame.getDefaultDrawerFrame();
		application.addComponentMenuElementWithCheckBox(DrawPanelController.class, "Default Drawer Visibility",
				new SelectChangeVisibleOptionListener(defaultDrawerWindow), true);
		defaultDrawerWindow.setVisible(true);
	}

	/**
	 * Setup menu for adjusting logging settings.
	 * 
	 * @param application Application context.
	 */
	private static void setupLogger(Application application) {
		application.addComponentMenu(Logger.class, "Logger", 0);
		application.addComponentMenuElement(Logger.class, "Clear log",
				(ActionEvent e) -> application.flushLoggerOutput());
		application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
		application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
		application.addComponentMenuElement(Logger.class, "Warning level",
				(ActionEvent e) -> logger.setLevel(Level.WARNING));
		application.addComponentMenuElement(Logger.class, "Severe level",
				(ActionEvent e) -> logger.setLevel(Level.SEVERE));
		application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Application app = new Application("2d jobs Visio");
				DrawerFeature.setupDrawerPlugin(app);

				DriverFeature.setupDriverPlugin(app);
				setupDrivers(app);
				setupPresetTests(app);
				setupLogger(app);

				app.setVisibility(true);
			}
		});
	}

}
