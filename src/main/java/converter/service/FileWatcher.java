package converter.service;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileWatcher {
    DataParser parser = new DataParser();



    public FileWatcher () throws Exception {
        FileAlterationObserver observer = new FileAlterationObserver(Settings.getInstance().getPath());
        FileAlterationMonitor monitor = new FileAlterationMonitor();
        FileAlterationListener listener = new FileAlterationListenerAdaptor() {
            @Override
            public void onFileChange(File file) {
                if (Settings.getInstance().getFileName().equals(file.getName())) {
                    parser.parseData(readFileChanges(file));
                }
            }
        };
        observer.addListener(listener);
        monitor.addObserver(observer);
        monitor.start();
    }

    private String readFileChanges (File file) {
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        return stringBuilder.toString();
    }

}
