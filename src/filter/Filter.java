package filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Filter {

    public static void main(String[] args) {
        
        String filters = "begr";
        
        char filter = args[0].charAt(1);
        
        if (!filters.contains("" + filter)) {
            System.out.println("Invalid filter");
            System.exit(0);
        }
        
        if (args[0].length() != 2) {
            System.out.println("Only one filter allowed");
            System.exit(0);
        }
        
        if (args.length != 3) {
            System.out.println("Usage: java Filter [flag] infile outfile");
            System.exit(0);
        }
        
        String inFile = args[1];
        String outFile = args[2];
        
        FileInputStream inptr = null;
        FileOutputStream outptr = null;
        
        try {
            inptr = new FileInputStream(inFile);
            outptr = new FileOutputStream(outFile);
            
            BitMapFileHeader bf = new BitMapFileHeader(inptr);
        
            BitMapInfoHeader bi = new BitMapInfoHeader(inptr);
            
            
            System.out.println("Type: " + bf.bfType);
            System.out.println("Size doc: " + bf.bfSize);
            System.out.println("Height: " + bi.biHeight);
            System.out.println("Width: " + bi.biWidth);
            System.out.println("OffBits: " + bf.bfOffBits);
            System.out.println("Bit Info Size: " + bi.biSize);
            System.out.println("BitCount tamaño de pixel: " + bi.biBitCount);
            System.out.println("Compression debe ser cero: " + bi.biCompression);
            System.out.println("Image size: " + bi.biSizeImage);
            
            System.out.println("\n\n\n comparacion offBits con 54: " + Integer.compareUnsigned(bf.bfOffBits, 54));
            
            if (bf.bfType != 0x4d42 || bf.bfOffBits != 54 || bi.biSize != 40 ||
                bi.biBitCount != 24 || bi.biCompression != 0) {
                
                System.out.println("Unsupported file format.");
                inptr.close();
                outptr.close();
                System.exit(0);
            }
            
            int height = Math.abs(bi.biHeight);
            int width = bi.biWidth;
            
            RgbTriple[][] image = new RgbTriple[height][width];
            
            // Determina el padding de la imagen
            // 3 es el tamaño en bytes de los campos de la clase RgbTriple
            int padding = (4 - (width * 3) % 4) % 4;
            
            System.out.println("\n\n\nPadding: " + padding + "\n\n\n");
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {       
                    byte blue = (byte) inptr.read();
                    byte green = (byte) inptr.read();
                    byte red = (byte) inptr.read();
                    image[i][j] = new RgbTriple(blue, green, red);
                }
                if (padding != 0) {
                    inptr.readNBytes(padding);
                }
            }
            
            switch (filter){
                case 'b':
                    //blur
                    Filters.blur(height, width, image);
                    break;
                case 'e':
                    //edge
                    Filters.edge(height, width, image);
                    break;
                case 'g':
                    //grayscale
                    Filters.grayscale(height, width, image);
                    break;
                case 'r':
                    Filters.reflect(height, width, image);
                    //reflect
                    break;
            }
            
            outptr.write(bf.getBytesInArray());
            outptr.write(bi.getBytesInArray());
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) { 
                    outptr.write(image[i][j].blue);
                    outptr.write(image[i][j].green);
                    outptr.write(image[i][j].red);
                }
                if (padding != 0) {
                    outptr.write(0x00);
                }
            }
            
            inptr.close();
            outptr.close();
            
        } catch (IOException fnfe) {
            System.out.println(fnfe.getMessage());
            System.exit(0);
        } 
    }
    
}
