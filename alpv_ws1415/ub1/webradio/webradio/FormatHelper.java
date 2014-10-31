package alpv_ws1415.ub1.webradio.webradio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.nio.*;


public class FormatHelper {
  
  public static int byteSize = 4 * 5;
  
  public static byte[] serialize(AudioFormat format) {    
    return ByteBuffer.allocate(byteSize)
      // .putInt(format.getFrameSize())
      // .putFloat(format.getFrameRate())
      .putFloat(format.getSampleRate()) // sample rate
      .putInt(format.getSampleSizeInBits()) // sample size
      .putInt(format.getChannels()) // channels
      .putInt(format.isBigEndian() ? 1 : 0) // bigendian
      .putInt(format.getEncoding() == AudioFormat.Encoding.PCM_SIGNED ? 1 : 0) // signed
      .array();
  }
  
  public static AudioFormat parse(byte[] raw) {
    ByteBuffer buffer = ByteBuffer.allocate(byteSize);
    buffer.put(raw);
    
    float sampleRate = buffer.getFloat(0 * 4);
    int sampleSize = buffer.getInt(1 * 4);
    int channels = buffer.getInt(2 * 4);
    boolean signed = buffer.getInt(3 * 4) == 0;
    boolean bigEndian = buffer.getInt(4 * 4) == 0;
    
    return new AudioFormat(sampleRate, sampleSize, channels, signed, bigEndian);
  }
}