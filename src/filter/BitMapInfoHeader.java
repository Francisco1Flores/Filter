package filter;

import java.io.FileInputStream;
import java.io.IOException;


public class BitMapInfoHeader {
    
    private final byte[] header = new byte[40];
    final int biSize;
    final int biWidth;
    final int biHeight;
    final short biPlanes;
    final short biBitCount;
    final int biCompression;
    final int biSizeImage;
    final int biXPelsPerMeter;
    final int biYPelsPerMeter;
    final int biClrUsed;
    final int biClrImportant;
    
    public BitMapInfoHeader(FileInputStream inptr) throws IOException {
        
            inptr.read(header);
            for (byte by : header) {
                System.out.println(by);
            }
            System.out.println("-------------------------------");
            
            biSize = (header[3] << 24) |
                    (header[2] << 16) |
                    (header[1] << 8) |
                    header[0];
            
            biWidth = ((header[7] & 0x000000ff) << 24) |
                    ((header[6]& 0x000000ff) << 16) |
                    ((header[5]& 0x000000ff) << 8) |
                    (header[4] & 0x000000ff);
            
            biHeight = ((header[11] & 0x000000ff) << 24) |
                    ((header[10]& 0x000000ff) << 16) |
                    ((header[9]& 0x000000ff) << 8) |
                    (header[8] & 0x000000ff);
            
            biPlanes = (short) ((header[12] << 8) | header[13]);
            
            biBitCount = (short) ((header[15] << 8) | header[14]);
            
            biCompression = ((header[19] & 0x000000ff) << 24) |
                    ((header[18] & 0x000000ff) << 16) |
                    ((header[17] & 0x000000ff) << 8) |
                    (header[16] & 0x000000ff);
            
            biSizeImage = ((header[23] & 0x000000ff) << 24) |
                    ((header[22] & 0x000000ff) << 16) |
                    ((header[21] & 0x000000ff) << 8) |
                    (header[20] & 0x000000ff);
            
            biXPelsPerMeter = ((header[27] & 0x000000ff) << 24) |
                    ((header[26] & 0x000000ff) << 16) |
                    ((header[25] & 0x000000ff) << 8) |
                    (header[24] & 0x000000ff);
            
            biYPelsPerMeter = ((header[31] & 0x000000ff) << 24) |
                    ((header[30] & 0x000000ff) << 16) |
                    ((header[29] & 0x000000ff) << 8) |
                    (header[28] & 0x000000ff);
            
            biClrUsed = ((header[35] & 0x000000ff) << 24) |
                    ((header[34] & 0x000000ff) << 16) |
                    ((header[33] & 0x000000ff) << 8) |
                    (header[32] & 0x000000ff);
            
            biClrImportant = ((header[39] & 0x000000ff) << 24) |
                    ((header[38] & 0x000000ff) << 16) |
                    ((header[37] & 0x000000ff) << 8) |
                    (header[36] & 0x000000ff);
    }
    
    public byte[] getBytesInArray() {
        return header;
    }
}
