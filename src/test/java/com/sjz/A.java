package com.sjz;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.junit.Test;

public class A {
	 private static final String platformName = System.getProperty("os.name") + "-" + System.getProperty("os.arch") + "-" + System.getProperty("sun.arch.data.model");

	    public static final String JAVA_VENDOR_NAME = System.getProperty("java.vendor");

	    public static final boolean IBM_JAVA = JAVA_VENDOR_NAME.contains("IBM");

	    public static String getPlatformName() {
	        return platformName;
	    }

	    public static void main(String[] args) {
	        System.out.println(platformName);
	        System.out.println(IBM_JAVA);
	    }
}
