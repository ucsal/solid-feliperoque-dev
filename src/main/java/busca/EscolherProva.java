package busca;

import java.util.Scanner;

import repositorio.Repositorio;

public class EscolherProva implements Escolher {
	
	private final Scanner in;
	
	public EscolherProva(Scanner in){
		this.in = in;
	}
	
	@Override
	public Long escolher() {
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
