//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.statistics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileStatistics {
    private static Map<Integer, FileInfo> fileInfo_cache = new HashMap();
    private static ReadWriteLock statisticsLock = new ReentrantReadWriteLock();

    public FileStatistics() {
    }

    public static Set<Integer> getAllKey() {
        return fileInfo_cache.keySet();
    }

    public static void update(int id, int size) {
        statisticsLock.writeLock().lock();

        try {
            FileInfo file;
            if (fileInfo_cache.containsKey(id)) {
                file = (FileInfo)fileInfo_cache.get(id);
                file.updateLength(size);
            } else {
                file = new FileInfo();
                file.setFileId(id);
                file.setRecordTime(System.currentTimeMillis());
                file.updateLength(size);
                fileInfo_cache.put(id, file);
            }
        } finally {
            statisticsLock.writeLock().unlock();
        }

    }

    public static void calcTransmissionRate() {
        statisticsLock.writeLock().lock();

        try {
            Iterator var0 = fileInfo_cache.entrySet().iterator();

            while(var0.hasNext()) {
                Entry<Integer, FileInfo> item = (Entry)var0.next();
                FileInfo file = (FileInfo)item.getValue();
                file.calcTransmissionRate();
            }
        } finally {
            statisticsLock.writeLock().unlock();
        }

    }

    public static void remove(int id) {
        statisticsLock.writeLock().lock();

        try {
            fileInfo_cache.remove(id);
        } finally {
            statisticsLock.writeLock().unlock();
        }

    }

    public static FileInfo getFileInfo(int id) {
        FileInfo file = (FileInfo)fileInfo_cache.get(id);
        return file == null ? new FileInfo() : file;
    }
}
