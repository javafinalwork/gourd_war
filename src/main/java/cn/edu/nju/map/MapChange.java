package cn.edu.nju.map;

import cn.edu.nju.SceneSwitch;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;


public class MapChange
{
    private Scene scene;
    private Pane pane1;
    private AnchorPane root;
    private ImageView btn_left;
    private ImageView btn_right;
    private ImageView selected;
    private DropShadow dropShadow;
    private int pic1;
    private int pic2;
    private int pic3;
    private int pic1y;
    private int picz;
    private SceneSwitch ss;

    public MapChange(SceneSwitch ss)
    {
        this.ss = ss;
        pane1 = getPane(1200, 700);
        dropShadow = new DropShadow();
        dropShadow.setColor(Color.valueOf("#00E5EE"));
        dropShadow.setHeight(50);
        dropShadow.setWidth(50);
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        root = new AnchorPane();
        root.getChildren().add(pane1);

        AnchorPane.setTopAnchor(pane1, 0.0);
        AnchorPane.setLeftAnchor(pane1, 0.0);
        this.scene = new Scene(root, 1280, 700);
//        primaryStage.setScene(scene);
//        primaryStage.setWidth(1280);
//        primaryStage.setHeight(700);
//        primaryStage.centerOnScreen();
//        primaryStage.setTitle("changeMap");
//        primaryStage.show();
    }

    public Scene getScene()
    {
        return this.scene;
    }


    public Pane getPane(int weight, int height)
    {
        picz = 150;
        HBox hb = new HBox((weight - 64 * 2) / 2);
        hb.setAlignment(Pos.CENTER);
        btn_left = new ImageView("/image/map_choose/left.png");
        selected = new ImageView("/image/map_choose/selectMapButton.png");
        btn_right = new ImageView("/image/map_choose/right.png");
        btn_left.setPreserveRatio(true);
        btn_right.setPreserveRatio(true);
        hb.getChildren().addAll(btn_left, selected, btn_right);


        ImageView im1 = new ImageView("/image/map_choose/battle.png");
        im1.setPreserveRatio(true);
        im1.setFitWidth(weight / 2);
        im1.setFitHeight(height / 2);
        ImageView im2 = new ImageView("/image/map_choose/battle.png");
        im2.setPreserveRatio(true);
        im2.setFitWidth(weight / 2);
        im2.setFitHeight(height / 1);
        ImageView im3 = new ImageView("/image/map_choose/battle.png");
        im3.setPreserveRatio(true);
        im3.setFitWidth(weight / 2);
        im3.setFitHeight(height / 1);
        im1.setTranslateZ(picz);
        im2.setTranslateZ(0);
        im3.setTranslateZ(picz);
        pic1 = 0;
        pic2 = (int) (weight / 2 - im2.getFitWidth() / 2);
        pic3 = (int) (weight - im2.getFitWidth());
        pic1y = (int) (hb.getHeight() + im1.getFitHeight() / 2);
//        System.out.println(im1.getFitHeight());
//        System.out.println(hb.getLayoutX());
//        System.out.println(im2.getFitHeight());
//        System.out.println(pic1y);
        im1.setTranslateX(pic1);
        im1.setTranslateY(pic1y);
        im2.setTranslateX(pic2);
        im2.setTranslateY(pic1y);
        im3.setTranslateX(pic3);
        im3.setTranslateY(pic1y);
        ArrayList<ImageView> imageList = new ArrayList<>();
        imageList.add(im1);
        imageList.add(im2);
        imageList.add(im3);

        btn_right.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                ImageView tmpLeft = imageList.get(0);
                ImageView tmpMiddle = imageList.get(1);
                ImageView tmpRight = imageList.get(2);
                middleToRight(tmpMiddle);
                rightToLeft(tmpRight);
                leftToMiddle(tmpLeft);
                imageList.clear();
                imageList.add(tmpRight);
                imageList.add(tmpLeft);
                imageList.add(tmpMiddle);
            }
        });

        btn_left.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                ImageView tmpLeft = imageList.get(0);
                ImageView tmpMiddle = imageList.get(1);
                ImageView tmpRight = imageList.get(2);
                leftToRight(tmpLeft);
                middleToLeft(tmpMiddle);
                rightToMiddle(tmpRight);
                imageList.clear();
                imageList.add(tmpMiddle);
                imageList.add(tmpRight);
                imageList.add(tmpLeft);
            }
        });

        selected.setOnMouseClicked(event -> {
            System.out.println("这张图片被选择了");
            ss.changeToSignupScene();
        });

        AnchorPane anchorPane1 = new AnchorPane();//布局
        anchorPane1.getChildren().addAll(im1, im2, im3);

        SubScene bottomScene = new SubScene(anchorPane1, weight, height, true, SceneAntialiasing.BALANCED);//put pic

        PerspectiveCamera camera = new PerspectiveCamera();
        bottomScene.setCamera(camera);
        bottomScene.resize(1200,700);
        bottomScene.setOpacity(0.5);

        StackPane stackPane1 = new StackPane();
        stackPane1.getChildren().add(new ImageView("image/map_choose/background.png"));
        stackPane1.getChildren().add(bottomScene);
//        stackPane1.getChildren().add(anchorPane1);
        stackPane1.getChildren().add(hb);
//        stackPane1.setStyle("-fx-background-image: url(" + "image/map_choose/background.png" + "); " +
//                "-fx-background-repeat: no-repeat;" +
//                " -fx-background-size: cover;");

        return stackPane1;
    }

    private void middleToRight(ImageView image)
    {
        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.seconds(0.5));
        translate.setFromX(pic2);
        translate.setFromY(pic1y);
        translate.setFromZ(0);
        //translate.setNode(image);
        translate.setToX(pic3);
        translate.setToY(pic1y);
        translate.setToZ(picz);

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0.5);

        FadeTransition fadeTransition1 = new FadeTransition();
        fadeTransition1.setFromValue(0.5);
        fadeTransition1.setToValue(1);

        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(fadeTransition, fadeTransition1);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(sequentialTransition, translate);
        parallelTransition.setNode(image);
        parallelTransition.play();
    }

    private void rightToLeft(ImageView image)
    {
        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.seconds(0.5));
        translate.setFromX(pic3);
        translate.setFromY(pic1y);
        translate.setFromZ(150);
        //translate.setNode(image);
        translate.setToX(pic1);
        translate.setToY(pic1y);
        translate.setToZ(150);

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0.7);

        FadeTransition fadeTransition1 = new FadeTransition();
        fadeTransition1.setFromValue(0.7);
        fadeTransition1.setToValue(1);

        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(fadeTransition, fadeTransition1);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(sequentialTransition, translate);
        parallelTransition.setNode(image);
        parallelTransition.play();
    }

    private void leftToMiddle(ImageView image)
    {
        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.seconds(0.5));
        translate.setFromX(pic1);
        translate.setFromY(pic1y);
        translate.setFromZ(150);
        //translate.setNode(image);
        translate.setToX(pic2);
        translate.setToY(pic1y);
        translate.setToZ(0);

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0.5);

        FadeTransition fadeTransition1 = new FadeTransition();
        fadeTransition1.setFromValue(0.5);
        fadeTransition1.setToValue(1);

        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(fadeTransition, fadeTransition1);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(sequentialTransition, translate);
        parallelTransition.setNode(image);
        parallelTransition.play();
    }

    private void middleToLeft(ImageView image)
    {
        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.seconds(0.5));
        translate.setFromX(pic2);
        translate.setFromY(pic1y);
        translate.setFromZ(0);
        //translate.setNode(image);
        translate.setToX(pic1);
        translate.setToY(pic1y);
        translate.setToZ(picz);

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0.5);

        FadeTransition fadeTransition1 = new FadeTransition();
        fadeTransition1.setFromValue(0.5);
        fadeTransition1.setToValue(1);

        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(fadeTransition, fadeTransition1);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(sequentialTransition, translate);
        parallelTransition.setNode(image);
        parallelTransition.play();
    }

    private void rightToMiddle(ImageView image)
    {
        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.seconds(0.5));
        translate.setFromX(pic3);
        translate.setFromY(pic1y);
        translate.setFromZ(150);
        //translate.setNode(image);
        translate.setToX(pic2);
        translate.setToY(pic1y);
        translate.setToZ(0);

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0.7);

        FadeTransition fadeTransition1 = new FadeTransition();
        fadeTransition1.setFromValue(0.7);
        fadeTransition1.setToValue(1);

        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(fadeTransition, fadeTransition1);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(sequentialTransition, translate);
        parallelTransition.setNode(image);
        parallelTransition.play();
    }

    private void leftToRight(ImageView image)
    {
        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.seconds(0.5));
        translate.setFromX(pic1);
        translate.setFromY(pic1y);
        translate.setFromZ(150);
        //translate.setNode(image);
        translate.setToX(pic3);
        translate.setToY(pic1y);
        translate.setToZ(150);

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0.5);

        FadeTransition fadeTransition1 = new FadeTransition();
        fadeTransition1.setFromValue(0.5);
        fadeTransition1.setToValue(1);

        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(fadeTransition, fadeTransition1);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(sequentialTransition, translate);
        parallelTransition.setNode(image);
        parallelTransition.play();
    }


    public Pane selsectButton(int weight, int height)
    {
        Button btn = new Button();
        AnchorPane anchorPane1 = new AnchorPane();//布局
        anchorPane1.getChildren().addAll(btn);
        SubScene bottomScene = new SubScene(anchorPane1, weight, height, true, SceneAntialiasing.BALANCED);//put pic
        StackPane stackPane1 = new StackPane();
        stackPane1.getChildren().addAll(bottomScene);
        stackPane1.setStyle("-fx-background-color:#00FF00");
        return stackPane1;
    }
}
