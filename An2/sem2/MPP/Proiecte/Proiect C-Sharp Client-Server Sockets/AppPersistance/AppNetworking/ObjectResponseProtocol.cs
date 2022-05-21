using AppModel;
using AppNetworking.DTO;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppNetworking
{
	public interface Response
	{
	}

	[Serializable]
	public class OkResponse : Response
	{

	}

	[Serializable]
	public class ErrorResponse : Response
	{
		private string message;

		public ErrorResponse(string message)
		{
			this.message = message;
		}

		public virtual string Message
		{
			get
			{
				return message;
			}
		}
	}

    [Serializable]
    public class GetProbeResponse : Response
    {
        private IEnumerable<ProbaDTO> friends;

        public GetProbeResponse(IEnumerable<ProbaDTO> friends)
        {
            this.friends = friends;
        }

        public virtual IEnumerable<ProbaDTO> Friends
        {
            get
            {
                return friends;
            }
        }
    }

	[Serializable]
	public class InscrieResponse : Response
	{
		

		public InscrieResponse()
		{
			
		}
	}

	[Serializable]
	public class GetProbeDupaParticipantResponse : Response
	{
		private IEnumerable<ProbaDTO> friends;

		public GetProbeDupaParticipantResponse(IEnumerable<ProbaDTO> friends)
		{
			this.friends = friends;
		}

		public virtual IEnumerable<ProbaDTO> Friends
		{
			get
			{
				return friends;
			}
		}
	}

	[Serializable]
	public class GetParticipantiDupaProbaResponse : Response
	{
		private IEnumerable<ParticipantDTO> friends;

		public GetParticipantiDupaProbaResponse(IEnumerable<ParticipantDTO> friends)
		{
			this.friends = friends;
		}

		public virtual IEnumerable<ParticipantDTO> Friends
		{
			get
			{
				return friends;
			}
		}
	}

	[Serializable]
	public class GetNrParticipantiResponse : Response
	{
		private List<int> friends;

		public GetNrParticipantiResponse(List<int> friends)
		{
			this.friends = friends;
		}

		public virtual List<int> Friends
		{
			get
			{
				return friends;
			}
		}
	}

	//[Serializable]
	//public class GetLoggedFriendsResponse : Response
	//{
	//	private UserDTO[] friends;

	//	public GetLoggedFriendsResponse(UserDTO[] friends)
	//	{
	//		this.friends = friends;
	//	}

	//	public virtual UserDTO[] Friends
	//	{
	//		get
	//		{
	//			return friends;
	//		}
	//	}
	//}
	public interface UpdateResponse : Response
	{
	}

    [Serializable]
    public class NewInscriereResponse : UpdateResponse
    {
		private List<ProbacuParticipant> list;


		public NewInscriereResponse(List<ProbacuParticipant> list)
        {
			this.list = list;
        }

		public virtual List<ProbacuParticipant> Friends
		{
			get
			{
				return list;
			}
		}

	}

    //[Serializable]
    //public class FriendLoggedInResponse : UpdateResponse
    //{
    //	private UserDTO friend;

    //	public FriendLoggedInResponse(UserDTO friend)
    //	{
    //		this.friend = friend;
    //	}

    //	public virtual UserDTO Friend
    //	{
    //		get
    //		{
    //			return friend;
    //		}
    //	}
    //}

    //[Serializable]
    //public class FriendLoggedOutResponse : UpdateResponse
    //{
    //	private UserDTO friend;

    //	public FriendLoggedOutResponse(UserDTO friend)
    //	{
    //		this.friend = friend;
    //	}

    //	public virtual UserDTO Friend
    //	{
    //		get
    //		{
    //			return friend;
    //		}
    //	}
    //}


    //[Serializable]
    //public class NewMessageResponse : UpdateResponse
    //{
    //	private MessageDTO message;

    //	public NewMessageResponse(MessageDTO message)
    //	{
    //		this.message = message;
    //	}

    //	public virtual MessageDTO Message
    //	{
    //		get
    //		{
    //			return message;
    //		}
    //	}
    //}
}
