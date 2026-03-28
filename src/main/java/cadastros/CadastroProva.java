package cadastros;

import java.util.Scanner;

import entidades.Prova;
import repositorio.Repositorio;

public class CadastroProva implements Cadastro {

	private final Scanner in;
	
	public CadastroProva(Scanner in){
		this.in = in;
	}

	@Override
	public void cadastro() {

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

		in.close();
	}

}
