Controller Tool API

Funções Básicas:

1-Receber relatório de improdutivo do dia e gerar dashboard de atendimento
2-Ter facil acesso a dados dos atendimentos:
  Placa;
  Atendimento;
  OS;
  Tipo de Atendimento;
  Status;
  Oficina;
  Telefone oficina;
  Localização da Oficina.
3-Adicionar anotações em cada atendimento

Funcionamento...

A API deverá receber o relatório via requisição, receber os dados e buscar informações adicionais no banco de dados,
Gerar arquivo Json e enviar ao front, onde cada atendimento será um card, cada card deverá ter as seguintes informações:
Placa, atendimento, OS, Tipo de Atendimento, Status, Oficina, Telefone Oficina, Localização Oficina e anotações.

Os card's deverão ser separados em colunas filtrados por seus status
Os card's deverão ter as seguintes funcionalidades, 
  Telefone, permitir chamar contato via WhatsApp;
  Localização, informar localização (gerar frame ao clicar com a localização no GoogleMaps);
  Anotações, ter espaço para inserir breves observações de cada chamado.

A base de dados referente a dados da oficina será mantida em um banco de dados, o qual deverá ser possível fazer a atualização via API,
Com isso será necessário a possibilidade de enviar arquivo e realizar a atualização do banco de dados.
Ao ser inputado o relatório de atendimentos serão cruzados os dados e informados os dados do prestador como:
  Razão Social;
  CNPJ;
  Telefone;
  Localização (latitude e longitude).
