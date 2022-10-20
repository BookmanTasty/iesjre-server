package com.leyvaddev;

import com.guichaguri.minimalftp.FTPConnection;
import com.guichaguri.minimalftp.FTPServer;
import com.guichaguri.minimalftp.api.IFTPListener;
import com.leyvaddev.custom.CommandHandler;
import com.leyvaddev.custom.UserbaseAuthenticator;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author Guilherme Chaguri
 */
public class CustomServer implements IFTPListener {

    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(CustomServer.class);

    public static void main(String[] args) throws IOException {
        logger.info("Starting server");
        // Create the FTP server
        FTPServer server = new FTPServer();
        // Create our custom authenticator
        UserbaseAuthenticator auth = new UserbaseAuthenticator();
        // Set our custom authenticator
        server.setAuthenticator(auth);
        // Register an instance of this class as a listener
        server.addListener(new CustomServer());
        // Changes the timeout to 10 minutes
        server.setTimeout(10 * 60 * 1000); // 10 minutes
        // Changes the buffer size
        server.setBufferSize(1024 * 5); // 5 kilobytes
        // Start it synchronously in our localhost and in the port 21
        logger.info("Server started");
        server.listenSync(InetAddress.getByName("localhost"), 21);

    }
    @Override
    public void onConnected(FTPConnection con) {
        // Creates our command handler
        CommandHandler handler = new CommandHandler(con);
        // Register our custom command
        con.registerCommand("CUSTOM", "CUSTOM <string>", handler::customCommand);
    }
    @Override
    public void onDisconnected(FTPConnection con) {
        // You can use this event to dispose resources related to the connection
        // As the instance of CommandHandler is only held by the command, it will
        // be automatically disposed by the JVM
    }
}
