package repositorio;

import java.util.ArrayList;
import java.util.List;

import br.com.ucsal.olimpiadas.Participante;
import br.com.ucsal.olimpiadas.Prova;
import br.com.ucsal.olimpiadas.Questao;
import br.com.ucsal.olimpiadas.Tentativa;

public class Repositorio {

	public static long proximoParticipanteId = 1;
	public static long proximaProvaId = 1;
	public static long proximaQuestaoId = 1;
	public static long proximaTentativaId = 1;

	public static final List<Participante> participantes = new ArrayList<>();
	public static final List<Prova> provas = new ArrayList<>();
	public static final List<Questao> questoes = new ArrayList<>();
	public static final List<Tentativa> tentativas = new ArrayList<>();
	
}
