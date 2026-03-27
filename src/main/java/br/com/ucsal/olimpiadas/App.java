package br.com.ucsal.olimpiadas;

import java.util.Scanner;

import busca.Escolher;
import busca.EscolherParticipante;
import busca.EscolherProva;
import cadastros.Cadastro;
import cadastros.CadastroParticipante;
import cadastros.CadastroProva;
import cadastros.CadastroQuestao;
import repositorio.Repositorio;
import servicos.ServicosOlimpiada;

public class App {
	
	private static final Scanner in = new Scanner(System.in);
	
	public static final Escolher ep = new EscolherParticipante(in);
	public static final Escolher epr = new EscolherProva(in);
	
	public static final Cadastro cp = new CadastroParticipante(in);
	public static final Cadastro cpr = new CadastroProva(in);
	public static final Cadastro cq = new CadastroQuestao(in, epr);
	

	private static final ServicosOlimpiada servico = new ServicosOlimpiada();
	private static final Tabuleiro tabuleiro = new Tabuleiro();


	public static void main(String[] args) {
		servico.seed();

		while (true) {
			System.out.println("\n=== OLIMPÍADA DE QUESTÕES (V1) ===");
			System.out.println("1) Cadastrar participante");
			System.out.println("2) Cadastrar prova");
			System.out.println("3) Cadastrar questão (A–E) em uma prova");
			System.out.println("4) Aplicar prova (selecionar participante + prova)");
			System.out.println("5) Listar tentativas (resumo)");
			System.out.println("0) Sair");
			System.out.print("> ");

			switch (in.nextLine()) {
			case "1"-> cp.cadastro();
			case "2"-> cpr.cadastro();
			case "3"-> cq.cadastro();
			case "4"-> aplicarProva();
			case "5"-> listarTentativas();
			case "0"-> {
				System.out.println("tchau");
				return;
			}
			default ->
				System.out.println("opção inválida");
			}
		}
	}

	static void aplicarProva() {
		if (Repositorio.participantes.isEmpty()) {
			System.out.println("cadastre participantes primeiro");
			return;
		}
		if (Repositorio.provas.isEmpty()) {
			System.out.println("cadastre provas primeiro");
			return;
		}

		var participanteId = ep.escolher();
		if (participanteId == null)
			return;

		var provaId = epr.escolher();
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

	static void listarTentativas() {
		System.out.println("\n--- Tentativas ---");
		for (var t : Repositorio.tentativas) {
			System.out.printf("#%d | participante=%d | prova=%d | nota=%d/%d%n", t.getId(), t.getParticipanteId(),
					t.getProvaId(), servico.calcularNota(t), t.getRespostas().size());
		}
	}

}