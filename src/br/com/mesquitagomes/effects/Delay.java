package br.com.mesquitagomes.effects;

public class Delay {

	private boolean initializationComplete;
	private int readIndex;
	private int writeIndex;
	private int dryLevel;
	private int wetLevel;
	private int[] delayBuffer;
	private int delayBufferSize;
	private int feedbackLevel;

	public int getSamples(short buffer[], int length) {

		if (!getByPass()) {
			if (initializationComplete)
			;
		}

		int len = length;

		for (int i = 0; i < len; i++) {

			int inputSample = (int) buffer[i]; // amostra do sinal original
			int delaySample = (int) delayBuffer[readIndex++]; // adiciona o sinal original e o sinal atrasado à saída
			int outputSample = (inputSample * dryLevel) / 100 + (delaySample * wetLevel) / 100; // diminui a saturação da amostra de saída

			if (outputSample > 30000) outputSample = 30000;
			else if (outputSample < -30000) outputSample = -30000;

			buffer[i] = (short) outputSample; // concatena ao sinal original de entrada o sinal atrasado com o tempo de duração
			inputSample += (delaySample * feedbackLevel) / 100; // diminui a saturação da amostra de entrada

			if (inputSample > 30000) inputSample = 30000;
			else if (inputSample < -30000) inputSample = -30000;

			delayBuffer[writeIndex++] = (short) inputSample; // insere o sinal de entrada no buffer
			readIndex %= delayBufferSize; // especifica a posição do array de leitura do sinal
			writeIndex %= delayBufferSize; // especifica a posição do array de escrita do sinal
		}

		return len;

	}

	public boolean doInitialization() {

		// TODO Auto-generated method stub
		return false;
	}

	public boolean getByPass() {

		// TODO Auto-generated method stub
		return false;
	}
}
