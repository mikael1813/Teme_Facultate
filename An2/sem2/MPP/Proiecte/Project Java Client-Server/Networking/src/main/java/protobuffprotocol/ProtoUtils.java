package protobuffprotocol;

import domain.Participant;
import domain.Proba;
import domain.Utilizator;
import domain.enums.Stil;

import java.util.ArrayList;
import java.util.List;

public class ProtoUtils {
    public static ChatProtobufs.Request createLoginRequest(Utilizator user) {
        ChatProtobufs.Utilizator userDTO = ChatProtobufs.Utilizator.newBuilder().setUser(user.getUser()).setParola(user.getParola()).build();
        ChatProtobufs.Request request = ChatProtobufs.Request.newBuilder().setType(ChatProtobufs.Request.Type.Login)
                .setUser(userDTO).build();
        return request;
    }

    public static ChatProtobufs.Request createLogoutRequest(Utilizator user) {
        ChatProtobufs.Utilizator userDTO = ChatProtobufs.Utilizator.newBuilder().setUser(user.getUser()).build();
        ChatProtobufs.Request request = ChatProtobufs.Request.newBuilder().setType(ChatProtobufs.Request.Type.Logout)
                .setUser(userDTO).build();
        return request;
    }

    public static ChatProtobufs.Request createGetProbeRequest() {
        ChatProtobufs.Request request = ChatProtobufs.Request.newBuilder().setType(ChatProtobufs.Request.Type.Get_Probe)
                .build();
        return request;
    }

    public static ChatProtobufs.Request createGetParticipantiDupaProbaRequest(Proba proba) {
        ChatProtobufs.Proba probaDTO = ChatProtobufs.Proba.newBuilder().setDistanta(proba.getDistanta()).setId(proba.getId()).setStil(proba.getStil().toString()).build();
        ChatProtobufs.Request request = ChatProtobufs.Request.newBuilder().setType(ChatProtobufs.Request.Type.Get_Participanti_Dupa_Probe)
                .setProba(probaDTO).build();
        return request;
    }

    public static ChatProtobufs.Request createGetProbeDupaParticipantRequest(Participant participant) {
        ChatProtobufs.Participant participantDTO = ChatProtobufs.Participant.newBuilder().setId(participant.getId()).setNume(participant.getNume()).setVarsta(participant.getVarsta()).build();
        ChatProtobufs.Request request = ChatProtobufs.Request.newBuilder().setType(ChatProtobufs.Request.Type.Get_Probe_Dupa_Participant)
                .setParticipant(participantDTO).build();
        return request;
    }

    public static ChatProtobufs.Request createInscrieRequest(Participant participant, Proba[] probe) {
        ChatProtobufs.Participant participantDTO = ChatProtobufs.Participant.newBuilder().setNume(participant.getNume()).setVarsta(participant.getVarsta()).build();
        ChatProtobufs.Request.Builder request = ChatProtobufs.Request.newBuilder().setType(ChatProtobufs.Request.Type.Inscrie);
        request.setParticipnatNou(participantDTO);
        for(int i=0;i<probe.length;i++){
            ChatProtobufs.Proba probaDTO = ChatProtobufs.Proba.newBuilder().setDistanta(probe[i].getDistanta()).setId(probe[i].getId()).setStil(probe[i].getStil().toString()).build();
            request.addListProbe(probaDTO);
        }
        //ChatProtobufs.Request request = ChatProtobufs.Request.newBuilder().setType(ChatProtobufs.Request.Type.Inscrie)
        //        .setParticipnatNou(participantDTO).setListProbe(listDTO).build();
        return request.build();
    }

    public static ChatProtobufs.Request createGetNrParticipantiRequest() {
        ChatProtobufs.Request request = ChatProtobufs.Request.newBuilder().setType(ChatProtobufs.Request.Type.Get_Nr_Participanti)
                .build();
        return request;
    }

//    public static ChatProtobufs.Request createSendMesssageRequest(Message message){
//        ChatProtobufs.Message messageDTO=ChatProtobufs.Message.newBuilder().
//                setSenderId(message.getSender().getId())
//                .setReceiverId(message.getReceiver().getId())
//                .setText(message.getText()).build();
//        ChatProtobufs.ChatRequest request= ChatProtobufs.ChatRequest.newBuilder()
//                .setType(ChatProtobufs.ChatRequest.Type.SendMessage)
//                .setMessage(messageDTO).build();
//        return request;
//    }
//
//    public static ChatProtobufs.Request createLoggedFriendsRequest(User user){
//        ChatProtobufs.User userDTO=ChatProtobufs.User.newBuilder().setId(user.getId()).build();
//        ChatProtobufs.ChatRequest request= ChatProtobufs.ChatRequest.newBuilder()
//                .setType(ChatProtobufs.ChatRequest.Type.GetLoggedFriends)
//                .setUser(userDTO).build();
//        return request;
//    }


    public static ChatProtobufs.Response createOkResponse() {
        ChatProtobufs.Response response = ChatProtobufs.Response.newBuilder()
                .setType(ChatProtobufs.Response.Type.Ok).build();
        return response;
    }

    public static ChatProtobufs.Response createErrorResponse(String text) {
        ChatProtobufs.Response response = ChatProtobufs.Response.newBuilder()
                .setType(ChatProtobufs.Response.Type.Error)
                .setError(text).build();
        return response;
    }

    public static ChatProtobufs.Response createGetProbeResponse(Proba[] probas) {

        ChatProtobufs.Response.Builder response = ChatProtobufs.Response.newBuilder()
                .setType(ChatProtobufs.Response.Type.Get_Probe);
        for (Proba proba : probas) {
            ChatProtobufs.Proba probaDTO = ChatProtobufs.Proba.newBuilder().setId(proba.getId()).setDistanta(proba.getDistanta()).setStil(proba.getStil().toString()).build();
            response.addProbe(probaDTO);
        }

        return response.build();
    }

    public static ChatProtobufs.Response createGetParticipnatiDupaProbaResponse(Participant[] participants) {
        ChatProtobufs.Response.Builder response = ChatProtobufs.Response.newBuilder()
                .setType(ChatProtobufs.Response.Type.Get_Participanti_Dupa_Probe);
        for (Participant participant : participants) {
            ChatProtobufs.Participant participantDTO = ChatProtobufs.Participant.newBuilder().setId(participant.getId()).setVarsta(participant.getVarsta()).setNume(participant.getNume()).build();
            response.addParticipanti(participantDTO);
        }

        return response.build();
    }

    public static ChatProtobufs.Response createGetProbeDupaParticipantResponse(Proba[] probas) {
        ChatProtobufs.Response.Builder response = ChatProtobufs.Response.newBuilder()
                .setType(ChatProtobufs.Response.Type.Get_Probe);
        for (Proba proba : probas) {
            ChatProtobufs.Proba probaDTO = ChatProtobufs.Proba.newBuilder().setId(proba.getId()).setDistanta(proba.getDistanta()).setStil(proba.getStil().toString()).build();
            response.addProbe(probaDTO);
        }

        return response.build();
    }

//    public static ChatProtobufs.Response createFriendLoggedInResponse(User user){
//        ChatProtobufs.Utilizator userDTO=ChatProtobufs.Utilizator.newBuilder().setId(user.getId()).build();
//
//        ChatProtobufs.Response response=ChatProtobufs.Response.newBuilder()
//                .setType(ChatProtobufs.Response.Type.FriendLoggedIn)
//                .setUser(userDTO).build();
//        return response;
//    }
//
//    public static ChatProtobufs.Response createFriendLoggedOutResponse(User user){
//        ChatProtobufs.User userDTO=ChatProtobufs.User.newBuilder().setId(user.getId()).build();
//
//        ChatProtobufs.ChatResponse response=ChatProtobufs.ChatResponse.newBuilder()
//                .setType(ChatProtobufs.ChatResponse.Type.FriendLoggedOut)
//                .setUser(userDTO).build();
//        return response;
//    }
//    public static ChatProtobufs.Response createNewMessageResponse(Message message){
//        ChatProtobufs.Message messageDTO=ChatProtobufs.Message.newBuilder()
//                .setSenderId(message.getSender().getId())
//                .setReceiverId(message.getReceiver().getId())
//                .setText(message.getText())
//                .build();
//
//        ChatProtobufs.Response response=ChatProtobufs.ChatResponse.newBuilder()
//                .setType(ChatProtobufs.ChatResponse.Type.NewMessage)
//                .setMessage(messageDTO).build();
//        return response;
//    }
//
//    public static ChatProtobufs.Response createLoggedFriendsResponse(User[] users){
//        ChatProtobufs.ChatResponse.Builder response=ChatProtobufs.ChatResponse.newBuilder()
//                .setType(ChatProtobufs.ChatResponse.Type.GetLoggedFriends);
//        for (User user: users){
//            ChatProtobufs.User userDTO=ChatProtobufs.User.newBuilder().setId(user.getId()).build();
//            response.addFriends(userDTO);
//        }
//
//        return response.build();
//    }

    public static String getError(ChatProtobufs.Response response) {
        String errorMessage = response.getError();
        return errorMessage;
    }

    public static Proba[] getProbe(ChatProtobufs.Response response) {
        Proba[] friends = new Proba[response.getProbeCount()];
        for (int i = 0; i < response.getProbeCount(); i++) {
            ChatProtobufs.Proba userDTO = response.getProbe(i);
            Proba user = new Proba();
            user.setId(userDTO.getId());
            user.setDistanta(userDTO.getDistanta());
            user.setStil(Stil.valueOf(userDTO.getStil()));
            friends[i] = user;
        }
        return friends;
    }

    public static Participant[] getParticipanti(ChatProtobufs.Response response) {
        Participant[] friends = new Participant[response.getParticipantiCount()];
        for (int i = 0; i < response.getParticipantiCount(); i++) {
            ChatProtobufs.Participant userDTO = response.getParticipanti(i);
            Participant user = new Participant();
            user.setId(userDTO.getId());
            user.setNume(userDTO.getNume());
            user.setVarsta(userDTO.getVarsta());
            friends[i] = user;
        }
        return friends;
    }

    public static Proba[] getProbeParticipant(ChatProtobufs.Response response) {
        Proba[] friends = new Proba[response.getProbeParticipantCount()];
        for (int i = 0; i < response.getProbeParticipantCount(); i++) {
            ChatProtobufs.Proba userDTO = response.getProbeParticipant(i);
            Proba user = new Proba();
            user.setId(userDTO.getId());
            user.setDistanta(userDTO.getDistanta());
            user.setStil(Stil.valueOf(userDTO.getStil()));
            friends[i] = user;
        }
        return friends;
    }

    public static Utilizator getUser(ChatProtobufs.Request request) {
        Utilizator user = new Utilizator();
        user.setId(request.getUser().getId());
        user.setUser(request.getUser().getUser());
        user.setParola(request.getUser().getParola());
        return user;
    }

    public static List<Integer> getNrParticipanti(ChatProtobufs.Response response) {
        return response.getNrParticipnatiList();
    }

//    public static User getUser(ChatProtobufs.Response response){
//        User user=new User();
//        user.setId(response.getUser().getId());
//        return user;
//    }
//
//    public static Message getMessage(ChatProtobufs.ChatResponse response){
//        User sender=new User();
//        sender.setId(response.getMessage().getSenderId());
//        User receiver=new User();
//        receiver.setId(response.getMessage().getReceiverId());
//        Message message=new Message(sender,response.getMessage().getText(), receiver);
//        return message;
//    }
//
//    public static User[] getFriends(ChatProtobufs.ChatResponse response){
//        User[] friends=new User[response.getFriendsCount()];
//        for(int i=0;i<response.getFriendsCount();i++){
//            ChatProtobufs.User userDTO=response.getFriends(i);
//            User user=new User();
//            user.setId(userDTO.getId());
//            friends[i]=user;
//        }
//        return friends;
//    }
//    public static User getUser(ChatProtobufs.ChatRequest request){
//        User user=new User();
//        user.setId(request.getUser().getId());
//        user.setPasswd(request.getUser().getPasswd());
//        return user;
//    }
//
//    public static Message getMessage(ChatProtobufs.ChatRequest request){
//        User sender=new User();
//        sender.setId(request.getMessage().getSenderId());
//        User receiver=new User();
//        receiver.setId(request.getMessage().getReceiverId());
//        Message message=new Message(sender,request.getMessage().getText(), receiver);
//        return message;
//    }

}
