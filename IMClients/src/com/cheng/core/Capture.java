package com.cheng.core;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import com.cheng.domain.MsgType;
import com.cheng.listener.ChatP2PVoiceListener;
import com.cheng.main.App;

public class Capture implements Runnable {
	public boolean stop = false;
	boolean mix = false;// ªÏ“Ù
	int pitch = 0;// ªÏ“Ù
	boolean code = false;// —πÀı

	private TargetDataLine targetDataLine = null;
	AudioFormat audioFormat = null;
	DataLine.Info info = null;
	TargetDataLine targetDataLines = null;

	public Capture() {

		audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 66150F, 16, 2, 4, 66150F, true);

		info = new DataLine.Info(TargetDataLine.class, audioFormat);
		try {
			targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
			targetDataLine.open(audioFormat);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}

	public void change(int i) {

		pitch = i;

	}

	public void addBg(String path) throws IOException {
		Mixer.changer(path);
		mix = true;
	}

	public void removeBg(String path) {
		mix = false;
	}

	public void addCode() {
		code = true;
	}

	public void removeCode() {
		code = false;
	}

	public void run() {
		stop = false;
		mix = false;// ªÏ“Ù
		pitch = 0;// ªÏ“Ù
		code = false;// —πÀı
		play();
	}

	public static void main(String[] args) throws IOException {
		

	}

	public void stopSelf() {
		stop = true;
	}

	public void play() {

		targetDataLine.start();
		Service service = new Service();
		ChatP2PVoiceListener listener = new ChatP2PVoiceListener();
		byte[] data = null;
		while (!stop) {
			data = new byte[12288];
			if (targetDataLine != null) {
				targetDataLine.read(data, 0, 12288);

				if (pitch == 1) {// ±‰µ˜
					data = Pitch.downPitch(data, Pitch.PITCH_SPEED_2);
				} else if (pitch == 2) {
					data = Pitch.downPitch(data, Pitch.PITCH_SPEED_1);
				} else if (pitch == 3) {
					data = Pitch.upPitch(data, Pitch.PITCH_SPEED_1);
				} else if (pitch == 4) {
					data = Pitch.upPitch(data, Pitch.PITCH_SPEED_2);
				}
				if (mix) {// º”±≥æ∞“Ù
					data = Mixer.mix(data);
				}

				if (code) {// —πÀı
					data = ADPCM.adpcm_coder(data, 0, 12288);
					data = service.toProtocal(MsgType.MSG_TYPE_CHAT_P2P_VOICE_CODE, data);
				} else {
					data = service.toProtocal(MsgType.MSG_TYPE_CHAT_P2P_VOICE, data);
				}

				try {
					// System.out.println(data.length + "..........");
					service.send(data);
					data = null;
					// App.conn.writer.write(data);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

	}

	public byte[] toProtocal(byte type, byte[] data) {

		byte[] result = new byte[data.length + 5];
		result[0] = 55;
		result[1] = 10;
		result[2] = 24;
		result[3] = 71;
		result[4] = 98;
		if (type == 73) {
			result[3] = 73;
			result[4] = 108;
		}
		for (int i = 5; i < data.length + 5; i++) {
			result[i] = data[i - 5];
		}

		return result;
	}

}
