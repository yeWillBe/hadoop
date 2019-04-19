package com.sjz.a;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/* ���м̳���hadoop��Mapper�� */
/* 4��������Ӧ�����������ֵ������������ֵ��������ǳ�����ƫ�ƣ�����ֵ��һ���ı����������
 ��ݣ����ֵ������ */
/* ʹ�õ����Ͷ���hadoop�����Ͷ�����java���õģ��ʺ��������л����� */
public class MaxTemperatureMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	private static final int MISSING = 999;

	/* ��дmap���� */
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		/* map�����key�ò��ţ�����ֵ���ı����ݣ�ת����һ���ַ� */
		String line = value.toString();
		String year = line.substring(15, 19);
		int airTemperature;
		if (line.charAt(87) == '+') {
			airTemperature = Integer.parseInt(line.substring(88, 92));
		} else {
			airTemperature = Integer.parseInt(line.substring(87, 92));
		}
		String quality = line.substring(92, 93);
		/* ������ɸѡ�ļ�¼д��context */
		if (airTemperature != MISSING && quality.matches("[01459]")) {
			/* ��ݰ���Text��ʽ��������Int���������Ӧ */
			context.write(new Text(year), new IntWritable(airTemperature));
		}
	}
}