package view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.enums.Skins;

public class SkinView {
    public MainMenuView mainMenuView;
    public ImageView buildingSkinImage;
    public ImageView jetSkinImageView;
    public ImageView tankSkinImageView;

    public void setMainMenuView(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
    }


    public void JetChangeSkin(MouseEvent event) {
        Skins.changeJetSkin();
        Image image = new Image(Skins.activatedJetSkin);
        jetSkinImageView.setImage(image);
    }

    public void backButton(MouseEvent event) {
        mainMenuView.setScene(mainMenuView.getScene());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(mainMenuView.getScene());
        stage.show();
    }
}
