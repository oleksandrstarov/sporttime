package converter.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    private Logger logger = Logger.getLogger("MyLog");
    private FileHandler fh;

    private static final Log instance = new Log();

    private Log(){
        try {
            File directory = new File(".");
            // This block configure the logger with handler and formatter
            System.out.println(directory.getPath()+ File.separator + "st-" + Utils.getCurrentTimeStamp() + ".log");
            fh = new FileHandler(directory.getPath()+ File.separator + "st-" + Utils.getCurrentTimeStamp() + ".log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
            logger.info("Logging started");

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Log getInstance(){
        return instance;
    }

    public void log(String message) {
        logger.info(message);
    }

    public void error(String message) {
        logger.warning(message);
    }

    public void error(Exception message) {
        logger.warning(getMessage(message));
    }

    public void error(Error message) {
        logger.warning(getMessage(message));
    }

    private String getMessage(Exception ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    private String getMessage(Error ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
