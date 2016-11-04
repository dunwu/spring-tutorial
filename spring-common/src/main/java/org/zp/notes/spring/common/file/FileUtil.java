package org.zp.notes.spring.common.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpeng0913 on 2016/10/26.
 */
public class FileUtil {

    static FileFilter filefilter = new FileFilter() {
        public boolean accept(File file) {
            //if the file extension is .txt return true, else false
            if (file.getName().endsWith(".vm")) {
                return true;
            }
            return false;
        }
    };

    /**
     * 非递归方式获取一个目录下所有文件
     *
     * @param path
     * @return
     */
    public static List<String> getFileName(String path) {
        List<String> filelist = new ArrayList<>();
        File f = new File(path);
        if (!f.exists()) {
//            System.out.println(path + " not exists");
            return null;
        }

        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (fs.isDirectory()) {
//                System.out.println(fs.getPath() + " [目录]");
            } else {
//                System.out.println(fs.getPath());
                filelist.add(fs.getPath());
            }
        }

        return filelist;
    }

    public static void getAllFiles(File file) {
        File flist[] = file.listFiles();
        if (flist == null || flist.length == 0) {
            return;
        }
        for (File f : flist) {
            if (f.isDirectory()) {
                //这里将列出所有的文件夹
//                System.out.println("Dir==>" + f.getAbsolutePath());
                getAllFiles(f);
            } else {
                //这里将列出所有的文件
                System.out.println("file==>" + f.getAbsolutePath());
            }
        }
    }

    /**
     * 返回一个目录下的所有文件（包括子目录）
     */
    public static File[] listFiles(File path) {
        List<File> fileList = new ArrayList<File>();

        if (path.isDirectory()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    File[] tmp = listFiles(files[i]);
                    for (int j = 0; j < tmp.length; j++) {
                        fileList.add(tmp[j]);
                    }
                } else {
                    fileList.add(files[i]);
                }
            }
        } else {
            fileList.add(path);
        }

        return fileList.toArray(new File[0]);
    }

    /**
     * 返回一个目录下的所有指定类型文件（包括子目录）
     * @param types 形式为"txt|doc|jpg"，以"|"隔开
     */
    public static File[] listSpecifiedFiles(File path, String types) {
        List<File> fileList = new ArrayList<File>();

        if (path.isDirectory()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    File[] tmp = listFiles(files[i]);
                    for (int j = 0; j < tmp.length; j++) {
                        if (isMatchFile(tmp[j], types)){
                            fileList.add(tmp[j]);
                        }
                    }
                } else {
                    if (isMatchFile(files[i], types)){
                        fileList.add(files[i]);
                    }
                }
            }
        } else {
            if (isMatchFile(path, types)){
                fileList.add(path);
            }
        }

        return fileList.toArray(new File[0]);
    }

    /**
     * 判断文件类型是否符合后缀类型
     * @param file
     * @param types 形式为"txt|doc|jpg"，以"|"隔开
     */
    public static boolean isMatchFile(File file, String types) {
        String[] exts = types.split("|");
        for (String ext : exts) {
            if (file.getName().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 拷贝文件
     */
    public static void copyFile(String src, String dest) throws IOException {
        int BUFF_SIZE = 100000;
        byte[] buffer = new byte[BUFF_SIZE];

        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dest);
            while (true) {
                int amountRead = in.read(buffer);
                if (amountRead == -1) {
                    break;
                }
                out.write(buffer, 0, amountRead);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
