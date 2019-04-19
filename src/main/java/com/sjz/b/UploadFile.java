package com.sjz.b;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

public class UploadFile {
	public static void main(String[] args) {
		try {
			String localSrc = "D://demo.zip";
			String dst = "hdfs://192.168.100.17:8888";
			InputStream in = new BufferedInputStream(new FileInputStream(localSrc));
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(URI.create(dst), conf);
			OutputStream out = fs.create(new Path(dst), new Progressable() {
				public void progress() {
					System.out.print(".");
				}
			});
			IOUtils.copyBytes(in, out, 4096, true);
			System.out.println("success");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
