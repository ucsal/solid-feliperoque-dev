package servicos;

import repositorio.Repositorio;

public class ServicosTentativas {

	private final ServicosOlimpiada servico;

	public ServicosTentativas(ServicosOlimpiada servico) {
		this.servico = servico;
	}

	public void listarTentativas() {
		System.out.println("\n--- Tentativas ---");
		for (var t : Repositorio.tentativas) {
			System.out.printf("#%d | participante=%d | prova=%d | nota=%d/%d%n", t.getId(), t.getParticipanteId(),
					t.getProvaId(), servico.calcularNota(t), t.getRespostas().size());
		}
	}

}
