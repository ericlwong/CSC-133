/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.codename1.ui.layouts.BoxLayout;

public class Game extends Form implements Runnable {
	
	private GameWorld gw;
	private ScoreView sv;
	private MapView mv;
	private Button accelerateButton, leftButton, changeStratButton, brakeButton, rightButton, pausePlayButton, positionButton;
	private AccelerateCommand accelerateCommand;
	private LeftCommand steerLeftCommand;
	private SwitchStrategiesCommand changeStratCommand;
	private BrakeCommand brakeCommand;
	private RightCommand steerRightCommand;
	private HelpCommand helpCommand;
	private SoundCommand soundCommand;
	private AboutCommand aboutCommand;
	private ExitCommand exitCommand;
	private PausePlayCommand pausePlayCommand;
	private PositionCommand positionCommand;
	private CheckBox soundStatus;
	private UITimer timer;
	
	public Game() {
		gw = new GameWorld();
		
		this.setLayout(new BorderLayout());		// set Form LayoutManager as BorderLayout	
		sv = new ScoreView();					// create ScoreView object
		mv = new MapView(gw);						// create MapView object
		gw.addObserver(sv);			// Add the ScoreView as an observer of the GameWorld
		gw.addObserver(mv);			// Add the MapView as an observer of the GameWorld
		
		
		//UI TIMER
		timer = new UITimer(this);
		timer.schedule(25, true, this);				// Timer ticks every 20ms
		
		
		// NORTH SIDE of BorderLayout
		this.addComponent(BorderLayout.NORTH, sv);		// add ScoreView to the very top
		
		// WEST SIDE of BorderLayout
		Container westContainer = new Container();
		westContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		westContainer.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		westContainer.getAllStyles().setPadding(Component.TOP, 100);
		
		// Accelerate Button
		accelerateButton = new Button("Accelerate");
		accelerateButton.getUnselectedStyle().setBgTransparency(255);
		accelerateButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		accelerateButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		accelerateButton.getSelectedStyle().setBgTransparency(100);
		accelerateButton.getAllStyles().setPadding(Component.TOP, 5);
		accelerateButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		accelerateButton.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		accelerateCommand = new AccelerateCommand(gw);
		accelerateButton.setCommand(accelerateCommand);		// Allow the accelerate command to be invoked by the accelerate button
		addKeyListener('a', accelerateCommand);			// Allow the accelerate command to be invoked by the key 'a'
		
		// Steer Left Button
		leftButton = new Button("Left");
		leftButton.getUnselectedStyle().setBgTransparency(255);
		leftButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		leftButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		leftButton.getSelectedStyle().setBgTransparency(100);
		leftButton.getAllStyles().setPadding(Component.TOP, 5);
		leftButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		leftButton.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		steerLeftCommand = new LeftCommand(gw);
		leftButton.setCommand(steerLeftCommand);		// Allow the steer left command to be invoked by the steer left button
		addKeyListener('l', steerLeftCommand);			// Allow the steer left command to be invoked by the key 'l'
		
		// Change Strategies Button
		changeStratButton = new Button("Change Strategies");
		changeStratButton.getUnselectedStyle().setBgTransparency(255);
		changeStratButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		changeStratButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		changeStratButton.getSelectedStyle().setBgTransparency(100);
		changeStratButton.getAllStyles().setPadding(Component.TOP, 5);
		changeStratButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		changeStratButton.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		changeStratCommand = new SwitchStrategiesCommand(gw);
		changeStratButton.setCommand(changeStratCommand);	// Allow the change strategies command to be invoked by the change strategies button
		
		// Add buttons to the west container
		westContainer.addComponent(accelerateButton);
		westContainer.addComponent(leftButton);
		westContainer.addComponent(changeStratButton);
		this.addComponent(BorderLayout.WEST, westContainer);
		// END OF WEST SIDE
		
		
		// EAST SIDE of BorderLayout
		Container eastContainer = new Container();
		eastContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		eastContainer.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		eastContainer.getAllStyles().setPadding(Component.TOP, 100);
		
		// Brake Button
		brakeButton = new Button("Brake");
		brakeButton.getUnselectedStyle().setBgTransparency(255);
		brakeButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		brakeButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		brakeButton.getSelectedStyle().setBgTransparency(100);
		brakeButton.getAllStyles().setPadding(Component.TOP, 5);
		brakeButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		brakeButton.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		brakeCommand = new BrakeCommand(gw);
		brakeButton.setCommand(brakeCommand);		// Set the brake command to be invoked by the brake button
		addKeyListener('b', brakeCommand);			// Set the brake command to be invoked by the key 'b'
		
		// Steer Right Button
		rightButton = new Button("Right");
		rightButton.getUnselectedStyle().setBgTransparency(255);
		rightButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		rightButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		rightButton.getSelectedStyle().setBgTransparency(100);
		rightButton.getAllStyles().setPadding(Component.TOP, 5);
		rightButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		rightButton.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		steerRightCommand = new RightCommand(gw);
		rightButton.setCommand(steerRightCommand);		// Allow the steer right command to be invoked by the steer right button
		addKeyListener('r', steerRightCommand);			// Allow the steer right command to be invoked by the key 'r'
		
		// Add buttons to the east container
		eastContainer.addComponent(brakeButton);
		eastContainer.addComponent(rightButton);
		this.addComponent(BorderLayout.EAST, eastContainer);
		// END OF EAST SIDE
		
		// SOUTH SIDE of BorderLayout
		Container southContainer = new Container();
		southContainer.setLayout(new FlowLayout(Component.CENTER));
		southContainer.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		
		positionButton = new Button("Position");
		positionButton.getUnselectedStyle().setBgTransparency(255);
		positionButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		positionButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		positionButton.getSelectedStyle().setBgTransparency(255);
		positionButton.getSelectedStyle().setBgColor(ColorUtil.WHITE);
		positionButton.getSelectedStyle().setFgColor(ColorUtil.BLUE);
		positionButton.getAllStyles().setPadding(Component.TOP, 5);
		positionButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		positionButton.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		positionCommand = new PositionCommand(gw);
		positionButton.setCommand(positionCommand);
		positionButton.setEnabled(false); 		// Position Button is disabled on startup (Game Defaults to Play Mode on Start up)
		
		pausePlayButton = new Button("Pause");
		pausePlayButton.getUnselectedStyle().setBgTransparency(255);
		pausePlayButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		pausePlayButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		pausePlayButton.getSelectedStyle().setBgTransparency(255);
		pausePlayButton.getSelectedStyle().setBgColor(ColorUtil.WHITE);
		pausePlayButton.getSelectedStyle().setFgColor(ColorUtil.BLUE);
		pausePlayButton.getAllStyles().setPadding(Component.TOP, 5);
		pausePlayButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		pausePlayButton.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		pausePlayCommand = new PausePlayCommand(this, pausePlayButton);
		pausePlayButton.setCommand(pausePlayCommand);
		
		/*
		// -----------------DEPRECATED FROM A2--------------
		// Collide with NPC Button
		Button collideNPCButton = new Button("Collide with NPC");
		collideNPCButton.getUnselectedStyle().setBgTransparency(255);
		collideNPCButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		collideNPCButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		collideNPCButton.getSelectedStyle().setBgTransparency(100);
		collideNPCButton.getAllStyles().setPadding(Component.TOP, 5);
		collideNPCButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		collideNPCButton.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		CollideNPCCommand collideWithNPCCommand = new CollideNPCCommand(gw);
		collideNPCButton.setCommand(collideWithNPCCommand);		// Allow the collide with NPC command to be invoked by the collide with NPC button
		
		// Collide with Base Button
		Button collideBaseButton = new Button("Collide with Base");
		collideBaseButton.getUnselectedStyle().setBgTransparency(255);
		collideBaseButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		collideBaseButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		collideBaseButton.getSelectedStyle().setBgTransparency(100);
		collideBaseButton.getAllStyles().setPadding(Component.TOP, 5);
		collideBaseButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		collideBaseButton.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		CollideBaseCommand collideWithBaseCommand = new CollideBaseCommand(gw);
		collideBaseButton.setCommand(collideWithBaseCommand);		// Allow the collide with base command to be invoked by the collide with base button
		
		// Collide with Energy Station Button
		Button collideEnergyButton = new Button("Collide with Energy Station");
		collideEnergyButton.getUnselectedStyle().setBgTransparency(255);
		collideEnergyButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		collideEnergyButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		collideEnergyButton.getSelectedStyle().setBgTransparency(100);
		collideEnergyButton.getAllStyles().setPadding(Component.TOP, 5);
		collideEnergyButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		collideEnergyButton.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		CollideEnergyCommand collideWithEnergyCommand = new CollideEnergyCommand(gw);
		collideEnergyButton.setCommand(collideWithEnergyCommand);		// Allow the collide with energy command to be invoked by the collide with energy button
		addKeyListener('e', collideWithEnergyCommand);		// Allow the collide with energy command to be invoked by the key 'e'
		
		// Collide with Drone Button
		Button collideDroneButton = new Button("Collide with Drone");
		collideDroneButton.getUnselectedStyle().setBgTransparency(255);
		collideDroneButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		collideDroneButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		collideDroneButton.getSelectedStyle().setBgTransparency(100);
		collideDroneButton.getAllStyles().setPadding(Component.TOP, 5);
		collideDroneButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		collideDroneButton.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		CollideDroneCommand collideWithDroneCommand = new CollideDroneCommand(gw);
		collideDroneButton.setCommand(collideWithDroneCommand);		// Allow the collide with drone command to be invoked by the collide with drone button
		addKeyListener('g', collideWithDroneCommand);		// Allow the collide with drone command to be invoked by the key 'g'
		
		// Tick Button
		Button tickButton = new Button("Tick");
		tickButton.getUnselectedStyle().setBgTransparency(255);
		tickButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		tickButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		tickButton.getSelectedStyle().setBgTransparency(100);
		tickButton.getAllStyles().setPadding(Component.TOP, 5);
		tickButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		tickButton.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		TickCommand tickCommand = new TickCommand(gw);
		tickButton.setCommand(tickCommand);		// Allow the tick command to be invoked by the tick button
		addKeyListener('t', tickCommand);		// Allow the tick command to be invoked by the key 't'
		
		// Add buttons to south container
		southContainer.addComponent(collideNPCButton);
		southContainer.addComponent(collideBaseButton);
		southContainer.addComponent(collideEnergyButton);
		southContainer.addComponent(collideDroneButton);
		southContainer.addComponent(tickButton);
		// -----------------DEPRECATED FROM A2--------------
		 */
		southContainer.addComponent(positionButton);
		southContainer.addComponent(pausePlayButton);
		this.addComponent(BorderLayout.SOUTH, southContainer);
		// END OF SOUTH SIDE
		
		
		// CENTER of BorderLayout
		//Container centerContainer = new Container();
		//centerContainer.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.rgb(255, 0, 0)));
		this.addComponent(BorderLayout.CENTER, mv);
		// END OF CENTER
		
		// Toolbar
		Toolbar tb = new Toolbar();
		this.setToolbar(tb);
		tb.setTitle("Silli-Challenge Game");	// set title
		// Create help command
		helpCommand = new HelpCommand();
		tb.addCommandToRightBar(helpCommand);
		tb.addCommandToSideMenu(accelerateCommand);			// Allow the accelerate command to be invoked by the accelerate option in the side menu
		// Create sound command
		soundCommand = new SoundCommand(gw);
		soundStatus = new CheckBox();		// Create a check box for the sound command
		soundStatus.setCommand(soundCommand);
		soundStatus.getUnselectedStyle().setBgTransparency(0);
		soundStatus.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		soundStatus.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		soundStatus.getSelectedStyle().setBgTransparency(100);
		tb.addComponentToSideMenu(soundStatus);
		// Create about command
		aboutCommand = new AboutCommand();
		tb.addCommandToSideMenu(aboutCommand);
		// Create exit command
		exitCommand = new ExitCommand(gw);
		tb.addCommandToSideMenu(exitCommand);
		// END OF TOOLBAR
				
		this.show();
		
		// Set GameWorld's height and width to that of the MapView
		gw.setHeight(mv.getHeight());	
		gw.setWidth(mv.getWidth());
		gw.init();
	}

	@Override
	public void run() {
		gw.clockTick(25);
	}
	
	// Method to pause the game
	public void pause() {
		timer.cancel();
		gw.pause();
		disableButtonCommands();		// When paused, disable all Buttons' commands
		
	}
	
	// Method to set the game back to play
	public void play() {
		timer.schedule(25, true, this);
		gw.unpause();
		enableButtonCommands();			// When in play mode, enable Buttons' commands
	}
	
	// Method to disable the commands of the Buttons
	public void disableButtonCommands() {
		accelerateButton.setEnabled(false);
		removeKeyListener('a', accelerateCommand);
		accelerateCommand.setEnabled(false);
		leftButton.setEnabled(false);
		removeKeyListener('l', steerLeftCommand);
		changeStratButton.setEnabled(false);
		brakeButton.setEnabled(false);
		removeKeyListener('b', brakeCommand);
		rightButton.setEnabled(false);
		removeKeyListener('r', steerRightCommand);
		positionButton.setEnabled(true);		// If paused, the position button is available for use
	}
	
	// Method to re-enable the commands of the Buttons
	public void enableButtonCommands() {
		accelerateButton.setEnabled(true);
		addKeyListener('a', accelerateCommand);
		accelerateCommand.setEnabled(true);
		leftButton.setEnabled(true);
		addKeyListener('l', steerLeftCommand);
		changeStratButton.setEnabled(true);
		brakeButton.setEnabled(true);
		addKeyListener('b', brakeCommand);
		rightButton.setEnabled(true);
		addKeyListener('r', steerRightCommand);
		positionButton.setEnabled(false);		// If in play mode, position button needs to be disabled
	}
}
