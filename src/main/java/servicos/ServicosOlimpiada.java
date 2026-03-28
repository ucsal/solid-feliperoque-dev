package servicos;

import entidades.Prova;
import entidades.Questao;
import entidaes.questao.Tentativa;
import repositorio.Repositorio;

public class ServicosOlimpiada {
	
	
	public void seed() {
        var prova = new Prova();
        prova.setId(Repositorio.proximaProvaId++);
        prova.setTitulo("Olimpíada 2026 • Nível 1 • Prova A");
        Repositorio.provas.add(prova);

        var q1 = new Questao();
        q1.setId(Repositorio.proximaQuestaoId++);
        q1.setProvaId(prova.getId());
        q1.setEnunciado("""
                Questão 1 — Mate em 1.
                É a vez das brancas.
                Encontre o lance que dá mate imediatamente.
                """);
        q1.setFenInicial("6k1/5ppp/8/8/8/7Q/6PP/6K1 w - - 0 1");
        q1.setAlternativas(new String[] { "A) Qh7#", "B) Qf5#", "C) Qc8#", "D) Qh8#", "E) Qe6#" });
        q1.setAlternativaCorreta('C');

        Repositorio.questoes.add(q1);
    }

    public int calcularNota(Tentativa tentativa) {
        int acertos = 0;
        for (var r : tentativa.getRespostas()) {
            if (r.isCorreta())
                acertos++;
        }
        return acertos;
    }
}
