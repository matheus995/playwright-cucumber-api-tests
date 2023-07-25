#language:pt

@AllScenarios-PT @postUser
Funcionalidade: Cadastro de usuarios

  @cadastrarUsuario
  Cenario: Cadastrar usuario com sucesso
    Dado que tenho um usuario
    Quando enviar requisicao POST para o path users
    Entao deve retornar o status code 201
    E o contrato deve estar de acordo com o postUser.json

  @validarPreenchimentosCadastroUsuario
  Esquema do Cenario: Validar o preenhcimento do payload para o POST /users
    Dado que tenho um usuario
    E preencho no payload o campo <campo> com o valor "<valor>"
    Quando enviar requisicao POST para o path users
    Entao deve retornar o status code <code>

    Exemplos:
      | campo | valor             | code |
      | name  |                   | 201  |
      | name  | 0                 | 201  |
      | name  | 123               | 201  |
      | name  | aaaa              | 201  |
      | name  | null              | 201  |
      | name  | true              | 201  |
      | name  | false             | 201  |
      | name  | decimalNumber     | 201  |
      | name  | negativeNumber    | 201  |
      | name  | 15.numbers        | 201  |
      | name  | 100.stringNumbers | 201  |
      | name  | 25.specialString  | 201  |
      | job   |                   | 201  |
      | job   | 0                 | 201  |
      | job   | 123               | 201  |
      | job   | aaaa              | 201  |
      | job   | null              | 201  |
      | job   | true              | 201  |
      | job   | false             | 201  |
      | job   | decimalNumber     | 201  |
      | job   | negativeNumber    | 201  |
      | job   | 15.numbers        | 201  |
      | job   | 100.stringNumbers | 201  |
      | name  | 25.specialString  | 201  |