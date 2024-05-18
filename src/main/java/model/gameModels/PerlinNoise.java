package model.gameModels;


public class PerlinNoise {
    private static final int PERSISTENCE = 4;
    private static final int OCTAVES = 4;

    public static double noise(int x, int y, double prevY){
        double total = 0;
        for (int i = 0; i < OCTAVES - 1; i++) {
            int frequency = (int) Math.pow(2, i);
            int amplitude = (int) Math.pow(PERSISTENCE, i);
            total = smoothNoise(x * frequency, y * frequency) * amplitude;
        }
        total = Math.abs(total);

        // Scale the noise value to the range -1.5 to 1.5
        double scaledNoise = (total * 3) - 1.5;

        // Add the scaled noise value to the previous y-axis value
        double newY = prevY + scaledNoise;

        // Ensure the difference between consecutive y-axis values doesn't exceed 3
        if (newY - prevY > 3) {
            newY = prevY + 3;
        } else if (newY - prevY < -3) {
            newY = prevY - 3;
        }

        return newY;
    }

    private static double smoothNoise(int x, int y) {
        double corners = (randomNoise(x - 1, y - 1) + randomNoise(x + 1, y - 1) + randomNoise(x - 1, y + 1) + randomNoise(x + 1, y + 1)) / 16;
        double sides = (randomNoise(x - 1, y) + randomNoise(x + 1, y) + randomNoise(x, y - 1) + randomNoise(x, y + 1)) / 8;
        double center = randomNoise(x, y) / 4;
        return corners + sides + center;
    }

    private static double randomNoise(int x, int y) {
        int n = x + y * 57;
        n = (n << 13) ^ n;
        return (1.0 - ((n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0);
    }
}

