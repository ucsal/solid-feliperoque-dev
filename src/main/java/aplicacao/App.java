package aplicacao;

import java.util.Scanner;

import busca.Escolher;
import busca.EscolherParticipante;
import busca.EscolherProva;
import cadastros.Cadastro;
import cadastros.CadastroParticipante;
import cadastros.CadastroProva;
import cadastros.CadastroQuestao;
import entidades.questao.Tabuleiro;
import servicos.ServicosOlimpiada;
import servicos.ServicosProva;
import servicos.ServicosTentativas;

public class App {
	
	private static final Scanner in = new Scanner(System.in);
	
	public static final Escolher escolherParticipante = new EscolherParticipante(in);
	public static final Escolher escolherProva = new EscolherProva(in);
	
	public static final Cadastro cadastroParticipante = new CadastroParticipante(in);
	public static final Cadastro cadastroProva = new CadastroProva(in);
	public static final Cadastro cadastroQuestao = new CadastroQuestao(in, escolherProva);
	
	private static final Tabuleiro tabuleiro = new Tabuleiro();
	
	private static final ServicosOlimpiada servico = new ServicosOlimpiada();
	private static final ServicosProva aplicarProva = new ServicosProva(in, escolherParticipante, escolherProva, tabuleiro, servico);
	private static final ServicosTentativas tentativas = new ServicosTentativas(servico);
	
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
			case "1"-> cadastroParticipante.cadastro();
			case "2"-> cadastroProva.cadastro();
			case "3"-> cadastroQuestao.cadastro();
			case "4"-> aplicarProva.aplicarProva();
			case "5"-> tentativas.listarTentativas();
			case "0"-> {
				System.out.println("tchau");
				in.close();
				return;
			}
			default ->
				System.out.println("opção inválida");
			}
		}
	}
}