package filter;


import java.io.FileInputStream;
import java.io.IOException;

public class BitMapFileHeader {
    
    private final byte[] header;
    final short bfType;
    final long bfSize;
    final short bfReserved1;
    final short bfReserved2;
    final int bfOffBits;
    
    public BitMapFileHeader(FileInputStream inptr) throws IOException {
            
        header = inptr.readNBytes(14);
        
        for (byte by : header) {
            System.out.println(by);
        }
        System.out.println("-------------------------------");
        
            
        bfType = (short) ((header[1] << 8) | header[0]);

        bfSize = (((header[5] & 0x000000ff) << 24) |
                ((header[4] & 0x000000ff) << 16) |
                ((header[3] & 0x000000ff) << 8) |
                (header[2] & 0x000000ff));
        
        bfReserved1 = (short) ((header[7] << 8) | header[6]);

        bfReserved2 = (short) ((header[9] << 8) | header[8]);

        bfOffBits = (((header[13] & 0x000000ff) << 24) |
                ((header[12] & 0x000000ff) << 16) |
                ((header[11] & 0x000000ff) << 8) |
                (header[10] & 0x000000ff));   
    }
    public byte[] getBytesInArray() {
        return header;
    }
}
