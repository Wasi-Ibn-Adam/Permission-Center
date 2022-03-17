package com.wasitech.permissioncenter.java.com.wasitech.assist.activators;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.musicg.api.WhistleApi;
import com.musicg.wave.WaveHeader;

import java.util.LinkedList;

import com.wasitech.basics.classes.Issue;

public class Activator  extends Thread {
    private final RecorderThread recorder;
    private final WhistleApi whistleApi;
    private Thread thread;
    private final LinkedList<Boolean> whistleResults = new LinkedList<>();
    private int numWhistles;


    private Whistler whistler;

    public Activator(RecorderThread recorder){
        this.recorder = recorder;
        AudioRecord audioRecord = recorder.getAudioRecord();

        int bitsPerSample = 0;
        if (audioRecord.getAudioFormat() == AudioFormat.ENCODING_PCM_16BIT) {
            bitsPerSample = 16;
        } else if (audioRecord.getAudioFormat() == AudioFormat.ENCODING_PCM_8BIT) {
            bitsPerSample = 8;
        }

        int channel = 0;
        // whistle detection only supports mono channel
        if (audioRecord.getChannelConfiguration() == AudioFormat.CHANNEL_IN_MONO) {
            channel = 1;
        }

        WaveHeader waveHeader = new WaveHeader();
        waveHeader.setChannels(channel);
        waveHeader.setBitsPerSample(bitsPerSample);
        waveHeader.setSampleRate(audioRecord.getSampleRate());
        whistleApi = new WhistleApi(waveHeader);
    }
    private void initBuffer() {
        numWhistles = 0;
        whistleResults.clear();

        // init the first frames
        int whistleCheckLength = 3;
        for (int i = 0; i < whistleCheckLength; i++) {
            whistleResults.add(false);
        }
        // end init the first frames
    }
    public void start() {
        thread = new Thread(this);
        thread.start();
    }
    public void stopDetection(){
        thread = null;
    }
    public void run() {
        try {
            byte[] buffer;
            initBuffer();

            Thread thisThread = Thread.currentThread();
            while (thread == thisThread) {
                // detect sound
                buffer = recorder.getFrameBytes();

                // audio analyst
                if (buffer != null) {
                    // sound detected
                    // whistle detection
                    //System.out.println("*Whistle:");
                    boolean isWhistle = whistleApi.isWhistle(buffer);
                    if (whistleResults.getFirst()) {
                        numWhistles--;
                    }

                    whistleResults.removeFirst();
                    whistleResults.add(isWhistle);

                    if (isWhistle) {
                        numWhistles++;
                    }
                    //System.out.println("num:" + numWhistles);

                    int whistlePassScore = 3;
                    if (numWhistles >= whistlePassScore) {
                        // clear buffer
                        initBuffer();
                        onWhistleDetected();
                    }
                    // end whistle detection
                }
                else{
                    // no sound detected
                    if (whistleResults.getFirst()) {
                        numWhistles--;
                    }
                    whistleResults.removeFirst();
                    whistleResults.add(false);
                }
                // end audio analyst
            }
        } catch (Exception e) {
            Issue.print(e, Activator.class.getName());
        }
    }
    private void onWhistleDetected(){
        if (whistler != null){
            whistler.onWhistleDetected();
        }
    }
    public void setWhistler(Whistler listener){
        whistler = listener;
    }


    public static class RecorderThread extends Thread {

        private final AudioRecord audioRecord;
        private boolean isRecording;
        private final int frameByteSize = 2048; // for 1024 fft size (16bit sample size)
        byte[] buffer;

        public RecorderThread(){
            int channelConfiguration = AudioFormat.CHANNEL_IN_MONO;
            int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
            int sampleRate = 44100;
            int recBufSize = AudioRecord.getMinBufferSize(sampleRate, channelConfiguration, audioEncoding); // need to be larger than size of a frame
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfiguration, audioEncoding, recBufSize);
            buffer = new byte[frameByteSize];
        }
        public AudioRecord getAudioRecord(){
            return audioRecord;
        }

        public boolean isRecording(){
            return this.isAlive() && isRecording;
        }

        public void startRecording(){
            try{
                audioRecord.startRecording();
                isRecording = true;
            } catch (Exception e) {
                Issue.print(e, Activator.class.getName());
            }
        }

        public void stopRecording(){
            try{
                audioRecord.stop();
                audioRecord.release();
                isRecording = false;
            } catch (Exception e) {
                Issue.print(e, Activator.class.getName());
            }
        }

        public byte[] getFrameBytes(){
            audioRecord.read(buffer, 0, frameByteSize);

            // analyze sound
            int totalAbsValue = 0;
            short sample;
            float averageAbsValue;

            for (int i = 0; i < frameByteSize; i += 2) {
                sample = (short)((buffer[i]) | buffer[i + 1] << 8);
                totalAbsValue += Math.abs(sample);
            }
            averageAbsValue =(float) totalAbsValue / frameByteSize / 2;

            //System.out.println(averageAbsValue);

            // no input
            if (averageAbsValue < 30){
                return null;
            }

            return buffer;
        }

        public void run() {
            startRecording();
        }
    }

}
