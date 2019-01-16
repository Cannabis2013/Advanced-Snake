package BaseKit;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class View extends Object{

	public View() {
		drawBoard = new Canvas();
		mainScene = new Scene(new Pane());
		painter = drawBoard.getGraphicsContext2D();
		backgroundImage = null;
		children = new ArrayList<>();
		P = null;
		
		
		paintUpdate();
		setupInputEventHandlers();
		setupResizeEvents();
		defaultLocation();
	}
	
	public View(View parent) {
		
		drawBoard = null;
		mainScene = null;
		painter = parent.getPainter();
		backgroundImage = null;
		children = new ArrayList<>();
		P = parent;
		P.addChild(this);
	}
	public View(Pane layout)
	{
		drawBoard = new Canvas();
		mainScene = new Scene(new Pane());
		painter = drawBoard.getGraphicsContext2D();
		backgroundImage = null;
		children = new ArrayList<>();
		
		setLayout(layout);
		setupResizeEvents();
		defaultLocation();
	}
	
	public GraphicsContext getPainter()
	{
		return drawBoard.getGraphicsContext2D();
	}
	
	// Get dimension and position
	
	public Dimension Size()
	{
		return mainStage.size();
	}
	
	public double Width()
	{
		return mainStage.width();
	}
	
	public double Height()
	{
		if(Hint == sizeHint.fullScreen)
			return mainStage.height();
		else
		return mainStage.height() - 39;
	}
	
	public void setWidth(double w)
	{
		mainStage.setWidth(w);
	}
	
	public void setHeight(double h)
	{
		mainStage.setHeight(h);
	}
	
	public void setSizeHint(sizeHint hint)
	{
		Hint = hint;
	}
	
	public double X()
	{
		return mainStage.X();
	}
	
	public double Y()
	{
		return mainStage.Y();
	}
	
	public void setX(double x)
	{
		mainStage.setX(x);
	}
	
	public void setY(double y)
	{
		mainStage.setY(y);
	}
	
	public PointD position()
	{
		return mainStage.position();
	}
	
	// Set methods
	
	public void setMinimumSize(int minWidth, int minHeight)
	{
		mainStage.setMaxWidth(minWidth);
		mainStage.setMaxHeight(minHeight);
		mainStage.setFullScreen(true);
	}
	
	public void setMaximumSize(int maxWidth, int maxHeight)
	{
		mainStage.setMaxWidth(maxWidth);
		mainStage.setMaxHeight(maxHeight);
	}
	public void setFixedSize(double fixedWidth, double fixedHeight)
	{
		mainStage.setMaxHeight(fixedHeight);
		mainStage.setMinHeight(fixedHeight);
		mainStage.setMaxWidth(fixedWidth + 17);
		mainStage.setMinWidth(fixedWidth + 17);
	}
	
	public void setFullScreen(boolean full)
	{
		mainStage.setFullScreen(true);
		mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		setSizeHint(sizeHint.fullScreen);
	}
	
	public void setBackground(Image img)
	{
		backgroundImage = img;
		if(backgroundImage != null)
		{
			painter.drawImage(backgroundImage, 0, 0, Width(), Height());
		}
	}
	
	public void setBackgroundColor(Color color)
	{
		backgroundColor = color;
		backgroundImage = null;
		painter.fillRect(0, 0, Width(), Height());
	}
	
	protected void paintClear()
	{
		painter.clearRect(0, 0, Width(), Height());
		if(backgroundImage != null)
			painter.drawImage(backgroundImage, 0, 0, Width(), Height());
		else
		{
			painter.setFill(backgroundColor);
			painter.fillRect(0, 0, Width(), Height());
		}
		
	}

	protected void paintUpdate()
	{
		Pane grid = new Pane(drawBoard);
		setLayout(grid);
	}
	
	private void defaultLocation()
	{
		mainStage.setSize(400, 400);
		Rectangle2D cRect = new Rectangle2D(mainStage.getX(), mainStage.getY(), mainStage.getWidth(), mainStage.getHeight());

		ObservableList<Screen> screens = Screen.getScreensForRectangle(cRect);

		double halfWidth = mainStage.width()/2;
		double halfHeight = mainStage.height() /2;

		if(!screens.isEmpty())
		{
			Screen currentScreen= screens.get(0);
			Rectangle2D rect = currentScreen.getBounds();
			int h = (int) rect.getHeight(),
					w = (int) rect.getWidth();
			mainStage.setPosition(w / 2 - halfWidth, h /2 - halfHeight);
		}
	}
	
	public void show() {
		mainStage.show();
		drawBoard.setWidth(mainStage.getWidth());
		drawBoard.setHeight(mainStage.getHeight());
	}
	public void hide() {
		mainStage.hide();
	}

	public void Close()
	{
		mainStage.close();
	}
	
	/*
	 * Scenebuilder related.
	 * Enables the user to get elements by their id in the same manner known from Javascript.
	 */
	
	protected Node getElementById(String id)
	{
		return mainScene.lookup(id);
	}
	
	/*
	 * Setup layouts.
	 * This section contains two methods in which the second overloads the first.
	 * The first takes a Pane object and builds the widget around it.
	 * The second takes a String that holds a path to a fxml file. This is used when designing widgets with the scenebuilder extension.
	 */
	
	protected void setLayout(Pane root)
	{
		mainScene.setRoot(root);
		mainStage.setScene(mainScene);
	}

	protected void setLayout(String path)
	{
		try {
			Parent ui = FXMLLoader.load(View.class.getResource(path));
			
			mainScene.setRoot(ui);
			mainStage.setScene(mainScene);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * Parent section
	 */
	
	public boolean HasParent()
	{
		if(P != null)
			return true;
		else
			return false;
	}
	
	public void setParent(View parent)
	{
		P = parent;
	}
	
	public View Parent()
	{
		return P;
	}
	
	public void addChild(View child)
	{
		if(child != null)
			children.add(child);
		else
			throw new IllegalArgumentException();
	}
	
	public View ChildAt(int index)
	{
		if(children.size() > index && index >= 0)
			return children.get(index);
		else
			return null;
	}
	
	public View Child(String childName)
	{
		for (View child : children) {
			if(child.ObjectName().equals(childName))
				return child;
		}
		return null;
	}
	
	public void RemoveChild(View child)
	{
		children.remove(child);
	}
	
	/*
	 * Draw related section.
	 */
	
	public void draw()
	{};
	
	/*
	 * Setup event methods.
	 * Adds the necessary handlers and listeners used in typical window managers.
	 */
	
	private void setupResizeEvents()
	{
		ChangeListener<Number> stageListener = (observable,oldValue,newValue) -> 
		{
			int w = (int) mainStage.getWidth(), h = (int) mainStage.getHeight();
			Dimension dim = new Dimension();
			dim.setSize(w, h);
			resizeEvent(null);
			
			drawBoard = new Canvas(w, h);
			painter = drawBoard.getGraphicsContext2D();
		};
		
		mainStage.widthProperty().addListener(stageListener);
		mainStage.heightProperty().addListener(stageListener);
	}
	
	private void setupInputEventHandlers()
	{
		mainScene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				keyPressEvent(event);
			}
		});
		mainScene.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mouseClickedEvent(event);
			}
		});
		mainScene.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mouseMoveEvent(event);
			}
		});
		
		mainScene.addEventHandler(MouseEvent.MOUSE_DRAGGED,new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mouseDraggedEvent(event);
			}
		});
		
		mainScene.addEventHandler(MouseEvent.MOUSE_RELEASED,new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mouseReleaseEvent(event);
			}
		});
		
		
		mainScene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				keyReleasedEvent(event);
			}
		});
	}
	
	/*
	 * Event section.
	 * The following methods is called when an event occur.
	 */
	
	protected void keyPressEvent(KeyEvent event)
	{}
	
	protected void keyReleasedEvent(KeyEvent event)
	{}
	
	protected void mouseClickedEvent(MouseEvent event)
	{}
	
	protected void mouseMoveEvent(MouseEvent event)
	{}
	
	protected void mouseDraggedEvent(MouseEvent event)
	{}
	protected void mouseReleaseEvent(MouseEvent event) 
	{}
	
	protected void resizeEvent(Event event)
	{}
	
	/*
	 * Member variable/property section.
	 */
	
	public enum sizeHint{maxSize,fixedSize,minimumSize, fullScreen, normal};
	
	private sizeHint Hint = sizeHint.normal;
	protected Canvas drawBoard;
	protected Pane ui;
	protected Image backgroundImage;
	protected View P;
	private myStage mainStage = new myStage();
	protected GraphicsContext painter;
	private Scene mainScene;
	private List<View> children;
	private Color backgroundColor;
}
