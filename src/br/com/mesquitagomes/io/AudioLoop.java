package br.com.mesquitagomes.io;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import br.com.mesquitagomes.effects.Delay;
import br.com.mesquitagomes.effects.Overdrive;

public class AudioLoop {

	private Overdrive overdrive;
	private Delay delay;
	private TargetDataLine m_targetLine;
	private SourceDataLine m_sourceLine;
	private int m_nExternalBufferSize;
	private boolean initializationComplete;
	private boolean m_bRecording;
	private Thread clockThread;

	public AudioLoop(AudioFormat format, int nInternalBufferSize, int nExternalBufferSize, String strMixerName)
			throws LineUnavailableException {

		overdrive = new Overdrive();
		delay = new Delay();
		Mixer mixer = null;

		if (strMixerName != null) {
			Mixer.Info mixerInfo = AudioCommon.getMixerInfo(strMixerName);
			mixer = AudioSystem.getMixer(mixerInfo);
		} else {
			AudioCommon.listMixers(false); // list targets
			AudioCommon.listMixers(true); // list sources
		}

		DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, format, nInternalBufferSize);
		DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, format, nInternalBufferSize);

		if (mixer != null) {
			m_targetLine = (TargetDataLine) mixer.getLine(targetInfo);
			m_sourceLine = (SourceDataLine) mixer.getLine(sourceInfo);
		} else {
			m_targetLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
			m_sourceLine = (SourceDataLine) AudioSystem.getLine(sourceInfo);
		}

		m_targetLine.open(format, nInternalBufferSize);
		m_sourceLine.open(format, nInternalBufferSize);
		m_nExternalBufferSize = nExternalBufferSize;

	}

	public void run() {

		if (initializationComplete = delay.doInitialization()) System.out.println("Initialization Complete: " + initializationComplete);

		for (Thread myThread = Thread.currentThread(); clockThread == myThread;) {

			byte abBuffer[] = new byte[m_nExternalBufferSize];
			int nBufferSize = abBuffer.length;
			m_bRecording = true;

			while (m_bRecording) {

				int nBytesRead = m_targetLine.read(abBuffer, 0, nBufferSize);
				short buffer[] = byte2short(abBuffer);
				int len = buffer.length;
				int test = overdrive.getSamples(buffer, len);

				if (!delay.getByPass()) test = delay.getSamples(buffer, len);

				m_sourceLine.write(short2byte(buffer), 0, nBytesRead);
			}
		}
	}

	private byte[] short2byte(short[] buffer) {

		// TODO Auto-generated method stub
		return null;
	}

	private short[] byte2short(byte[] abBuffer) {

		// TODO Auto-generated method stub
		return null;
	}

}
