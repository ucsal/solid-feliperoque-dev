package br.com.ucsal.olimpiadas;

import java.util.Scanner;

public class App {
	private static final ServicosOlimpiada servico = new ServicosOlimpiada();
	private static final Tabuleiro tabuleiro = new Tabuleiro();
	private static final Scanner in = new Scanner(System.in);

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
			case "1" -> cadastrarParticipante();
			case "2" -> cadastrarProva();
			case "3" -> cadastrarQuestao();
			case "4" -> aplicarProva();
			case "5" -> listarTentativas();
			case "0" -> {
				System.out.println("tchau");
				return;
			}
			default -> System.out.println("opção inválida");
			}
		}
	}

	static void cadastrarParticipante() {
		System.out.print("Nome: ");
		var nome = in.nextLine();

		System.out.print("Email (opcional): ");
		var email = in.nextLine();

		if (nome == null || nome.isBlank()) {
			System.out.println("nome inválido");
			return;
		}

		var p = new Participante();
		p.setId(Repositorio.proximoParticipanteId++);
		p.setNome(nome);
		p.setEmail(email);

		Repositorio.participantes.add(p);
		System.out.println("Participante cadastrado: " + p.getId());
	}

	static void cadastrarProva() {
		System.out.print("Título da prova: ");
		var titulo = in.nextLine();

		if (titulo == null || titulo.isBlank()) {
			System.out.println("título inválido");
			return;
		}

		var prova = new Prova();
		prova.setId(Repositorio.proximaProvaId++);
		prova.setTitulo(titulo);

		Repositorio.provas.add(prova);
		System.out.println("Prova criada: " + prova.getId());
	}

	static void cadastrarQuestao() {
		if (Repositorio.provas.isEmpty()) {
			System.out.println("não há provas cadastradas");
			return;
		}

		var provaId = escolherProva();
		if (provaId == null)
			return;

		System.out.println("Enunciado:");
		var enunciado = in.nextLine();

		var alternativas = new String[5];
		for (int i = 0; i < 5; i++) {
			char letra = (char) ('A' + i);
			System.out.print("Alternativa " + letra + ": ");
			alternativas[i] = letra + ") " + in.nextLine();
		}

		System.out.print("Alternativa correta (A–E): ");
		char correta;
		try {
			correta = Questao.normalizar(in.nextLine().trim().charAt(0));
		} catch (Exception e) {
			System.out.println("alternativa inválida");
			return;
		}

		var q = new Questao();
		q.setId(Repositorio.proximaQuestaoId++);
		q.setProvaId(provaId);
		q.setEnunciado(enunciado);
		q.setAlternativas(alternativas);
		q.setAlternativaCorreta(correta);

		Repositorio.questoes.add(q);

		System.out.println("Questão cadastrada: " + q.getId() + " (na prova " + provaId + ")");
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

		var participanteId = escolherParticipante();
		if (participanteId == null)
			return;

		var provaId = escolherProva();
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


	static Long escolherParticipante() {
		System.out.println("\nParticipantes:");
		for (var p : Repositorio.participantes) {
			System.out.printf("  %d) %s%n", p.getId(), p.getNome());
		}
		System.out.print("Escolha o id do participante: ");

		try {
			long id = Long.parseLong(in.nextLine());
			boolean existe = Repositorio.participantes.stream().anyMatch(p -> p.getId() == id);
			if (!existe) {
				System.out.println("id inválido");
				return null;
			}
			return id;
		} catch (Exception e) {
			System.out.println("entrada inválida");
			return null;
		}
	}

	static Long escolherProva() {
		System.out.println("\nProvas:");
		for (var p : Repositorio.provas) {
			System.out.printf("  %d) %s%n", p.getId(), p.getTitulo());
		}
		System.out.print("Escolha o id da prova: ");

		try {
			long id = Long.parseLong(in.nextLine());
			boolean existe = Repositorio.provas.stream().anyMatch(p -> p.getId() == id);
			if (!existe) {
				System.out.println("id inválido");
				return null;
			}
			return id;
		} catch (Exception e) {
			System.out.println("entrada inválida");
			return null;
		}
	}

}