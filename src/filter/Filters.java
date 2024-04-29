package filter;

public class Filters {
    
    public static void blur(int height, int width, RgbTriple[][] image) {
        
        RgbTriple[][] tempImage = new RgbTriple[height][width];
        
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                
                int totalBlue, totalGreen, totalRed;
                totalBlue = totalGreen = totalRed = 0;
                float counter = 0f;
                
                for (int y = row - 1; y <= row + 1; y++) {
                    for (int x = column - 1; x <= column + 1; x++) {
                        
                        if (y > -1 && y < height && x > -1 && x < width) {
                            totalBlue += Byte.toUnsignedInt(image[y][x].blue);
                            totalGreen += Byte.toUnsignedInt(image[y][x].green);
                            totalRed += Byte.toUnsignedInt(image[y][x].red);
                            counter++;
                        }
                        
                    }
                }
                byte blue = (byte) Math.round(totalBlue / counter);
                byte green = (byte) Math.round(totalGreen / counter);
                byte red = (byte) Math.round(totalRed / counter);
               
                tempImage[row][column] = new RgbTriple(blue, green, red); 
            } 
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                image[i][j] = tempImage[i][j];
            }  
        }
    }
    
    public static void edge(int height, int width, RgbTriple[][] image) {
        
        RgbTriple[][] tempImage = new RgbTriple[height][width];
        RgbTriple blackPixel = new RgbTriple((byte)0, (byte)0, (byte)0);
        RgbTriple tempPixel;
        
        final int[][] GX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        final int[][] GY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                int totalBlueY, totalGreenY, totalRedY;
                int totalBlueX, totalGreenX, totalRedX;
                totalBlueY = totalGreenY = totalRedY = 0;
                totalBlueX = totalGreenX = totalRedX = 0;
                
                for (int y = row - 1, boxY = 0; y <= row + 1; y++, boxY++) {
                    for (int x = column - 1, boxX = 0; x <= column + 1; x++, boxX++) {
                        
                        tempPixel = (y < 0 || y > height - 1 || x < 0 || x > width -1) ?
                                blackPixel : image[y][x];
                                
                        totalBlueY += Byte.toUnsignedInt(tempPixel.blue) * GY[boxY][boxX];
                        totalGreenY += Byte.toUnsignedInt(tempPixel.green) * GY[boxY][boxX];
                        totalRedY += Byte.toUnsignedInt(tempPixel.red) * GY[boxY][boxX];
                        
                        totalBlueX += Byte.toUnsignedInt(tempPixel.blue) * GX[boxY][boxX];
                        totalGreenX += Byte.toUnsignedInt(tempPixel.green) * GX[boxY][boxX];
                        totalRedX += Byte.toUnsignedInt(tempPixel.red) * GX[boxY][boxX];
                        
                    }
                }
                
                int blue = (int) Math.round(Math.sqrt((totalBlueY * totalBlueY) + totalBlueX * totalBlueX));
                int green = (int) Math.round(Math.sqrt((totalGreenY * totalGreenY) + totalGreenX * totalGreenX));
                int red = (int) Math.round(Math.sqrt((totalRedY * totalRedY) + totalRedX * totalRedX));
                
                blue = (blue > 255) ? 255 : blue;
                green = (green > 255) ? 255 : green;
                red = (red > 255) ? 255 : red;
                
                tempImage[row][column] = new RgbTriple((byte) blue, (byte) green, (byte) red);
            }
        }
        
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                image[row][column] = tempImage[row][column];
            }
        }
    }
    
    public static void grayscale(int height, int width, RgbTriple[][] image) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                
                int blue = Byte.toUnsignedInt(image[i][j].blue);
                int green = Byte.toUnsignedInt(image[i][j].green);
                int red = Byte.toUnsignedInt(image[i][j].red);
                
                int average = Math.round((blue + green + red) / 3f);
                
                image[i][j].blue = image[i][j].green = image[i][j].red = (byte) average;
            } 
        }
    }
    
    public static void reflect(int height, int width, RgbTriple[][] image) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width / 2; j++) {
                RgbTriple aux = image[i][j];
                image[i][j] = image[i][(width - 1) - j];
                image[i][(width - 1) - j] = aux;
            }
        }
    }
}
