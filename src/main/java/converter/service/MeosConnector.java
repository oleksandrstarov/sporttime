/*
  Copyright 2014 Melin Software HB
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
      http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
package converter.service;

import converter.model.SICard;
import converter.model.SIPunch;
import converter.model.SpecialPunch;

import java.io.*;
import java.net.Socket;
import java.util.*;


public class MeosConnector {
  public static void main(String[] args) throws Throwable {
    System.out.println("Send MeOS punch or card."); // Display the string.
    System.out.print("Enter 'P' to send a punch, 'C' to send a card: ");
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    String line = readCommand(br, "P", "C");
    
    SIPunch punch = null;
    
    if (line.equalsIgnoreCase("P")) {
      punch = new SIPunch();
      System.out.print("Punch, enter control code (2 for Finish):");
      punch.codeNumber = Short.parseShort(readCommand(br));
      
      System.out.print("Enter card number:");
      punch.SICardNo = Integer.parseInt(readCommand(br));
      
      System.out.print("Enter punch time (HH:MM:SS):");
      punch.codeTime = readTime(br);
    }
    else {
      SICard card = new SICard();
      punch = card;
      System.out.print("Card, enter card number:");
      punch.SICardNo = Integer.parseInt(readCommand(br));
      
      int time;
      System.out.print("Enter start time (HH:MM:SS):");
      time = readTime(br);
      if (time > 0)
        card.addPunch(SpecialPunch.PunchStart.code, time);
      
      int code = 1;
      while (code != 0) {
        System.out.print("Enter punch code (empty to stop):");
        String codeS = readCommand(br);
        code = 0;
        if (!codeS.isEmpty()) {
          code = Integer.parseInt(codeS);
          System.out.print("Enter punch code (HH:MM:SS):");
          time = readTime(br);
          card.addPunch(code, time);
        }
      }
      
      System.out.print("Enter finish time (HH:MM:SS):");
      time = readTime(br);
      if (time > 0)
        card.addPunch(SpecialPunch.PunchFinish.code, time);
    }

    Socket socket = new Socket("localhost", 10000);
    OutputStream socketOutputStream = socket.getOutputStream();
    
    byte[] buffer = punch.serialize();
    socketOutputStream.write(buffer, 0, buffer.length);
    socketOutputStream.close();
    socket.close();
  }

  private static int readTime(BufferedReader br) throws IOException {
    String[] hms = readCommand(br).split(":");
    int time = 0;
    for (String p:hms)
       time = 60 * time + Integer.parseInt(p);
    return time * 10;
  }

  private static String readCommand(BufferedReader br, String... valid) throws IOException {
    String line = null;
    while ((line = br.readLine()) != null) {
      if (valid.length == 0 || Arrays.asList(valid).contains(line.toUpperCase(Locale.ENGLISH))) {
        break;
      }
    }
    return line;
  }
}
