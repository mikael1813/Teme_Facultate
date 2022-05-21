using AppNetworking.DTO;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppNetworking
{
	public interface Request
	{
	}


	[Serializable]
	public class LoginRequest : Request
	{
		private UtilizatorDTO user;

		public LoginRequest(UtilizatorDTO user)
		{
			this.user = user;
		}

		public virtual UtilizatorDTO User
		{
			get
			{
				return user;
			}
		}
	}

	[Serializable]
	public class LogoutRequest : Request
	{
		private UtilizatorDTO user;

		public LogoutRequest(UtilizatorDTO user)
		{
			this.user = user;
		}

		public virtual UtilizatorDTO User
		{
			get
			{
				return user;
			}
		}
	}

	[Serializable]
	public class getProbeRequest : Request
	{

		public getProbeRequest()
		{
			
		}

	}

	[Serializable]
	public class getParticipantiDupaProbaRequest : Request
	{
		private ProbaDTO proba;

		public getParticipantiDupaProbaRequest(ProbaDTO proba)
		{
			this.proba = proba;
		}

		public virtual ProbaDTO Proba
		{
			get
			{
				return proba;
			}
		}
	}

	[Serializable]
	public class getProbeDupaParticipantRequest : Request
	{
		private ParticipantDTO proba;

		public getProbeDupaParticipantRequest(ParticipantDTO proba)
		{
			this.proba = proba;
		}

		public virtual ParticipantDTO Proba
		{
			get
			{
				return proba;
			}
		}
	}

	[Serializable]
	public class InscrieRequest : Request
	{
		private ParticipantDTO user;
		private List<ProbaDTO> probaDTOs;

		public InscrieRequest(ParticipantDTO user, List<ProbaDTO> probaDTOs)
		{
			this.user = user;
			this.probaDTOs = probaDTOs;
		}

		public virtual ParticipantDTO User
		{
			get
			{
				return user;
			}
		}

		public virtual List<ProbaDTO> List
		{
			get
			{
				return probaDTOs;
			}
		}
	}

	[Serializable]
	public class getNrParticipantiRequest : Request
	{

		public getNrParticipantiRequest()
		{

		}

	}

	//[Serializable]
	//public class SendMessageRequest : Request
	//{
	//	private MessageDTO message;

	//	public SendMessageRequest(MessageDTO message)
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

	//[Serializable]
	//public class GetLoggedFriendsRequest : Request
	//{
	//	private UserDTO user;

	//	public GetLoggedFriendsRequest(UserDTO user)
	//	{
	//		this.user = user;
	//	}

	//	public virtual UserDTO User
	//	{
	//		get
	//		{
	//			return user;
	//		}
	//	}
	//}
}
