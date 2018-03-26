package converter.service;

public class Settings {
    private static final Settings instance = new Settings();

    private String port;
    private String path;
    private String fileName;

    //private constructor to avoid client applications to use constructor
    private Settings(){}

    public static Settings getInstance(){
        return instance;
    }

    public Integer getPort() {
        return Integer.parseInt(port);
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

