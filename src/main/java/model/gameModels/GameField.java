package model.gameModels;
import model.saved.SavedGameField;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.Random;

public class GameField extends Pane {
    private int[] groundHeights; // آرایه برای نگهداری ارتفاع نقاط زمین
    private int groundWidth, groundHeight;
    private double[] xPoints, yPoints;
    private Canvas canvas;
    public int waveNumber;
    public Polygon groundPolygon;

    public GameField(int width, int height, int waveNumber) {
        groundWidth = width;
        groundHeight = height;
        groundHeights = new int[width]; // ایجاد آرایه ارتفاع با اندازه برابر عرض زمین
        generateGroundHeights(); // تولید ارتفاع های تصادفی برای نقاط زمین
        canvas = new Canvas(width, height);
        this.getChildren().add(canvas);
        this.waveNumber = waveNumber;
        createPolygon();
    }

    public GameField(SavedGameField savedGameField) {
        groundHeights = savedGameField.groundHeights;
        groundWidth = savedGameField.groundWidth;
        groundHeight = savedGameField.groundHeight;
        waveNumber = savedGameField.waveNumber;
        canvas = new Canvas(groundWidth, groundHeight);
        System.out.println(groundHeights[0]);
        this.getChildren().add(canvas);
        createPolygon();
    }

    private void createPolygon() {
        xPoints = new double[groundWidth + 2];
        yPoints = new double[groundWidth + 2];
        for (int i = 0; i < groundWidth; i++) {
            xPoints[i] = i;
            yPoints[i] = groundHeight - groundHeights[i];
        }
        xPoints[groundWidth] = groundWidth;
        yPoints[groundWidth] = groundHeight;
        xPoints[groundWidth + 1] = 0;
        yPoints[groundWidth + 1] = groundHeight;
        int[] xPointsInt = new int[xPoints.length];
        int[] yPointsInt = new int[yPoints.length];
        for (int i = 0; i < xPoints.length; i++) {
            xPointsInt[i] = (int) xPoints[i];
            yPointsInt[i] = (int) yPoints[i];
        }
        groundPolygon = new Polygon(xPointsInt, yPointsInt, groundWidth + 2);
    }

    public GameField(int waveNumber) {
        this(800, 600, waveNumber);
    }

    private void generateGroundHeights() {
        Random rand = new Random();
        double a = rand.nextDouble() - 1;
        double b = rand.nextDouble() * 10 - 1;
        double c = rand.nextDouble() * 10 - 1;
        double d = rand.nextDouble() * 10 - 1;
        double e = rand.nextDouble() + 10;
        for (int i = 0; i < groundWidth; i++) {
            double x = (double) i / 80;
            groundHeights[i] = (int) ((x - a) * (x - b) * (x - c) * (x - d) * (x - e))/25 + 150;
            if (groundHeights[i] <= 20 ){
                generateGroundHeights();
                return;
            }
        }
    }

    private void generateGroundHeights(int zeros) {

        double[] zerosX = generateZeros(zeros);
        double[] sinZeroX = generateSinZeros(zeros);
        for (int i = 0; i < groundWidth; i++) {
            double x = (double) i / 80;
            double result = 1;
            for (int j = 0; j < zeros; j++) {
                result *= (x - zerosX[j]);
            }



            groundHeights[i] = (int) result / 25 + 150;
            if (groundHeights[i] <= 20 ){
                generateGroundHeights(zeros);
                return;
            }
        }
    }

    private double[] generateSinZeros(int zeros) { //create a random sinus function
        Random rand = new Random();
        double[] zerosX = new double[zeros];
        for (int i = 0; i < zeros - 2; i++) {
            zerosX[i] = rand.nextDouble() * 10 - 1;
        }
        zerosX[zeros - 2] = rand.nextDouble() - 1;
        zerosX[zeros - 1] = rand.nextDouble() + 10;
        return zerosX;
    }

    private double[] generateZeros(int zeros) {
        Random rand = new Random();
        double[] zerosX = new double[zeros];
        for (int i = 0; i < zeros - 2; i++) {
            zerosX[i] = rand.nextDouble() * 10 - 1;
        }
        zerosX[zeros - 2] = rand.nextDouble() - 1;
        zerosX[zeros - 1] = rand.nextDouble() + 10;
        return zerosX;
    }

    public int getGroundHeightAt(int x) {
        if (x < 0 ) return groundHeights[0];
        if (x > 799) return groundHeights[799];
        return groundHeights[x];
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0, 0, groundWidth, groundHeight);
        switch (waveNumber){
            case 1:
                g.setFill(Color.rgb(143, 92, 49, 1));
                break;
            case 2:
                g.setFill(Color.rgb(25, 32, 11, 1));
                break;
            default:
                g.setFill(Color.rgb(91, 56, 37, 1));
        }

        g.fillPolygon(xPoints, yPoints, 802);
    }
    public void draw(Target target) {
        target.draw(canvas.getGraphicsContext2D());
    }


    public void drawGround(GraphicsContext g) {
        // Draw the wavy ground on the GraphicsContext g
        // This is a placeholder implementation and should be replaced with your actual drawing logic
        g.beginPath();
        g.moveTo(0, 600);
        for (int x = 0; x < getWidth(); x++) {
            if (x >= groundHeights.length) {
                break;
            }
            double y = getHeight() - groundHeights[x];
            g.lineTo(x, y);
        }
        g.lineTo(getWidth(), getHeight());
        g.closePath();
        g.fill();
    }

    public SavedGameField save() {
        SavedGameField savedGameField = new SavedGameField();
        savedGameField.groundHeights = groundHeights;
        savedGameField.groundWidth = groundWidth;
        savedGameField.groundHeight = groundHeight;
        savedGameField.waveNumber = waveNumber;
        System.out.println(groundHeights[0]);
        return savedGameField;
    }
}
