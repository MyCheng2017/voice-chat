package com.cheng.listener;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import com.cheng.core.ADPCM;
import com.cheng.core.MessageSender;
import com.cheng.domain.MsgType;
import com.cheng.domain.Request;
import com.cheng.domain.RequestType;
import com.google.gson.Gson;

public class ChatP2PVoiceListener extends MessageSender implements OnRecevieListener {

	AudioFormat audioFormat = null;
	DataLine.Info info = null;
	static SourceDataLine sourceDataLine = null;

	public ChatP2PVoiceListener() {
		audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 66150F, 16, 2, 4, 66150F, true);
		info = new DataLine.Info(SourceDataLine.class, audioFormat);
		try {
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		try {
			sourceDataLine.open(audioFormat);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		sourceDataLine.start();
	}

	public static void start() {
		if (sourceDataLine != null) {
			sourceDataLine.start();
		}

	}

	public void onReceive(byte[] data) {
		if (checkByte(data)) {
			if (data[3] == MsgType.MSG_TYPE_CHAT_P2P_VOICE) {
				sourceDataLine.write(getByte(data), 0, 12288);
			} else if (data[3] == MsgType.MSG_TYPE_CHAT_P2P_VOICE_CODE) {
				System.out.println(getByte(data).length);
				byte[] bs = ADPCM.adpcm_decoder(data,5, 3077);
				 
				sourceDataLine.write(bs, 0, 12288);
			} else if (data[3] == MsgType.MSG_TYPE_REQUEST) {
				String json = new String(data, 5, data[1] * 100 + data[2]);
				Gson gson = new Gson();
				Request request = gson.fromJson(json, Request.class);
				if (request.type == RequestType.REQUEST_P2P_VOICE_START) {
					try {
						sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
						sourceDataLine.open(audioFormat);
						sourceDataLine.start();

					} catch (LineUnavailableException e) {
						e.printStackTrace();
					}

				} else if (request.type == RequestType.REQUEST_P2P_VOICE_STOP) {
					if (sourceDataLine != null) {
						sourceDataLine.drain();
						sourceDataLine.stop();
						sourceDataLine.close();
					}
					sourceDataLine = null;
				}
			}

		}

	}

	public static void stop() {
		if (sourceDataLine != null) {
			// sourceDataLine.drain();
			sourceDataLine.stop();
			sourceDataLine.close();
		}
	}

	public void name(byte[] b, int len) {

		sourceDataLine.write(b, 0, len);

	}

	public byte[] getByte(byte[] b) {
		byte[] bb = new byte[b.length - 5];
		for (int i = 5; i < b.length; i++) {
			bb[i - 5] = b[i];
		}
		return bb;

	}
}
