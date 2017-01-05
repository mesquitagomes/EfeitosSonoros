package br.com.mesquitagomes.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class PlayThread extends Thread {

	private ByteArrayOutputStream byteArrayOutputStream;
	private byte[] tempBuffer;
	private AudioInputStream audioInputStream;
	private SourceDataLine sourceDataLine;

	public PlayThread(ByteArrayOutputStream byteArrayOutputStream, byte[] tempBuffer) {

		this.byteArrayOutputStream = byteArrayOutputStream;
		this.tempBuffer = tempBuffer;
		try {
			byte audioData[] = byteArrayOutputStream.toByteArray();
			InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
			AudioFormat audioFormatplay = CaptureAndPlay.getAudioFormat();
			audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormatplay, audioData.length
					/ audioFormatplay.getFrameSize());
			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormatplay);
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			sourceDataLine.open(audioFormatplay);
			sourceDataLine.start();
		} catch (Exception e) {
			System.out.println("PLAY! " + e);
			System.exit(0);
		}
	}

	public PlayThread(Object byteArrayOutputStream2, byte[] tempBuffer2) {

		// TODO Auto-generated constructor stub
	}

	public void run() {

		try {
			int cnt;
			while ((cnt = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
				if (cnt > 0) {
					sourceDataLine.write(tempBuffer, 0, cnt);
				}
			}
			sourceDataLine.drain();
			sourceDataLine.close();
		} catch (Exception e) {
			System.out.println("RUN PLAY! " + e);
			System.exit(0);
		}
	}

}
