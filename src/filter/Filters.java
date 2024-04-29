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
                            //System.out.println("Entro");
                            totalBlue += Byte.toUnsignedInt(image[y][x].blue);
                            totalGreen += Byte.toUnsignedInt(image[y][x].green);
                            totalRed += Byte.toUnsignedInt(image[y][x].red);
                            counter++;
                            //if ((row % 100) == 0)
                              //  System.out.println("blue: " + totalBlue + "  green: " + totalGreen + "  red: " + totalRed);
                        }
                        
                    }
                }
                byte blue = (byte) Math.round(totalBlue / counter);
                byte green = (byte) Math.round(totalGreen / counter);
                byte red = (byte) Math.round(totalRed / counter);
                
                /*
                int blueint = Byte.toUnsignedInt(blue);
                int greenint = Byte.toUnsignedInt(green);
                int redint = Byte.toUnsignedInt(red);
                
                if ((column % 100) == 0)
                    System.out.println("blue: " + blueint + "  green: " + greenint + "  red: " + redint);*/
                
                tempImage[row][column] = new RgbTriple(blue, green, red);
                
            } 
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                image[i][j] = tempImage[i][j];
            }  
        }
    }
    
    public static void edge(int height, int width, RgbTriple[][] imagee) {
        
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
