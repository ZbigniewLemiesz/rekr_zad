package imagePointer;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {
    private static final PseudoClass SELECTED_P_C = PseudoClass.getPseudoClass("selected");

    private final double radius = 4;
    private final double spacing = 2;

    private final ObjectProperty<Circle> selectedCircle = new SimpleObjectProperty<>();

    private final ObjectProperty<Point2D> selectedLocation = new SimpleObjectProperty<>();


    @Override
    public void start(Stage primaryStage) {

        SplitPane splitPane = new SplitPane();

        //IMAGES PART
        primaryStage.setTitle("Title");
        Group grouproot = new Group();

        GridPane gridpane = new GridPane();
        gridpane.setHgap(20);
        gridpane.setVgap(20);

        //list pointers part
        ListView listView = new ListView();


        HBox hbox = new HBox(listView);


        VBox leftControl = new VBox(hbox);


        final ImageView imv = new ImageView();
        final Image image = new Image("file:assert/950510-alzheimers-dementia-feature-732x549-thumbnail.jpg");
        imv.setImage(image);
        // anchorRoot.getChildren().add(imv);

        final ImageView imv1 = new ImageView();
        imv1.setImage(image);
        // anchorRoot.getChildren().add(imv1);
        final ImageView imv2 = new ImageView();
        imv2.setImage(image);
        //anchorRoot.getChildren().add(imv2);
        final ImageView imv3 = new ImageView();
        imv3.setImage(image);
        // anchorRoot.getChildren().add(imv3);

        imv.setPickOnBounds(true);

        gridpane.add(imv, 1, 1);
        gridpane.add(imv1, 2, 1);
        gridpane.add(imv2, 1, 2);
        gridpane.add(imv3, 2, 2);

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Point2D sceneCoords = new Point2D(e.getSceneX(), e.getSceneY());
                Point2D anchorPaneCoords = gridpane.sceneToLocal(sceneCoords);
                Random rand = new Random();
                int r = rand.nextInt(255);
                int g = rand.nextInt(255);
                int b = rand.nextInt(255);
                System.out.println(e.getX());
                System.out.println(e.getY());
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    Circle marker = new Circle(e.getX()+20, e.getY()+20, 5, Color.rgb(r, g, b));
                    Circle marker1 = new Circle(e.getX()+40+176, e.getY()+20, 5, Color.rgb(r, g, b));
                    Circle marker2 = new Circle(e.getX()+20, e.getY()+20+132+20, 5, Color.rgb(r, g, b));
                    Circle marker3 = new Circle(e.getX()+40+176, e.getY()+20+132+20, 5, Color.rgb(r, g, b));
                    Point p = new Point((int) e.getX(), (int) e.getY());
                    listView.getItems().add(new VBox(new Label("POINT:"), new Label("X: "), new IntField(1, 1000, (int) e.getX()), new Label("Y: "), new IntField(1, 1000, (int) e.getY())));
                    addEventHandler(grouproot, marker);
                    grouproot.getChildren().add(marker);
                    grouproot.getChildren().add(marker1);
                    grouproot.getChildren().add(marker2);
                    grouproot.getChildren().add(marker3);

                }
            }
        };

        imv.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        imv1.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        imv2.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        imv3.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

        grouproot.getChildren().add(gridpane);

        // END IMAGES PART

        VBox rightControl = new VBox(grouproot);

        splitPane.getItems().addAll(leftControl, rightControl);
        Scene scene = new Scene(splitPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void addEventHandler(Group parent, Node node) {
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent me) -> {
            if (me.getButton().equals(MouseButton.SECONDARY)) {
                parent.getChildren().remove(node);
            }
        });
    }

    private Circle createCircle(int x, int y) {
        Circle circle = new Circle();
        circle.setCenterX(x * (spacing + radius + radius) + spacing);
        circle.setCenterY(y * (spacing + radius + radius) + spacing);
        circle.setRadius(radius);

        circle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            selectedCircle.set(circle);
            selectedLocation.set(new Point2D(x, y));
        });

        return circle;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
