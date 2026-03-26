package br.com.ucsal.olimpiadas;

import java.util.ArrayList;
import java.util.List;

public class Repositorio {

	static long proximoParticipanteId = 1;
	static long proximaProvaId = 1;
	static long proximaQuestaoId = 1;
	static long proximaTentativaId = 1;

	static final List<Participante> participantes = new ArrayList<>();
	static final List<Prova> provas = new ArrayList<>();
	static final List<Questao> questoes = new ArrayList<>();
	static final List<Tentativa> tentativas = new ArrayList<>();
	
}
