1. Responsabilidade Única (SRP)
- A classe App foi simplificada para atuar estritamente como a interface de menu do usuário.
- A lógica de execução e aplicação de exames foi movida para a classe ServicosProva.
- O gerenciamento e a formatação de relatórios de resultados foram isolados em ServicosTentativas.
- Cada classe de cadastro agora possui a única função de processar a entrada de dados do seu respectivo domínio.

2. Aberto/Fechado (OCP)
- O sistema foi estruturado através das interfaces Cadastro e Escolher.
- Isso permite a expansão do software (como a criação de novos tipos de cadastros ou métodos) sem a necessidade de modificar o código fonte da classe principal App.

4. Substituição de Liskov (LSP)
- No menu principal, as operações de cadastro são tratadas através do tipo genérico da interface Cadastro.
- Qualquer implementação específica (CadastroParticipante, CadastroProva, CadastroQuestao) funciona como uma substituição direta da interface, sem comprometer a estabilidade ou o comportamento esperado do sistema.

4. Segregação de Interface (ISP)
- As interfaces foram divididas por contextos de uso específicos:
- Cadastro: Focada estritamente na criação de novos registros.
- Escolher: Especializada na busca e seleção de IDs de entidades existentes.
- Essa separação evita que classes de busca sejam obrigadas a implementar métodos de cadastro que não utilizariam.

5. Inversão de Dependência (DIP)
- As classes de serviço e cadastro não dependem mais de implementações concretas:
- As dependências como o Scanner ou métodos de busca são injetadas através de um construtor.
- A classe CadastroQuestao, por exemplo, depende da interface Escolher em vez de uma classe de busca específica, reduzindo o acoplamento entre os pacotes de cadastro e busca.
