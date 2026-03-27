package cadastros;

import java.util.Scanner;

import br.com.ucsal.olimpiadas.Participante;
import repositorio.Repositorio;

public class CadastroParticipante implements Cadastro {
	
	private final Scanner in;
	
	public CadastroParticipante(Scanner in){
		this.in = in;
	}

	@Override
	public void cadastro() {
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
		
		in.close();
	}
}
