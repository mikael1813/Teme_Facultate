package protobuffprotocol;


import domain.Participant;
import domain.Proba;
import domain.Utilizator;
import rpcprotocol.ChatServicesRpcProxy;
import services.AppException;
import services.IAppObserver;
import services.IService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProtoChatProxy implements IService {
    private String host;
    private int port;

    private IAppObserver client;

    private InputStream input;
    private OutputStream output;
    private Socket connection;

    private BlockingQueue<ChatProtobufs.Response> qresponses;
    private volatile boolean finished;

    public ProtoChatProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<ChatProtobufs.Response>();
    }

    public void login(Utilizator user, IAppObserver client) throws AppException {
        initializeConnection();
        sendRequest(ProtoUtils.createLoginRequest(user));
        ChatProtobufs.Response response = readResponse();
        if (response.getType() == ChatProtobufs.Response.Type.Ok) {
            this.client = client;
            return;
        }
        if (response.getType() == ChatProtobufs.Response.Type.Error) {
            String errorText = ProtoUtils.getError(response);
            closeConnection();
            throw new AppException(errorText);
        }
    }

//    public void sendMessage(Message message) throws ChatException {
//        sendRequest(ProtoUtils.createSendMesssageRequest(message));
//        ChatProtobufs.ChatResponse response=readResponse();
//        if (response.getType()==ChatProtobufs.ChatResponse.Type.Error){
//            String errorText=ProtoUtils.getError(response);
//            throw new ChatException(errorText);
//        }
//    }

    public void logout(Utilizator user, IAppObserver client) throws AppException {
        sendRequest(ProtoUtils.createLogoutRequest(user));
        ChatProtobufs.Response response = readResponse();
        closeConnection();
        if (response.getType() == ChatProtobufs.Response.Type.Error) {
            String errorText = ProtoUtils.getError(response);
            throw new AppException(errorText);
        }
    }

//    public User[] getLoggedFriends(User user) throws ChatException {
//        sendRequest(ProtoUtils.createLoggedFriendsRequest(user));
//        ChatProtobufs.ChatResponse response=readResponse();
//        if (response.getType()==ChatProtobufs.ChatResponse.Type.Error){
//            String errorText=ProtoUtils.getError(response);
//            throw new ChatException(errorText);
//        }
//        User[] friends=ProtoUtils.getFriends(response);
//        return friends;
//    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(ChatProtobufs.Request request) throws AppException {
        try {
            System.out.println("Sending request ..." + request);
            //request.writeTo(output);
            request.writeDelimitedTo(output);
            output.flush();
            System.out.println("Request sent.");
        } catch (IOException e) {
            throw new AppException("Error sending object " + e);
        }

    }

    private ChatProtobufs.Response readResponse() throws AppException {
        ChatProtobufs.Response response = null;
        try {
            response = qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws AppException {
        try {
            connection = new Socket(host, port);
            output = connection.getOutputStream();
            //output.flush();
            input = connection.getInputStream();     //new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(ChatProtobufs.Response updateResponse) {
        switch (updateResponse.getType()) {
            case Get_Nr_Participanti: {
                try {
                    client.newInscriere();
                } catch (AppException e) {
                    e.printStackTrace();
                }
                break;

            }
//              case FriendLoggedIn:{
//                  User friend=ProtoUtils.getUser(updateResponse);
//                  System.out.println("Friend logged in "+friend);
//                  try {
//                      client.friendLoggedIn(friend);
//                  } catch (ChatException e) {
//                      e.printStackTrace();
//                  }
//                  break;
//              }
//              case FriendLoggedOut:{
//                  User friend=ProtoUtils.getUser(updateResponse);
//                  System.out.println("Friend logged out "+friend);
//                  try {
//                      client.friendLoggedOut(friend);
//                  } catch (ChatException e) {
//                      e.printStackTrace();
//                  }
//
//                  break;
//              }
//              case NewMessage:{
//                  Message message=ProtoUtils.getMessage(updateResponse);
//                  try {
//                      client.messageReceived(message);
//                  } catch (ChatException e) {
//                      e.printStackTrace();
//                  }
//                  break;
//              }

        }

    }


    @Override
    public Proba[] getProbe() throws AppException {
        sendRequest(ProtoUtils.createGetProbeRequest());
        ChatProtobufs.Response response = readResponse();
        if (response.getType() == ChatProtobufs.Response.Type.Error) {
            String errorText = ProtoUtils.getError(response);
            throw new AppException(errorText);
        }
        Proba[] friends = ProtoUtils.getProbe(response);
        return friends;
    }

    @Override
    public Participant[] getParticipantiDupaProba(Proba p) throws AppException, AppException {
        sendRequest(ProtoUtils.createGetParticipantiDupaProbaRequest(p));
        ChatProtobufs.Response response = readResponse();
        if (response.getType() == ChatProtobufs.Response.Type.Error) {
            String errorText = ProtoUtils.getError(response);
            throw new AppException(errorText);
        }
        Participant[] friends = ProtoUtils.getParticipanti(response);
        return friends;
    }

    @Override
    public Proba[] getProbeDupaParticipanti(Participant p) throws AppException {
        sendRequest(ProtoUtils.createGetProbeDupaParticipantRequest(p));
        ChatProtobufs.Response response = readResponse();
        if (response.getType() == ChatProtobufs.Response.Type.Error) {
            String errorText = ProtoUtils.getError(response);
            throw new AppException(errorText);
        }
        Proba[] friends = ProtoUtils.getProbeParticipant(response);
        return friends;
    }

    @Override
    public void Inscrie(Participant participant, Proba[] probe) throws AppException {
        sendRequest(ProtoUtils.createInscrieRequest(participant, probe));
        ChatProtobufs.Response response = readResponse();
        if (response.getType() == ChatProtobufs.Response.Type.Error) {
            String errorText = ProtoUtils.getError(response);
            throw new AppException(errorText);
        }

    }

    @Override
    public List<Integer> getNrParticipanti() throws AppException {
        sendRequest(ProtoUtils.createGetNrParticipantiRequest());
        ChatProtobufs.Response response = readResponse();
        if (response.getType() == ChatProtobufs.Response.Type.Error) {
            String errorText = ProtoUtils.getError(response);
            throw new AppException(errorText);
        }
        return ProtoUtils.getNrParticipanti(response);
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    ChatProtobufs.Response response = ChatProtobufs.Response.parseDelimitedFrom(input);
                    System.out.println("response received " + response);

                    if (isUpdateResponse(response.getType())) {
                        Thread thread = new Thread(()->handleUpdate(response));
                        //handleUpdate(response);
                        thread.start();
                    } else {
                        try {
                            qresponses.put(response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }

    private boolean isUpdateResponse(ChatProtobufs.Response.Type type) {
        switch (type) {
            case New_Inscriere:
                return true;
        }
        return false;
    }
}
