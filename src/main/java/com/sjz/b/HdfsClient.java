package com.sjz.b;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.Before;
import org.junit.Test;

public class HdfsClient {

    FileSystem fs = null;

    /**
     * ��ʼ��FileSystem
     */
    @Before
    public void init() throws Exception {
        // ����һ�����ò�����������һ������������Ҫ���ʵ�hdfs��URI
        // �Ӷ�FileSystem.get()������֪��Ӧ����ȥ����һ������hdfs�ļ�ϵͳ�Ŀͻ��ˣ��Լ�hdfs�ķ��ʵ�ַ
        // new Configuration();��ʱ�����ͻ�ȥ����jar���е�hdfs-default.xml
        // Ȼ���ټ���classpath�µ�hdfs-site.xml
        Configuration conf = new Configuration();
        //conf.set("fs.defaultFS", "hdfs://hdp-node01:9000");
        /**
         * �������ȼ��� 1���ͻ��˴��������õ�ֵ 2��classpath�µ��û��Զ��������ļ� 3��Ȼ���Ƿ�������Ĭ������
         */
        //conf.set("dfs.replication", "3");

        // ��ȡһ��hdfs�ķ��ʿͻ��ˣ����ݲ��������ʵ��Ӧ����DistributedFileSystem��ʵ��
        // fs = FileSystem.get(conf);

        // �������ȥ��ȡ����conf����Ϳ��Բ�Ҫ��"fs.defaultFS"���������ң�����ͻ��˵���ݱ�ʶ�Ѿ���hadoop�û�
        fs = FileSystem.get(new URI("hdfs://bogon:8888"), conf, "root");
    }

    /**
     * ��hdfs�ϴ��ļ�
     */
    @Test
    public void testAddFileToHdfs() throws Exception {
        // Ҫ�ϴ����ļ����ڵı���·��
        Path src = new Path("d:/demo.zip");
        // Ҫ�ϴ���hdfs��Ŀ��·��
        Path dst = new Path("/");
        fs.copyFromLocalFile(src, dst);

        fs.close();
    }

    /**
     * ��hdfs�и����ļ��������ļ�ϵͳ
     */
    @Test
    public void testDownloadFileToLocal() throws IllegalArgumentException, IOException {
        fs.copyToLocalFile(new Path("/demo2"), new Path("d:/"));
        fs.close();
    }

    /**
     * ��hfds�д���Ŀ¼��ɾ��Ŀ¼��������
     */
    @Test
    public void testMkdirAndDeleteAndRename() throws IllegalArgumentException, IOException {
        // ����Ŀ¼
        fs.mkdirs(new Path("/a1/b1/c1"));

        // ɾ���ļ��� ������Ƿǿ��ļ��У�����2�����ֵtrue
        fs.delete(new Path("/aaa"), true);

        // �������ļ����ļ���
        fs.rename(new Path("/a1"), new Path("/a2"));
    }

    /**
     * �鿴Ŀ¼��Ϣ��ֻ��ʾ�ļ�
     */
    @Test
    public void testListFiles() throws FileNotFoundException, IllegalArgumentException, IOException {
        // ˼����Ϊʲô���ص�������������List֮�������
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);

        while (listFiles.hasNext()) {
            LocatedFileStatus fileStatus = listFiles.next();

            System.out.println(fileStatus.getPath().getName());
            System.out.println(fileStatus.getBlockSize());
            System.out.println(fileStatus.getPermission());
            System.out.println(fileStatus.getLen());
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            for (BlockLocation bl : blockLocations) {
                System.out.println("block-length:" + bl.getLength() + "--" + "block-offset:" + bl.getOffset());
                String[] hosts = bl.getHosts();
                for (String host : hosts) {
                    System.out.println(host);
                }
            }
            System.out.println("--------------�ָ���--------------");
        }
    }

    /**
     * �鿴�ļ����ļ�����Ϣ
     */
    @Test
    public void testListAll() throws FileNotFoundException, IllegalArgumentException, IOException {
        FileStatus[] listStatus = fs.listStatus(new Path("/"));
        String flag = "d--             ";

        for (FileStatus fstatus : listStatus) {
            if (fstatus.isFile())  
                flag = "f--         ";
            System.out.println(flag + fstatus.getPath().getName());
        }
    }
}
