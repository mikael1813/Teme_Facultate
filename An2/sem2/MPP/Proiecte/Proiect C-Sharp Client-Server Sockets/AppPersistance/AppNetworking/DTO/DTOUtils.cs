using AppModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppNetworking.DTO
{
	public class DTOUtils
    {
		public static Utilizator getFromDTO(UtilizatorDTO usdto)
		{
			string id = usdto.user;
			string pass = usdto.pass;
			return new Utilizator(id, pass);

		}
		public static UtilizatorDTO getDTO(Utilizator user)
		{
			string id = user.user;
			string pass = user.parola;
			return new UtilizatorDTO(id, pass);
		}

		public static Participant getFromDTO(ParticipantDTO usdto)
		{
			string id = usdto.nume;
			int pass = usdto.varsta;
			Participant p = new Participant(pass, id);
			p.id = usdto.id;
			return p;

		}
		public static ParticipantDTO getDTO(Participant user)
		{
			string id = user.nume;
			int pass = user.varsta;
			ParticipantDTO p = new ParticipantDTO(id, pass);
			p.id = user.id;
			return p;
		}

		public static Proba getFromDTO(ProbaDTO usdto)
		{
			float id = usdto.distanta;
			Stil pass = usdto.stil;
			Proba p = new Proba(id, pass);
			p.id = usdto.id;
			return p;

		}
		public static ProbaDTO getDTO(Proba user)
		{
			Stil id = user.stil;
			float pass = user.distanta;
			ProbaDTO p = new ProbaDTO(pass, id);
			p.id = user.id;
			return p;
		}

		public static Inscriere getFromDTO(InscriereDTO usdto)
		{
			Participant id = usdto.Participant;
			Proba pass = usdto.Proba;
			return new Inscriere(id, pass);

		}
		public static InscriereDTO getDTO(Inscriere user)
		{
			Participant id = user.Participant;
			Proba pass = user.Proba;
			return new InscriereDTO(id, pass);
		}



		public static List<ProbaDTO> getDTO(List<Proba> users)
		{
			List<ProbaDTO> frDTO = new List<ProbaDTO>();
			foreach(Proba p in users)
            {
				frDTO.Add(getDTO(p));
            }
			return frDTO;
		}

		public static List<Proba> getFromDTO(List<ProbaDTO> users)
		{
			List<Proba> friends = new List<Proba>();
			foreach(ProbaDTO p in users)
            {
				friends.Add(getFromDTO(p));
            }
			return friends;
		}

		public static List<ParticipantDTO> getDTO(List<Participant> users)
		{
			List<ParticipantDTO> frDTO = new List<ParticipantDTO>();
			foreach (Participant p in users)
			{
				frDTO.Add(getDTO(p));
			}
			return frDTO;
		}

		public static List<Participant> getFromDTO(List<ParticipantDTO> users)
		{
			List<Participant> friends = new List<Participant>();
			foreach (ParticipantDTO p in users)
			{
				friends.Add(getFromDTO(p));
			}
			return friends;
		}


	}
}
