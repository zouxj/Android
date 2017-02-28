package com.example.dell.musicplayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 描述：
 * <p>歌词解析
 * Created by zxj on 2017-1-6下午15:02:32
 * <p>
 */
public class LyricsParser {
	/**
	 * 从歌词文件解析出歌词数据列表
	 */
	public static ArrayList<LyricBean> parserFromFile(InputStream stream) {
		ArrayList<LyricBean> lyricsList = new ArrayList<LyricBean>();
		// 数据可用性检查
//		if (lyricsFile == null || !lyricsFile.exists()) {
//			lyricsList.add(new LyricBean(0, "没有找到歌词文件。"));
//			return lyricsList;
//		}
		// 按行解析歌词
		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new InputStreamReader(stream, "GB2312"));
			String line = buffer.readLine();
			while (line != null) {
				List<LyricBean> lineList = parserLine(line);
				lyricsList.addAll(lineList);
				line = buffer.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (buffer != null) {
				try {
					buffer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Collections.sort(lyricsList);// 排序
		return lyricsList;
	}

	/** 解析一行歌词 [01:45.51][02:58.62]整理好心情再出发 */
	private static List<LyricBean> parserLine(String line) {
		List<LyricBean> lineList = new ArrayList<LyricBean>();
		String[] arr = line.split("]");
		// [01:45.51 [02:58.62 整理好心情再出发
		String content = arr[arr.length - 1];
		// [01:45.51 [02:58.62
		for (int i = 0; i < arr.length - 1; i++) {
			int startPoint = parserStartPoint(arr[i]);
			lineList.add(new LyricBean(startPoint, content));
		}
		return lineList;
	}

	/** 解析出一行歌词的起始时间 [01:45.51 */
	private static int parserStartPoint(String startPoint) {
		int time = 0;
		String[] arr = startPoint.split(":");
		// [01 45.51
		String minStr = arr[0].substring(1);
		// 45.51
		arr = arr[1].split("\\.");
		// 45 51
		String secStr = arr[0];
		String mSecStr = arr[1];
		time = parseInt(minStr) * 60 * 1000 + parseInt(secStr) * 1000 + parseInt(mSecStr) * 10;
		return time;
	}

	private static int parseInt(String str) {
		return Integer.parseInt(str);
	}
}
