package busca;

import java.util.Scanner;

import repositorio.Repositorio;

public class EscolherParticipante implements Escolher {
	
	private final Scanner in;
	
	public EscolherParticipante(Scanner in){
		this.in = in;
	}

	@Override
	public Long escolher() {

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
	
}
