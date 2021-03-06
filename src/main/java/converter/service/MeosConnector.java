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

import java.io.*;
import java.net.Socket;
import java.util.*;


public class MeosConnector {
    public int port;

    public MeosConnector(int portNumber) {
        this.port = portNumber;
    }

    public static void sendData(SICard punch) {
        try {
            Socket socket = new Socket("localhost", Settings.getInstance().getPort());
            OutputStream socketOutputStream = socket.getOutputStream();

            byte[] buffer = punch.serialize();
            socketOutputStream.write(buffer, 0, buffer.length);
            socketOutputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.getInstance().error(e);
        }  catch (Error e) {
            e.printStackTrace();
            Log.getInstance().error(e);
        }
    }
}
