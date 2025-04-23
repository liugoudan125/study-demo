package com.beiming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;

/**
 * AudioUtils
 */
public class AudioUtils {

    private static final Logger log = LoggerFactory.getLogger(AudioUtils.class);

    /**
     * 音频格式处理(输出为mp3格式)
     *
     * @param source 源音频文件
     * @param target 输出的音频文件
     */
    public static void toMp3(File source, File target) {
        try {
            //Audio Attributes
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(16000);
            audio.setChannels(1);
            audio.setSamplingRate(16000);
            //Encoding attributes
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat("mp3");
            attrs.setAudioAttributes(audio);
            //Encode
            Encoder encoder = new Encoder();
            MultimediaObject multimediaObject = new MultimediaObject(source);
            encoder.encode(multimediaObject, target, attrs);
        } catch (Exception ex) {
            log.error("MP3格式转化错误", ex);
            throw new RuntimeException(ex);
        }
    }
}
