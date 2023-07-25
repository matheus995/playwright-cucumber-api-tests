#language:pt

@AllScenarios-PT @patchUser
Funcionalidade: Alterar usuario

  @alterarUsuario
  Cenario: Alterar usuario com sucesso
    Dado que realizo o cadastro de um usuario
    E desejo alterar o usuario cadastrado
    E defino o path param id com o valor do campo id da response anterior
    Quando enviar requisicao PATCH para o path users/{id}
    Entao deve retornar o status code 200

  @validarPreenchimentosAlteracaoUsuario
  Esquema do Cenario: Validar o preenhcimento do payload para o PATCH /users{id}
    Dado que realizo o cadastro de um usuario
    E defino o path param id com o valor do campo id da response anterior
    E preencho no payload o campo <campo> com o valor "<valor>"
    Quando enviar requisicao PATCH para o path users/{id}
    Entao deve retornar o status code <code>

    Exemplos:
      | campo | valor             | code |
      | name  |                   | 200  |
      | name  | 0                 | 200  |
      | name  | 123               | 200  |
      | name  | aaaa              | 200  |
      | name  | null              | 200  |
      | name  | true              | 200  |
      | name  | false             | 200  |
      | name  | decimalNumber     | 200  |
      | name  | negativeNumber    | 200  |
      | name  | 15.numbers        | 200  |
      | name  | 100.stringNumbers | 200  |
      | name  | 25.specialString  | 200  |
      | job   |                   | 200  |
      | job   | 0                 | 200  |
      | job   | 123               | 200  |
      | job   | aaaa              | 200  |
      | job   | null              | 200  |
      | job   | true              | 200  |
      | job   | false             | 200  |
      | job   | decimalNumber     | 200  |
      | job   | negativeNumber    | 200  |
      | job   | 15.numbers        | 200  |
      | job   | 100.stringNumbers | 200  |
      | name  | 25.specialString  | 200  |