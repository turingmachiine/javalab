package itis.javalab.hateoas.util;

import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.io.IOException;

public class JaveConverter {

    public static File convertToMp3(File source) {
        String path = source.getAbsolutePath();
        String[] temp = path.split("\\.");
        File result = new File(temp[0] + ".mp3");

        //Audio Attributes
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setBitRate(128000);
        audio.setChannels(2);
        audio.setSamplingRate(44100);

        //Encoding attributes
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat("mp3");
        attrs.setAudioAttributes(audio);

        //Encode
        Encoder encoder = new Encoder();
        try {
            encoder.encode(new MultimediaObject(source), result, attrs);
            result.createNewFile();
            return result;
        } catch (EncoderException | IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
