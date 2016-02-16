/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testgame;

import gameobjects.Map;
import gameobjects.NPCSprite;
import gameobjects.Sprite;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import controllers.MapController;
import gameUI.DialogBox;
import gameobjects.Door;
import gameobjects.HiddenCollision;
import gameobjects.LayerChanger;
import gameobjects.StaticObject;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
/**
 *
 * @author Ville
 */
public class TestGame extends Application {
    MapController mapController;
    Sprite sprite;
    Map map;
    ScrollPane scrollPane;
    @Override
    public void start(Stage primaryStage) throws IOException {
        MovementTimer timer = new MovementTimer();
        MapController controller = new MapController();
        Image image = new Image("/maps/map1.png");
        controller.initializeMap(new Map(1000, 1000, new ImageView(image)));
        this.map = controller.Map;
        scrollPane = new ScrollPane();
        scrollPane.setHmax(640);
        scrollPane.setVmax(640);
        scrollPane.setContent(map);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Parent root = scrollPane;
        Scene scene = new Scene(root, 640, 640);
        scene.getStylesheets().add("css/dialogbox.css");
        this.sprite = new Sprite("terra", 64.0, 96.0, new Image("/sprites/terra8x2.png"), false, true);
        NPCSprite terra2 = new NPCSprite("terra2", 64.0, 96.0, new Image("/sprites/terra8x2.png"), false, true);
        NPCSprite terra3 = new NPCSprite("terra3", 64.0, 96.0, new Image("/sprites/terra8x2.png"), false, true);
        NPCSprite terra4 = new NPCSprite("terra4", 64.0, 96.0, new Image("/sprites/terra8x2.png"), false, true);
        NPCSprite cow = new NPCSprite("cow", 192, 159.0, new Image("/sprites/cow8x2.png"), false, true);
        StaticObject tree = new StaticObject("tree1", 190.0, 250.0, new Image("/statics/tree1.png"), false, true);
        StaticObject bridgetop = new StaticObject("bridgetop", 640.0, 227.0, new Image("/statics/bridgetop.png"), false, true);
        StaticObject bridgebottom = new StaticObject("bridgetop", 640.0, 227.0, new Image("/statics/bridgebottom.png"), false, true);
        
        HiddenCollision col1 = new HiddenCollision("hidden1",64.0, 64.0, new Image("/statics/hiddencol.png"), false, true);
        HiddenCollision col2 = new HiddenCollision("hidden2",64.0, 64.0, new Image("/statics/hiddencol.png"), false, true);
        HiddenCollision col3 = new HiddenCollision("hidden3",64.0, 64.0, new Image("/statics/hiddencol.png"), false, true);
        HiddenCollision col4 = new HiddenCollision("hidden4",64.0, 64.0, new Image("/statics/hiddencol.png"), false, true);
        HiddenCollision col5 = new HiddenCollision("hidden5",64.0, 64.0, new Image("/statics/hiddencol.png"), false, true);
        HiddenCollision col6 = new HiddenCollision("hidden6",64.0, 64.0, new Image("/statics/hiddencol.png"), false, true);
        HiddenCollision col7 = new HiddenCollision("hidden7",64.0, 64.0, new Image("/statics/hiddencol.png"), false, true);
        HiddenCollision col8 = new HiddenCollision("hidden8",64.0, 64.0, new Image("/statics/hiddencol.png"), false, true);
        LayerChanger changer = new LayerChanger("layerChangeNS", 64.0, 64.0, new Image("/statics/hiddencol.png"), true, true);
        Door door = new Door("door1", 75.0, 124, new Image("/statics/door3.png"), false, true);
        Door fence = new Door("fence1", 136.0, 111, new Image("/statics/fencegate.png"), false, true);
        changer.layerChange(2, 0, 1, 0);
        tree.setCollisionArea(80, 215, 115, 240);
        bridgetop.setCollisionArea(0,0,0,0);
        bridgebottom.setCollisionArea(0,0,0,0);
        col1.setCollisionArea(0, 25, 80, 326);
        col2.setCollisionArea(154, 25, 206, 326);
        col3.setCollisionArea(360, 25, 640, 326);
        col4.setCollisionArea(0, 0, 640, 15);
        col5.setCollisionArea(0, 130, 80, 284);
        col6.setCollisionArea(158, 128, 640, 286);
        col7.setCollisionArea(0, 0, 15, 140);
        col8.setCollisionArea(615, 0, 640, 140);
        
        sprite.setCollisionArea(8, 73, 55, 98);
        terra2.setCollisionArea(8, 73, 55, 98);
        terra3.setCollisionArea(8, 73, 55, 98);
        terra4.setCollisionArea(8, 73, 55, 98);
        cow.setCollisionArea(55, 85, 130, 140);
        changer.setCollisionArea(94, 315, 160, 316);
        door.setCollisionArea(0, 60, 75, 119);
        fence.setCollisionArea(0, 60, 136, 118);
        map.addSprite(sprite, 500, 500, 1);
        map.addSprite(terra2, 500, 125, 2);
        map.addSprite(terra3, 400, 125, 2);
        map.addSprite(terra4, 300, 400, 1);
        map.addSprite(cow, 400, 700, 1);
        DialogBox dialog = new DialogBox("This is a test dialog");
        terra2.setDialog(dialog);
        terra3.setDialog(dialog);
        terra4.setDialog(dialog);
        DialogBox cowdialog = new DialogBox("Moo");
        cow.setDialog(cowdialog);
        map.addStatic(tree, 100, 400, 1);
        map.addStatic(bridgetop, 0, 100, 2);
        map.addStatic(bridgebottom, 0,323,1);
        
        map.addStatic(col1, 0, 100,1);
        map.addStatic(col2, 0, 100,1);
        map.addStatic(col3, 0, 100,1);
        map.addStatic(col4, 0, 100,2);
        map.addStatic(col5, 0, 100,2);
        map.addStatic(col6, 0, 100,2);
        map.addStatic(col7, 0, 100,2);
        map.addStatic(col8, 0, 100,2);
        map.addStatic(changer, 0, 100, 1);
        map.addStatic(door, 800, 500, 1);
        map.addStatic(fence, 600, 600, 1);
        map.startAIs();
        map.checkLayering();
        primaryStage.setScene(scene);
        createKeyboardListeners(scene);
        System.out.println(scrollPane.getLayoutBounds().toString());
        scrollPane.setHvalue(sprite.getLayoutX());
        scrollPane.setVvalue(sprite.getLayoutY());
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void createKeyboardListeners(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:   {
                        map.moveSpriteNorth(sprite);
                        break;
                    }
                    case S:   {
                        map.moveSpriteSouth(sprite);
                        scrollPane.setHvalue(sprite.getLayoutX());
                        scrollPane.setVvalue(sprite.getLayoutY());
                        break;
                        }
                    case A:   {
                        map.moveSpriteWest(sprite);
                        scrollPane.setHvalue(sprite.getLayoutX());
                        scrollPane.setVvalue(sprite.getLayoutY());
                        break;
                }
                    case D:   {
                        map.moveSpriteEast(sprite);
                        scrollPane.setHvalue(sprite.getLayoutX());
                        scrollPane.setVvalue(sprite.getLayoutY());
                        break;
                    }
                    case V: {
                        map.activateObject(sprite);
                    }
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                if(code == KeyCode.A || code == KeyCode.W || code == KeyCode.S
                    || code == KeyCode.D) {
                    map.stopMoving(sprite);
                    sprite.stopMoving();
                }
            }
        });
    }
}
