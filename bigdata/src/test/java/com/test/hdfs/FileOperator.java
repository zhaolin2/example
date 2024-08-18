//package com.test.hdfs;
//
//import org.apache.commons.compress.utils.IOUtils;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.*;
//import org.apache.hadoop.fs.permission.FsPermission;
//import org.apache.hadoop.hdfs.DistributedFileSystem;
//import org.junit.Before;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.net.URI;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * Created by Administrator on 2017/12/3.
// */
//public class FileOperator {
//    private static final Logger logger = LoggerFactory.getLogger(FileOperator.class);
//
//    public static DistributedFileSystem dfs=null;
//    public static String nameNodeUri="hdfs://localhost:9010";
////    public static String nameNodeUri="hdfs://namenode.docker-hadoop-spark.orb.local";
//
//
//    @Before
//    public void initFileSystem() throws Exception{
//        logger.info("initial hadoop env----");
//        dfs=new DistributedFileSystem();
//        dfs.initialize(new URI(nameNodeUri), new Configuration());
//        logger.info("connection is successful");
//        Path workingDirectory = dfs.getWorkingDirectory();
//        System.out.println("current workspace is ："+workingDirectory);
//    }
//    /**
//     * 创建文件夹
//     * @throws Exception
//     */
//    @Test
//    public void testMkDir() throws Exception{
//        boolean res = dfs.mkdirs(new Path("/test/aaa/bbb"));
//        System.out.println("目录创建结果："+(res?"创建成功":"创建失败"));
//    }
//    /**
//     * 删除目录/文件
//     * @throws Exception
//     */
//    @Test
//    public void testDeleteDir() throws Exception{
//        boolean deleteRes = dfs.delete(new Path("/test/aaa/bbb"), false);
//        System.out.println("删除结果,deleteRes："+deleteRes);
//
//    }
//
//    /**
//     * 获取指定目录下所有文件(忽略目录)
//     * @throws Exception
//     * @throws IllegalArgumentException
//     */
//    @Test
//    public void testFileList() throws Exception{
//        RemoteIterator<LocatedFileStatus> listFiles = dfs.listFiles(new Path("/"), true);
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        while (listFiles.hasNext()) {
//            LocatedFileStatus fileStatus = (LocatedFileStatus) listFiles.next();
//            //权限
//            FsPermission permission = fileStatus.getPermission();
//            //拥有者
//            String owner = fileStatus.getOwner();
//            //组
//            String group = fileStatus.getGroup();
//            //文件大小byte
//            long len = fileStatus.getLen();
//            long modificationTime = fileStatus.getModificationTime();
//            Path path = fileStatus.getPath();
//            System.out.println("-------------------------------");
//            System.out.println("permission:"+permission);
//            System.out.println("owner:"+owner);
//            System.out.println("group:"+group);
//            System.out.println("len:"+len);
//            System.out.println("modificationTime:"+sdf.format(new Date(modificationTime)));
//            System.out.println("path:"+path);
//        }
//    }
//    /**
//     * 【完整】文件上传
//     * 注意：文件上传在Window开发环境下，使用apache-common提供的<code>org.apache.commons.io.IOUtils.copy</code>可能存在问题
//     */
//    @Test
//    public void testUploadFullFile() throws Exception{
//        FSDataOutputStream out = dfs.create(new Path("/test/aaa/testFile.txt"), true);
//        InputStream in = new FileInputStream("/Users/zhaolin/lua/test.lua");
//        IOUtils.copy(in, out);
//        System.out.println("上传完毕");
//    }
//
//
//    /**
//     * 【分段|部分】文件上传
//     * 注意：文件上传在Window开发环境下，使用apache-common提供的<code>org.apache.commons.io.IOUtils.copy</code>可能存在问题
//     */
//    @Test
//    public void testUploadFile2() throws Exception{
//        FSDataOutputStream out = dfs.create(new Path("/test/aaa/testFile1.txt"), true);
//        InputStream in = new FileInputStream("/Users/zhaolin/lua/test.lua");
//        org.apache.commons.io.IOUtils.copyLarge(in, out, 6, 12);
//        System.out.println("上传完毕");
//    }
//    /**
//     * 【完整】下载文件
//     * 注意：windows开发平台下，使用如下API
//     */
//    @Test
//    public void testDownloadFile() throws Exception{
//        //使用Java API进行I/O,设置useRawLocalFileSystem=true
//        dfs.copyToLocalFile(false,new Path("/test/aaa/testFile.txt"),
//                new Path("E:/"),true);
//        System.out.println("下载完成");
//    }
//
//    /**
//     * 【部分】下载文件
//     */
//    @Test
//    public void testDownloadFile2() throws Exception{
//        //使用Java API进行I/O,设置useRawLocalFileSystem=true
//        FSDataInputStream src = dfs.open(new Path("/test/aaa/testFile.txt"));
//        FileOutputStream des = new FileOutputStream(new File("E:/","download_testFile.txt"));
//        src.seek(6);
//        org.apache.commons.io.IOUtils.copy(src, des);
//        System.out.println("下载完成");
//    }
//}
