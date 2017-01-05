package br.com.mesquitagomes.effects;

public class Overdrive {

	private int threshold;
	private int gain;
	private int drive;
	private int level;

	public int getSamples(short buffer[], int length) { // amostra do sinal e seu tamanho.

		int len = length;
		threshold = 6500 / drive; // limiar de satura��o
		gain = (drive * level) / 100; // ganho = (satura��o * volume)

		if (getByPass()) return len;

		for (int i = 0; i < len; i++) {

			int sample = ((int) (buffer[i])); // diminui o ru�do limitando a mostra ao limiar de satura��o

			if (sample > threshold) sample = threshold;
			else if (sample < -threshold) sample = -threshold;

			buffer[i] = (short) (int) ((double) sample * gain); // adiciona a satura��o ao sinal
		}

		return len;

	}

	private boolean getByPass() {

		// TODO Auto-generated method stub
		return false;
	}

}
