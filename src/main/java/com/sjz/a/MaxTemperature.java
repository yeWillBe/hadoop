package com.sjz.a;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
 
public class MaxTemperature {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: MaxTemperature <input path> <out path>");
            System.exit(-1);
        }
 
        /* ������ҵ������ָ��hadoop��ȡjar�� */
        Job job = new Job();
        job.setJarByClass(MaxTemperature.class);
        job.setJobName("MaxTemperature");
        
        /* ����input·����output·�� */
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
 
        /* ָ��Ҫʹ�õ�mapper��reducer���� */
        job.setMapperClass(MaxTemperatureMapper.class);
        job.setReducerClass(MaxTemperatureReducer.class);
 
        /* ����reduce������������ͣ�Ҫ��Reduce�����ƥ�� */
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
 
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
