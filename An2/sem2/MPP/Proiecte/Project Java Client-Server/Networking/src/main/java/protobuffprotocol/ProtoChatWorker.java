package protobuffprotocol;


import domain.Utilizator;
import services.AppException;
import services.IAppObserver;
import services.IService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;


public class ProtoChatWorker implements Runnable, IAppObserver {
    private IService server;
     private Socket connection;

     private InputStream input;
     private OutputStream output;
     private volatile boolean connected;
     public ProtoChatWorker(IService server, Socket connection) {
         this.server = server;
         this.connection = connection;
         try{
             output=connection.getOutputStream() ;//new ObjectOutputStream(connection.getOutputStream());
             input=connection.getInputStream(); //new ObjectInputStream(connection.getInputStream());
             connected=true;
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     public void run() {
         while(connected){
             try {
                // Object request=input.readObject();
                 System.out.println("Waiting requests ...");
                 ChatProtobufs.Request request=ChatProtobufs.Request.parseDelimitedFrom(input);
                 System.out.println("Request received: "+request);
                 ChatProtobufs.Response response=handleRequest(request);
                 if (response!=null){
                    sendResponse(response);
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
             try {
                 Thread.sleep(1000);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
         try {
             input.close();
             output.close();
             connection.close();
         } catch (IOException e) {
             System.out.println("Error "+e);
         }
     }

//     public void messageReceived(Message message) throws ChatException {
//         System.out.println("Message received  "+message);
//         try {
//             sendResponse(ProtoUtils.createNewMessageResponse(message));
//         } catch (IOException e) {
//             throw new ChatException("Sending error: "+e);
//         }
//     }
//
//     public void friendLoggedIn(User friend) throws ChatException {
//         System.out.println("Friend logged in "+friend);
//         try {
//             sendResponse(ProtoUtils.createFriendLoggedInResponse(friend));
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
//
//     public void friendLoggedOut(User friend) throws ChatException {
//         System.out.println("Friend logged out "+friend);
//         try {
//             sendResponse(ProtoUtils.createFriendLoggedOutResponse(friend));
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

     private ChatProtobufs.Response handleRequest(ChatProtobufs.Request request){
         ChatProtobufs.Response response=null;
         switch (request.getType()){
             case Login:{
                 System.out.println("Login request ...");
                 Utilizator user=ProtoUtils.getUser(request);
                 try {
                     server.login(user, this);
                     return ProtoUtils.createOkResponse();
                 } catch (AppException e) {
                     connected=false;
                     return ProtoUtils.createErrorResponse(e.getMessage());
                 }
             }
             case Logout:{
                 System.out.println("Logout request");
                 Utilizator user=ProtoUtils.getUser(request);
                 try {
                     server.logout(user, this);
                     connected=false;
                     return ProtoUtils.createOkResponse();

                 } catch (AppException e) {
                     return ProtoUtils.createErrorResponse(e.getMessage());
                 }
             }
//             case SendMessage:{
//                 System.out.println("SendMessageRequest ...");
//                 Message message=ProtoUtils.getMessage(request);
//                 try {
//                     server.sendMessage(message);
//                     return ProtoUtils.createOkResponse();
//                 } catch (ChatException e) {
//                     return ProtoUtils.createErrorResponse(e.getMessage());
//                 }
//             }
//             case GetLoggedFriends:{
//                 System.out.println("GetLoggedFriends Request ...");
//                 User user=ProtoUtils.getUser(request);
//                 try {
//                     User[] friends=server.getLoggedFriends(user);
//                     return ProtoUtils.createLoggedFriendsResponse(friends);
//                 } catch (ChatException e) {
//                     return ProtoUtils.createErrorResponse(e.getMessage());
//                 }
//             }
         }
         return response;
     }

     private void sendResponse(ChatProtobufs.Response response) throws IOException{
         System.out.println("sending response "+response);
         response.writeDelimitedTo(output);
         //output.writeObject(response);
         output.flush();
     }

    @Override
    public void newInscriere() throws AppException {

    }
}
