#language:pt

@AllScenarios-PT @getUsers
Funcionalidade: Consultar usuarios

  @consultarUsuarios
  Cenario: Consultar usuarios com sucesso
    Quando enviar requisicao GET para o path users
    Entao deve retornar o status code 200
    E o contrato deve estar de acordo com o getUsers.json