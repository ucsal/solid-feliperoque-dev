package servicos;

import java.util.Scanner;

import busca.Escolher;
import entidades.Questao;
import entidades.questao.Resposta;
import entidades.questao.Tabuleiro;
import entidades.questao.Tentativa;
import repositorio.Repositorio;

public class ServicosProva {

	private final Scanner in;
	private final Escolher escolherParticipante;
	private final Escolher escolherProva;
	private final Tabuleiro tabuleiro;
	private final ServicosOlimpiada servico;

	public ServicosProva(Scanner in, Escolher escolherParticipante, Escolher escolherProva, Tabuleiro tabuleiro,
			ServicosOlimpiada servico) {
		this.in = in;
		this.escolherParticipante = escolherParticipante;
		this.escolherProva = escolherProva;
		this.tabuleiro = tabuleiro;
		this.servico = servico;
	}

	public void aplicarProva() {
		if (Repositorio.participantes.isEmpty()) {
			System.out.println("cadastre participantes primeiro");
			return;
		}
		if (Repositorio.provas.isEmpty()) {
			System.out.println("cadastre provas primeiro");
			return;
		}

		var participanteId = escolherParticipante.escolher();
		if (participanteId == null)
			return;

		var provaId = escolherProva.escolher();
		if (provaId == null)
			return;

		var questoesDaProva = Repositorio.questoes.stream().filter(q -> q.getProvaId() == provaId).toList();
		
		if (questoesDaProva.isEmpty()) {
			System.out.println("esta prova não possui questões cadastradas");
			return;
		}

		var tentativa = new Tentativa();
		tentativa.setId(Repositorio.proximaTentativaId++);
		tentativa.setParticipanteId(participanteId);
		tentativa.setProvaId(provaId);

		System.out.println("\n--- Início da Prova ---");

		for (var q : questoesDaProva) {
			System.out.println("\nQuestão #" + q.getId());
			System.out.println(q.getEnunciado());

			System.out.println("Posição inicial:");
			tabuleiro.imprimirTabuleiroFen(q.getFenInicial());

			for (var alt : q.getAlternativas()) {
				System.out.println(alt);
			}

			System.out.print("Sua resposta (A–E): ");
			char marcada;
			try {
				marcada = Questao.normalizar(in.nextLine().trim().charAt(0));
			} catch (Exception e) {
				System.out.println("resposta inválida (marcando como errada)");
				marcada = 'X';
			}

			var r = new Resposta();
			r.setQuestaoId(q.getId());
			r.setAlternativaMarcada(marcada);
			r.setCorreta(q.isRespostaCorreta(marcada));

			tentativa.getRespostas().add(r);
		}

		Repositorio.tentativas.add(tentativa);

		int nota = servico.calcularNota(tentativa);
		System.out.println("\n--- Fim da Prova ---");
		System.out.println("Nota (acertos): " + nota + " / " + tentativa.getRespostas().size());
	}

}
