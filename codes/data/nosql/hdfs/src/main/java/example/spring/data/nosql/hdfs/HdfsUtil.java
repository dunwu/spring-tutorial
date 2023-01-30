package example.spring.data.nosql.hdfs;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.StreamProgress;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.ContentSummary;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * HDFS 客户端助手
 * @author Zhang Peng
 * @since 2019-03-01
 */
public class HdfsUtil {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private HdfsPool hdfsPool;

    public HdfsUtil(HdfsPool hdfsPool) {
        this.hdfsPool = hdfsPool;
    }

    // ------------------------------------------------------
    // 文件操作
    // ------------------------------------------------------

    /**
     * 上传文件
     * @param sourcePath 原文件路径
     * @param targetPath 目标路径
     * @throws IOException
     */
    public void uploadFile(@NotBlank String sourcePath, @NotBlank String targetPath) throws Exception {
        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            // 调用文件系统的文件复制方法，第一个参数为是否删除原文件（true为删除），默认为 false
            fileSystem.copyFromLocalFile(false, new Path(sourcePath), new Path(targetPath));
        } catch (Exception e) {
            log.error("upload failed", e);
            throw e;
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 上传文件
     * <p>
     * 如果 {@link File}存在，读取文件内容，并上传到 targetPath
     * @param file       原文件
     * @param targetPath 文件路径
     * @throws IOException
     */
    public void uploadFile(@NotNull File file, @NotBlank String targetPath) throws Exception {

        if (file == null || !file.exists()) {
            throw new IOException("file not exists");
        }

        // 从文件中读取二进制数据
        byte[] bytes = IoUtil.readBytes(new FileInputStream(file));

        FileSystem fileSystem = null;
        FSDataOutputStream outputStream = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            outputStream = fileSystem.create(new Path(targetPath));
            outputStream.write(bytes);
            outputStream.flush();
        } finally {
            IoUtil.close(outputStream);
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 上传文件
     * @param inputStream 输入流
     * @param targetPath  文件路径
     * @throws IOException
     */
    public void uploadFile(@NotNull InputStream inputStream, @NotBlank String targetPath) throws Exception {
        // 从输入流中读取二进制数据
        byte[] bytes = IoUtil.readBytes(inputStream);

        FileSystem fileSystem = null;
        FSDataOutputStream outputStream = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            outputStream = fileSystem.create(new Path(targetPath));
            outputStream.write(bytes);
            outputStream.flush();
        } finally {
            IoUtil.close(outputStream);
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 上传大文件
     * @param sourcePath 原文件路径
     * @param targetPath 文件路径
     * @throws IOException
     */
    public void uploadBigFile(@NotBlank String sourcePath, @NotBlank String targetPath) throws Exception {

        File file = new File(sourcePath);
        if (file.exists()) {
            throw new IOException("file not exists");
        }
        final float fileSize = file.length();

        FileSystem fileSystem = null;
        InputStream inputStream = null;
        FSDataOutputStream outputStream = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            inputStream = new BufferedInputStream(new FileInputStream(file));
            outputStream = fileSystem.create(new Path(targetPath),
                new Progressable() {
                    long cnt = 0;

                    @Override
                    public void progress() {
                        cnt++;
                        // progress 方法每上传大约 64KB 的数据后就会被调用一次
                        System.out.println("上传进度：" + (cnt * 64 * 1024 / fileSize) * 100 + " %");
                    }
                });

            IoUtil.copyByNIO(inputStream, outputStream, 4096, new StreamProgress() {
                @Override
                public void start() {
                    log.info("copy start");
                }

                @Override
                public void progress(long progressSize) {
                }

                @Override
                public void finish() {
                    log.info("copy success");
                }
            });
        } finally {
            IoUtil.close(outputStream);
            IoUtil.close(inputStream);
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 下载文件
     * @param sourcePath 原文件路径（HDFS 中的路径）
     * @param targetPath 目标路径
     * @throws Exception
     */
    public void downloadFile(@NotBlank String sourcePath, @NotBlank String targetPath) throws Exception {
        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            // 调用文件系统的文件复制方法，第一个参数为是否删除原文件（true为删除），默认为 false
            fileSystem.copyToLocalFile(false, new Path(sourcePath), new Path(targetPath));
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 复制文件
     * @param sourcePath 原文件路径
     * @param targetPath 目标文件路径
     * @throws Exception
     */
    public void copyFile(@NotBlank String sourcePath, @NotBlank String targetPath, int buffSize) throws Exception {
        FileSystem fileSystem = null;
        FSDataInputStream inputStream = null;
        FSDataOutputStream outputStream = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            inputStream = fileSystem.open(new Path(sourcePath));
            outputStream = fileSystem.create(new Path(targetPath));
            if (buffSize <= 0) {
                int DEFAULT_BUFFER_SIZE = 1024 * 1024 * 64;
                buffSize = DEFAULT_BUFFER_SIZE;
            }
            IOUtils.copyBytes(inputStream, outputStream, buffSize, false);
        } finally {
            IoUtil.close(outputStream);
            IoUtil.close(inputStream);
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 删除文件
     * @param path
     * @return true / false
     * @throws Exception
     */
    public boolean deleteOnExit(@NotBlank String path) throws Exception {
        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            return fileSystem.deleteOnExit(new Path(path));
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 读取文件内容
     * @param path 文件路径
     * @return
     * @throws Exception
     */
    public String readFile(@NotBlank String path) throws Exception {
        if (!exists(path)) {
            throw new IOException(path + " not exists in hdfs");
        }

        // 目标路径
        Path sourcePath = new Path(path);
        BufferedReader reader = null;
        FSDataInputStream inputStream = null;
        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            inputStream = fileSystem.open(sourcePath);
            // 防止中文乱码
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String lineTxt = "";
            StringBuffer sb = new StringBuffer();
            while ((lineTxt = reader.readLine()) != null) {
                sb.append(lineTxt);
            }
            return sb.toString();
        } finally {
            IoUtil.close(reader);
            IoUtil.close(inputStream);
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    public ContentSummary getContentSummary(String path) throws Exception {
        if (!exists(path)) {
            throw new IOException(path + " not exists in hdfs");
        }

        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            return fileSystem.getContentSummary(new Path(path));
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 获取某个文件在 HDFS 的集群位置
     * @param path
     * @return
     * @throws Exception
     */
    public BlockLocation[] getFileBlockLocations(@NotBlank String path) throws Exception {
        if (!exists(path)) {
            throw new IOException(path + " not exists in hdfs");
        }

        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            FileStatus fileStatus = fileSystem.getFileStatus(new Path(path));
            return fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 重命名文件
     * @param oldName 旧路径名
     * @param newName 新路径名
     * @return true / false
     * @throws IOException
     */
    public boolean rename(@NotBlank String oldName, @NotBlank String newName) throws Exception {
        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            return fileSystem.rename(new Path(oldName), new Path(newName));
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 创建文件夹
     * @param path 文件夹路径
     * @return true / false
     * @throws IOException
     */
    public boolean mkdirs(@NotBlank String path) throws Exception {
        if (exists(path)) {
            return true;
        }

        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            return fileSystem.mkdirs(new Path(path));
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 创建文件夹，并为其设置访问权限
     * @param path       文件夹路径
     * @param permission 权限
     * @return true / false
     * @throws Exception
     */
    public boolean mkdirsWithPermission(@NotBlank String path, FsPermission permission) throws Exception {
        if (exists(path)) {
            return true;
        }

        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            fileSystem.mkdirs(new Path(path), permission);
            return fileSystem.mkdirs(new Path(path));
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 读取目录信息
     * @param path HDFS 目录路径
     * @return {@link FileStatus[]}
     * @throws Exception
     */
    public FileStatus[] listStatus(@NotBlank String path) throws Exception {
        if (!exists(path)) {
            throw new IOException(path + " not exists in hdfs");
        }
        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            // 强制递归删除，使得无论文件或文件夹都会被彻底删除
            return fileSystem.listStatus(new Path(path));
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 读取文件列表
     * @param path
     * @return
     * @throws Exception
     */
    public List<LocatedFileStatus> listFiles(@NotBlank String path) throws Exception {
        if (!exists(path)) {
            throw new IOException(path + " not exists in hdfs");
        }

        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            // 递归找到所有文件
            RemoteIterator<LocatedFileStatus> filesList = fileSystem.listFiles(new Path(path), true);
            List<LocatedFileStatus> returnList = new ArrayList<>();
            while (filesList.hasNext()) {
                LocatedFileStatus next = filesList.next();
                returnList.add(next);
            }
            return returnList;
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 删除文件或文件夹
     * @param path 文件夹路径
     * @return true / false
     * @throws Exception
     */
    public boolean delete(@NotBlank String path) throws Exception {
        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            // 强制递归删除，使得无论文件或文件夹都会被彻底删除
            return fileSystem.delete(new Path(path), true);
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 判断文件或文件夹是否存在
     * @param path 路径
     * @return true / false
     * @throws Exception
     */
    public boolean exists(@NotBlank String path) throws Exception {
        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            return fileSystem.exists(new Path(path));
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

}
