package cn.edu.nju.map;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

import cn.edu.nju.SceneSwitch;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import javafx.scene.text.Text;


public class FinishController
{
    private SceneSwitch ss;
    private Scene scene;

    public FinishController(SceneSwitch ss)
    {
        this.ss = ss;
        try
        {
            start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Scene getScene()
    {
        return scene;
    }


    public void start() throws Exception
    {
        AnchorPane stackPane = new AnchorPane();
        stackPane.setOnKeyPressed(e->{
            if(e.getCode()== KeyCode.L)
            {
                ss.openRecordFile();
            }
        });
        ImageView imageViewFinish = new ImageView("image/finish/finishBackground.png");
        imageViewFinish.setFitWidth(1280);
        imageViewFinish.setFitHeight(700);
        stackPane.getChildren().add(imageViewFinish);
        ImageView imageView = new ImageView("image/finish/gameOver.png");

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setFromY(imageView.getLayoutY());
        translateTransition.setByY(10);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(Timeline.INDEFINITE);

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setCycleCount(Timeline.INDEFINITE);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0.5);
        fadeTransition.setAutoReverse(true);

        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);
        scaleTransition.setCycleCount(Timeline.INDEFINITE);
        scaleTransition.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(fadeTransition, translateTransition, scaleTransition);
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.setNode(imageView);
        parallelTransition.setAutoReverse(true);
        parallelTransition.play();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        //grid.setHgap(10);
        grid.setVgap(20);
        //grid.setPadding(new Insets(25, 25, 25, 25));
        Button restartBtn = new Button("返回");
        restartBtn.setPrefWidth(164);
        restartBtn.setPrefHeight(32);
        Button playbackBtn = new Button(" 回放游戏 ");
        playbackBtn.setPrefHeight(32);
        playbackBtn.setPrefWidth(164);
        Button exitBtn = new Button(" 退出游戏 ");
        exitBtn.setPrefWidth(164);
        exitBtn.setPrefHeight(32);

        HBox hbBtn = new HBox();
        HBox hBox = new HBox();
        HBox hBoxExit = new HBox();

        DropShadow shadow = new DropShadow();
        playbackBtn.setEffect(shadow);
        restartBtn.setEffect(shadow);
        exitBtn.setEffect(shadow);




        restartBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e)
            {
                restartBtn.setEffect(shadow);
                Path path = new Path();// 创建一个路径对象
                double x = restartBtn.getLayoutX()-40;
                double y = restartBtn.getLayoutY();
                path.getElements().add(new MoveTo(x, y + 18));// 从哪个位置开始动画，一般来说给组件的默认位置就行
                path.getElements().add(new LineTo(x - 20, y + 18));// 添加一个向左移动的路径
                path.getElements().add(new LineTo(x + 20, y + 18));// 添加一个向右移动的路径 这样就完成第一遍摇头
                path.getElements().add(new LineTo(x - 20, y + 18));// 添加一个向左移动的路径 第二遍
                path.getElements().add(new LineTo(x + 20, y + 18));// 添加一个向右移动的路径 这样就完成第二遍摇头
                path.getElements().add(new LineTo(x, y + 18));// 最后移动到原来的位置

                PathTransition pathTransition = new PathTransition();// 创建一个动画对象
                pathTransition.setDuration(Duration.seconds(0.5));// 动画持续时间 0.5秒
                pathTransition.setPath(path);// 把我们设置好的动画路径放入里面
                pathTransition.setNode(restartBtn);// 给动画添加组件，让某个组件来完成这个动画
                pathTransition.setCycleCount(1);// 执行1遍
                pathTransition.play();// 执行动画
            }
        });

        restartBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                //TODO
//                Sing();
            }
        });

        playbackBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                playbackBtn.setEffect(shadow);
                Path path = new Path();// 创建一个路径对象
                double x = playbackBtn.getLayoutX() - 40;
                double y = playbackBtn.getLayoutY();
                path.getElements().add(new MoveTo(x, y + 18));// 从哪个位置开始动画，一般来说给组件的默认位置就行
                path.getElements().add(new LineTo(x - 20, y + 18));// 添加一个向左移动的路径
                path.getElements().add(new LineTo(x + 20, y + 18));// 添加一个向右移动的路径 这样就完成第一遍摇头
                path.getElements().add(new LineTo(x - 20, y + 18));// 添加一个向左移动的路径 第二遍
                path.getElements().add(new LineTo(x + 20, y + 18));// 添加一个向右移动的路径 这样就完成第二遍摇头
                path.getElements().add(new LineTo(x, y + 18));// 最后移动到原来的位置
                PathTransition pathTransition = new PathTransition();// 创建一个动画对象
                pathTransition.setDuration(Duration.seconds(0.5));// 动画持续时间 0.5秒
                pathTransition.setPath(path);// 把我们设置好的动画路径放入里面
                pathTransition.setNode(playbackBtn);// 给动画添加组件，让某个组件来完成这个动画
                pathTransition.setCycleCount(1);// 执行1遍
                pathTransition.play();// 执行动画
            }
        });


        playbackBtn.setOnMouseClicked(e->{
            ss.openRecordFile();
        });

        exitBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                exitBtn.setEffect(shadow);
                Path path = new Path();// 创建一个路径对象
                double x = exitBtn.getLayoutX() - 40;
                double y = exitBtn.getLayoutY();
                path.getElements().add(new MoveTo(x, y + 18));// 从哪个位置开始动画，一般来说给组件的默认位置就行
                path.getElements().add(new LineTo(x - 20, y + 18));// 添加一个向左移动的路径
                path.getElements().add(new LineTo(x + 20, y + 18));// 添加一个向右移动的路径 这样就完成第一遍摇头
                path.getElements().add(new LineTo(x - 20, y + 18));// 添加一个向左移动的路径 第二遍
                path.getElements().add(new LineTo(x + 20, y + 18));// 添加一个向右移动的路径 这样就完成第二遍摇头
                path.getElements().add(new LineTo(x, y + 18));// 最后移动到原来的位置
                PathTransition pathTransition = new PathTransition();// 创建一个动画对象
                pathTransition.setDuration(Duration.seconds(0.5));// 动画持续时间 0.5秒
                pathTransition.setPath(path);// 把我们设置好的动画路径放入里面
                pathTransition.setNode(exitBtn);// 给动画添加组件，让某个组件来完成这个动画
                pathTransition.setCycleCount(1);// 执行1遍
                pathTransition.play();// 执行动画
            }
        });

        exitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                ss.exitGame();
            }
        });

        restartBtn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e)
            {
                restartBtn.setEffect(null);
            }
        });

        restartBtn.setOnMouseClicked(e->{
            ss.changeToStartScene();
        });
//        restartBtn.setOnAction(new EventHandler<ActionEvent>()
//        {
//            @Override
//            public void handle(ActionEvent event)
//            {
//                System.out.println("RESATART GAME");
//
////                Sing();
//            }
//        });

        Text text=new Text();
        text.setText("这样可以吗？");
        text.setFont(Font.font ("Microsoft YaHei",  FontWeight.BOLD,70));
        text.setFill(Color.BLACK);
        //label.setPrefSize(32,164);

        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().addAll(restartBtn);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(playbackBtn);
        hBoxExit.setAlignment(Pos.CENTER);
        hBoxExit.getChildren().addAll(exitBtn);

        grid.setOpacity(1);
        grid.add(imageView, 1, 1);
        grid.add(text,1,5);
        grid.add(hbBtn, 1, 10);
        grid.add(hBox, 1, 8);
        grid.add(hBoxExit, 1, 12);

        Random random = new Random();
        ArrayList<ImageView> imageList = new ArrayList<ImageView>();
        for (int i = 0; i < 50; i++)
        {
            ImageView im = new ImageView("image/finish/gourd.png");
            im.setPreserveRatio(true);
            int x = random.nextInt(1200);
            int y = 10;
            im.setTranslateX(x);
            im.setTranslateY(y);
            im.setTranslateZ(10);
            imageList.add(im);
        }
        stackPane.getChildren().addAll(imageList);
        imageList.forEach(new Consumer<ImageView>()
        {
            @Override
            public void accept(ImageView imageView)
            {
                double time = random.nextInt(10);
                TranslateTransition t1 = new TranslateTransition(Duration.seconds(time));
                t1.setFromX(imageView.getTranslateX());
                t1.setFromY(imageView.getTranslateY());
                t1.setByX(400);
                t1.setByY(700);

                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(time / 2));
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0);

                FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(time / 2));
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);

                SequentialTransition sequentialTransition = new SequentialTransition();
                sequentialTransition.getChildren().addAll(fadeTransition, fadeTransition1);

                RotateTransition rotateTransition = new RotateTransition(Duration.seconds(time));
                rotateTransition.setFromAngle(0);
                rotateTransition.setToAngle(360);

                ParallelTransition parallelTransition = new ParallelTransition();
                parallelTransition.setNode(imageView);
                parallelTransition.getChildren().addAll(t1, sequentialTransition, rotateTransition);
                parallelTransition.setCycleCount(Animation.INDEFINITE);
                parallelTransition.play();
            }
        });

        stackPane.getChildren().addAll(grid);

        AnchorPane.setTopAnchor(grid, 0.0);
        AnchorPane.setLeftAnchor(grid, 400.0);
        scene = new Scene(stackPane, 1280, 700);
        scene.setCursor(Cursor.CLOSED_HAND);
        scene.getStylesheets().add(getClass().getResource("/css/finish.css").toExternalForm());
    }


//    void Sing()
//    {
//        Media media;
//        MediaPlayer mediaPlayer;
//
//
//        mediaPlayer = new MediaPlayer(media = new Media(""););
//        mediaPlayer.play();
//        System.out.println("we are playing");
//    }
}