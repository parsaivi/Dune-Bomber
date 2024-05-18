package view;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class LoadingController {

    @FXML
    private ImageView backgroundImageView;

    private final String[] backgroundImages = {
            "/Images/loading1.jpg",
            "/Images/loading2.jpg",
            "/Images/loading3.jpg",
            "/Images/loading4.jpg",
            "/Images/loading5.jpg",
            "/Images/loading6.jpg",
            "/Images/loading7.jpg",
            "/Images/loading8.jpg",
            "/Images/loading9.jpg",
            "/Images/loading10.jpg",
            "/Images/loading11.jpg",
    };

    public void initialize() {
        String randomBackgroundImage = backgroundImages[new Random().nextInt(backgroundImages.length)];
        Image image = new Image(getClass().getResourceAsStream(randomBackgroundImage));
        backgroundImageView.setImage(image);
    }
}