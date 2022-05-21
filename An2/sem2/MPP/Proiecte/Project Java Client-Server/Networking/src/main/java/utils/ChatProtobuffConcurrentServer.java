package utils;


import protobuffprotocol.ProtoChatWorker;
import services.IService;

import java.net.Socket;


public class ChatProtobuffConcurrentServer extends AbsConcurrentServer {
    private IService chatServer;
    public ChatProtobuffConcurrentServer(int port, IService chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatProtobuffConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ProtoChatWorker worker=new ProtoChatWorker(chatServer,client);
        Thread tw=new Thread(worker);
        return tw;
    }
}