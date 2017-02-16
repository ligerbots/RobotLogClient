/**
 * 
 */
package org.ligerbots;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.log4j.XMLLayout;
import ch.qos.logback.classic.net.SocketReceiver;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Erik Uhlmann
 *
 */
public class RobotLogClient {
  private static final String ROBOT_IP = "10.28.77.2";

  static {
    ch.qos.logback.classic.Logger root =
        (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    root.setLevel(Level.DEBUG);
    LoggerContext rootCtx = (LoggerContext) LoggerFactory.getILoggerFactory();

//    // disable TRACE level for console output
//    LevelFilter levelFilter = new LevelFilter();
//    levelFilter.setContext(rootCtx);
//    levelFilter.setLevel(Level.TRACE);
//    levelFilter.setOnMatch(FilterReply.DENY);
//    levelFilter.setOnMismatch(FilterReply.ACCEPT);
//    levelFilter.start();
//    // find the console appender and apply filter
//    Iterator<Appender<ILoggingEvent>> apps = root.iteratorForAppenders();
//    while (apps.hasNext()) {
//      apps.next().addFilter(levelFilter);
//    }
    
    FileAppender<ILoggingEvent> fileAppender = new FileAppender<>();
    fileAppender.setAppend(true);
    fileAppender.setContext(rootCtx);
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    fileAppender.setFile("robot-" + dateFormat.format(date) + ".xml");
    LayoutWrappingEncoder<ILoggingEvent> enc = new LayoutWrappingEncoder<>();
    enc.setContext(rootCtx);
    XMLLayout layout = new XMLLayout();
    layout.setContext(rootCtx);
    layout.setLocationInfo(true);
    layout.start();
    enc.setLayout(layout);
    enc.start();
    fileAppender.setEncoder(enc);
    fileAppender.start();
    root.addAppender(fileAppender);
    
    SocketReceiver recv = new SocketReceiver();
    recv.setContext(rootCtx);
    recv.setRemoteHost(ROBOT_IP);
    recv.setPort(5801);
    recv.setReconnectionDelay(1000);
    recv.start();
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("RobotLogClient start");
    while(true) {
      try {
        Thread.sleep(Long.MAX_VALUE);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
