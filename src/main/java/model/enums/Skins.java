package model.enums;

public class Skins {
    public static final String JetSkin1 = "file:src/main/resources/Images/Game/jet1.png";
    public static final String JetSkin2 = "file:src/main/resources/Images/Game/jet2.png";
    public static final String JetSkin3 = "file:src/main/resources/Images/Game/jet3.png";
    public static final String JetSkin4 = "file:src/main/resources/Images/Game/jet4.png";
    public static final String JetSkin5 = "file:src/main/resources/Images/Game/jet5.png";
    public static final String JetSkin6 = "file:src/main/resources/Images/Game/jet6.png";
    public static final String[] JetSkins = {JetSkin1, JetSkin2, JetSkin3, JetSkin4, JetSkin5, JetSkin6};
    public static final String TankSkin1 = "file:src/main/resources/Images/Game/tank1.png";
    public static final String TankSkin2 = "file:src/main/resources/Images/Game/tank2.png";
    public static final String TankSkin3 = "file:src/main/resources/Images/Game/tank3.png";
    public static final String TankSkin4 = "file:src/main/resources/Images/Game/tank4.png";
    public static final String TankSkin5 = "file:src/main/resources/Images/Game/tank5.png";
    public static final String TankSkin6 = "file:src/main/resources/Images/Game/tank6.png";
    public static final String BuildingSkin1 = "file:src/main/resources/Images/Game/building1.png";
    public static final String BuildingSkin2 = "file:src/main/resources/Images/Game/building2.png";
    public static final String BuildingSkin3 = "file:src/main/resources/Images/Game/building3.png";
    public static final String BuildingSkin4 = "file:src/main/resources/Images/Game/building4.png";
    public static final String BuildingSkin5 = "file:src/main/resources/Images/Game/building5.png";
    public static String activatedJetSkin = JetSkin1;

    public static void changeJetSkin(int i) {
        activatedJetSkin = JetSkins[i];
    }
    public static void changeJetSkin(){
        activatedJetSkin = JetSkins[(int)(Math.random()*6)];
    }
}
