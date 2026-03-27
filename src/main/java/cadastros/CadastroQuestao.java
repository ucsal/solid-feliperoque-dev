package cadastros;

import java.util.Scanner;

import br.com.ucsal.olimpiadas.Questao;
import busca.Escolher;
import repositorio.Repositorio;

public class CadastroQuestao implements Cadastro {
	
	private final Scanner in;
	private final Escolher epr;
	
	public CadastroQuestao(Scanner in, Escolher epr){
		this.in = in;
		this.epr = epr;
	}

	@Override
	public void cadastro() {
		if (Repositorio.provas.isEmpty()) {
			System.out.println("não há provas cadastradas");
			return;
		}
		
		var provaId = epr.escolher();
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

}
