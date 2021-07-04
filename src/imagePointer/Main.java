package imagePointer;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    private final double radius = 4;

    private final ObjectProperty<Circle> selectedCircle = new SimpleObjectProperty<>();

    private final ObjectProperty<Point2D> selectedLocation = new SimpleObjectProperty<>();


    public static void main(String[] args) {
        launch(args);
    }

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
                Random rand = new Random();
                int r = rand.nextInt(255);
                int g = rand.nextInt(255);
                int b = rand.nextInt(255);
                System.out.println(e.getX());
                System.out.println(e.getY());
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    Circle marker = createCircle(e.getX() + gridpane.getHgap(), e.getY() + gridpane.getVgap(), r, b, g);
                    Circle marker1 = createCircle(e.getX() + 2 * gridpane.getHgap() + 176, e.getY() + gridpane.getVgap(), r, b, g);
                    Circle marker2 = createCircle(e.getX() + gridpane.getHgap(), e.getY() + 2 * gridpane.getVgap() + 132, r, b, g);
                    Circle marker3 = createCircle(e.getX() + 2 * gridpane.getHgap() + 176, e.getY() + 2 * gridpane.getVgap() + 132, r, b, g);


                    grouproot.getChildren().add(marker);
                    grouproot.getChildren().add(marker1);
                    grouproot.getChildren().add(marker2);
                    grouproot.getChildren().add(marker3);


                    IntField xCoordinate = new IntField(0, 176, (int) e.getX());
                    IntField yCoordinate = new IntField(0, 132, (int) e.getY());
                    VBox lvb = new VBox(new Label("Point"), new HBox(new Label("x = "), xCoordinate, new Label("y = "), yCoordinate));
                    listView.getItems().add(lvb);

                    //NumberExpressionBase

                    xCoordinate.valueProperty().bind(marker.centerXProperty().subtract(20));
                    yCoordinate.valueProperty().bind(marker.centerYProperty().subtract(20));

                    marker.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, event -> {
                        marker1.centerXProperty().bind(marker.centerXProperty().add(196));
                        marker2.centerXProperty().bind(marker.centerXProperty());
                        marker3.centerXProperty().bind(marker.centerXProperty().add(196));
                        marker1.centerYProperty().bind(marker.centerYProperty());
                        marker2.centerYProperty().bind(marker.centerYProperty().add(152));
                        marker3.centerYProperty().bind(marker.centerYProperty().add(152));
                        event.consume();
                    });

                    markerUnbind(marker, marker1, marker2, marker3);

                    marker1.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, event -> {
                        marker.centerXProperty().bind(marker1.centerXProperty().subtract(196));
                        marker2.centerXProperty().bind(marker1.centerXProperty().subtract(196));
                        marker3.centerXProperty().bind(marker1.centerXProperty());
                        marker.centerYProperty().bind(marker1.centerYProperty());
                        marker2.centerYProperty().bind(marker1.centerYProperty().add(152));
                        marker3.centerYProperty().bind(marker1.centerYProperty().add(152));
                        event.consume();
                    });

                    markerUnbind(marker1, marker, marker2, marker3);

                    marker2.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, event -> {
                        marker.centerXProperty().bind(marker2.centerXProperty());
                        marker1.centerXProperty().bind(marker2.centerXProperty().add(196));
                        marker3.centerXProperty().bind(marker2.centerXProperty().add(196));
                        marker.centerYProperty().bind(marker2.centerYProperty().subtract(152));
                        marker1.centerYProperty().bind(marker2.centerYProperty().subtract(152));
                        marker3.centerYProperty().bind(marker2.centerYProperty());
                        event.consume();
                    });

                    markerUnbind(marker2, marker, marker1, marker3);

                    marker3.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, event -> {
                        marker.centerXProperty().bind(marker3.centerXProperty().subtract(196));
                        marker1.centerXProperty().bind(marker3.centerXProperty());
                        marker2.centerXProperty().bind(marker3.centerXProperty().subtract(196));
                        marker.centerYProperty().bind(marker3.centerYProperty().subtract(152));
                        marker1.centerYProperty().bind(marker3.centerYProperty().subtract(152));
                        marker2.centerYProperty().bind(marker3.centerYProperty());
                        event.consume();
                    });

                    markerUnbind(marker3, marker, marker1, marker2);

                    xCoordinate.setOnKeyReleased(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent keyEvent) {
                            if (keyEvent.getCode() == KeyCode.ENTER) {
                                //setContentDisplay(ContentDisplay.TEXT_ONLY);

                                // field is empty, cancel the edit.
                                if (xCoordinate.getText() == null || xCoordinate.getText().equals("")) {
                                    return;
                                }

                                try {
                                    int editedValue = Integer.parseInt(xCoordinate.getText());
                                    int center = editedValue + 20;
                                    marker.setCenterX(center);
                                    marker2.setCenterX(center);
                                    int centerX2 = editedValue + 40 + 176;
                                    marker1.setCenterX(centerX2);
                                    marker3.setCenterX(centerX2);
                                } catch (NumberFormatException e) {
                                    System.out.println("NumberFormatException");
                                }
                            }
                        }
                    });

                    yCoordinate.setOnKeyReleased(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent keyCode) {
                            if (keyCode.getCode() == KeyCode.ENTER) {
                                //text.setContentDisplay(ContentDisplay.TEXT_ONLY);

                                // field is empty, cancel the edit.
                                if (yCoordinate.getText() == null || yCoordinate.getText().equals("")) {
                                    return;
                                }

                                try {
                                    int editedValue = Integer.parseInt(yCoordinate.getText());
                                    int center = editedValue + 20;
                                    marker.setCenterY(center);
                                    marker1.setCenterY(center);
                                    int centerY2 = editedValue + 40 + 132;
                                    marker2.setCenterY(centerY2);
                                    marker3.setCenterY(centerY2);
                                } catch (NumberFormatException e) {
                                    System.out.println("NumberFormatException");
                                }
                            }
                        }
                    });
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

    private void markerUnbind(Circle selected, Circle other1, Circle other2, Circle other3) {
        selected.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, event -> {
            other1.centerXProperty().unbind();
            other2.centerXProperty().unbind();
            other3.centerXProperty().unbind();
            other1.centerYProperty().unbind();
            other2.centerYProperty().unbind();
            other3.centerYProperty().unbind();
            event.consume();
        });
    }


    private Circle createCircle(double x, double y, int r, int g, int b) {
        Circle circle = new Circle(x, y, radius, Color.rgb(r, g, b));

        circle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            selectedCircle.set(circle);
            selectedLocation.set(new Point2D(x, y));
        });


        circle.setOnMousePressed(e -> {
            selectedLocation.set(new Point2D(e.getX(), e.getY()));
        });

        circle.setOnMouseDragged(e -> {
            double deltaX = e.getX() - selectedLocation.get().getX();
            double deltaY = e.getY() - selectedLocation.get().getY();
            circle.setCenterX(circle.getCenterX() + deltaX);
            circle.setCenterY(circle.getCenterY() + deltaY);
            selectedLocation.set(new Point2D(e.getX(), e.getY()));
        });

        return circle;
    }

}
