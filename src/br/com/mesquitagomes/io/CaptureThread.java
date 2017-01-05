package br.com.mesquitagomes.io;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

public class CaptureThread {

	private byte[] tempBuffer;
	private AudioFormat audioFormat;
	private Object targetDataLine;

	public CaptureThread(byte[] tempBuffer) {

		this.tempBuffer = tempBuffer;

		try {
			Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
			System.out.println("Available mixers:");
			for (int cnt = 0; cnt < mixerInfo.length; cnt++) {
				System.out.println(mixerInfo[cnt].getName());
			}
			audioFormat = CaptureAndPlay.getAudioFormat();
			DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
			Mixer mixer = AudioSystem.getMixer(mixerInfo[3]);
			targetDataLine = (TargetDataLine) mixer.getLine(dataLineInfo);
		} catch (Exception e) {
			System.out.println("PRE CAPTURE! " + e);
			System.exit(0);
		}
	}

	public void capture() {

		// TODO Auto-generated method stub

	}

	public Object getByteArrayOutputStream() {

		// TODO Auto-generated method stub
		return null;
	}
}
