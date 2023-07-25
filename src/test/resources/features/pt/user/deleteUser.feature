#language:pt

@AllScenarios-PT @deleteUser
Funcionalidade: Deletar usuario

  @deletarUsuario
  Cenario: Deletar usuario com sucesso
    Dado que realizo o cadastro de um usuario
    E defino o path param id com o valor do campo id da response anterior
    Quando enviar requisicao DELETE para o path users/{id}
    Entao deve retornar o status code 204