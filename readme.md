O objetivo é deixar os micro-serviços o mais genérico possível para agilizar nas mudanças de contratos em runtime, e na criação de novos micro-serviços.

Para isso sabemos que relay e consumer sempre tem a mesma estrutura, o que muda são os nomes das filas, e o contrato. Então o objetivo é explorar estes dois lados para deixar eles genéricos. E para o consumer temos o caso para qual endpoint ele vai enviar os dados para o service.

A criação e alteração do contrato dos endpoints podem ser feitos da mesma maneira que o Stoplight.

Para a generalização dos objetos será usado HasMap em Java.

Deixar as definições de objetos e classes sempre em memória para não atrasar na inserção de dados.
MyObject será utilizado para o repasse dos objetos instanciados
MyClass será utilizado para definição do nome, type e se é null ou não para cada campo, assim na hora inserção será usado essa definição para pedir os campos obrigatórios

Ao iniciar o programa ele vai buscar todos os MyObject e MyClass do banco de dados e deixá-los em memória, essa field será um static e será uma lista desses objetos.
Ao adicionar um novo objeto deixar em memória e no banco de dados.

Observação: como os dados vão ser bem poucos, talvez gravar tudo em um arquivo para que não use um banco de dados para tão pouco

