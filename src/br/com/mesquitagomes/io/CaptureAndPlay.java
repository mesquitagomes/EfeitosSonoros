package br.com.mesquitagomes.io;

import javax.sound.sampled.AudioFormat;

public class CaptureAndPlay extends Thread {

	private byte[] tempBuffer;
	private CaptureThread ct;
	private Object byteArrayOutputStream;
	private boolean stopCapture;

	public CaptureAndPlay() {

		ct = new CaptureThread(tempBuffer);
	}

	public void run() {

		try {
			while (!stopCapture) {
				ct.capture();
				byteArrayOutputStream = ct.getByteArrayOutputStream();
				PlayThread playThread = new PlayThread(byteArrayOutputStream, tempBuffer);
				playThread.start();
			}
		} catch (Exception e) {
			System.out.println("CaptureAndPlay! " + e);
			System.exit(0);
		}
	}

	public static AudioFormat getAudioFormat() {

		// TODO Auto-generated method stub
		return null;
	}
}
