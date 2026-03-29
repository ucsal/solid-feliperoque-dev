package repositorio;

import java.util.ArrayList;
import java.util.List;

import entidades.Participante;
import entidades.Prova;
import entidades.Questao;
import entidades.questao.Tentativa;

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
