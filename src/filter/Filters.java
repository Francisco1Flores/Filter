package filter;

public class Filters {
    
    public static void blur(int height, int width, RgbTriple[][] image) {
        
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
