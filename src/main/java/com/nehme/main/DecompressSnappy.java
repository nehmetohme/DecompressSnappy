package com.nehme.main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import org.xerial.snappy.SnappyInputStream;
import org.xerial.snappy.SnappyOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class DecompressSnappy {
  private static final int FILE_BUFFER_SIZE = 64 * 1024;
  public static void main(String[] args) {

String compress = "compress";
if (compress.equals(args[1])){

    try {
      FileInputStream fis = new FileInputStream(new File(args[2]));
      FileChannel channel = fis.getChannel();
      ByteBuffer bb = ByteBuffer.allocate((int) channel.size());
      channel.read(bb);
      byte[] beforeBytes = bb.array();
      
      OutputStream os = new SnappyOutputStream(new FileOutputStream(args[3]));
      os.write(beforeBytes);
      os.close();
      System.out.println(args[2] + " has been compressed");
    }
    catch(Exception e) {
      e.getStackTrace();
    }
}

String uncompress = "uncompress";
if (uncompress.equals(args[1])){
    try {
      FileInputStream compressed_fis = new FileInputStream(new File(args[2]));
      SnappyInputStream sis = new SnappyInputStream(compressed_fis);
      FileOutputStream out = new FileOutputStream(new File(args[3]));

      byte[] buf = new byte[FILE_BUFFER_SIZE];
      while (true) {
        int read = sis.read(buf);
        if (read == -1) {
          break;
        }
        out.write(buf, 0, read);
      }
      out.getFD().sync();
      System.out.println(args[2] + " has been uncompressed");

    }
    catch(Exception e) {
      e.getStackTrace();
    }
}
  }
}
