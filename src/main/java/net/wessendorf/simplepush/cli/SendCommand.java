/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.wessendorf.simplepush.cli;

import io.airlift.command.Command;
import io.airlift.command.Option;
import net.wessendorf.simplepush.SimplePushClient;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

@Command(name = "send", description = "Submit SimplePush message")
public class SendCommand implements Runnable{

    private static final Logger logger = Logger.getLogger(SimplePushClient.class.getName());

    @Option(name = {"-U", "--url"}, required = true, title = "updateURL", description = "Update URL of the SimplePush Server")
    public String updateURL = null;

    @Option(name = {"-V", "--version"}, title = "versionString", description = "Version string - if not provided current timestamp is used")
    public String versionString = null;


    @Override
    public void run() {
        final SimplePushClient simplePushClient = new SimplePushClient();

        // set current timestamp, if needed
        if (versionString == null) {
            versionString = "version="+System.currentTimeMillis();
        }

        try {
            simplePushClient.put(new URL(updateURL), versionString);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not parse URL");
        }
    }

}