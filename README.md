# Projeto final BE-JV-003 - POO II

## Grupo 2: Ana Beatriz Trindade, Antonio Neto, Gabriel Faglioni, Lucas Gribel Dos Reis, Matheus Neris Romeiro.

## LocateCar - Locadora de veículos
Criar uma aplicação que gerencie o aluguel de veículos, onde cada item abaixo seja considerado:

### Itens obrigatórios
- [x] Cadastrar os veículos;
- [x] Alterar um veículo cadastrado;
- [x] Buscar um veículo por parte do nome;
- [x] Cadastrar a agência onde o veículo será alugado/devolvido;
- [x] Alterar a agência onde o veículo será alugado/devolvido;
- [x] Buscar uma agência por parte do nome ou do logradouro do endereço;
- [x] Cadastrar o cliente (pessoa fisica/juridica)
- [x] Alterar o cliente (pessoa fisica/juridica)
- [x] Alugar um veículo para pessoa fisica;
- [x] Alugar um veículo para pessoa juridica;
- [x] Devolver um veículo para pessoa fisica;
- [x] Devolver um veículo para pessoa juridica;
- [ ] Gerar um comprovante com todos os dados do aluguel (aberto para o grupo decidir o que vai ser demonstrado);
- [ ] Gerar um comprovante com todos os dados da devolução (aberto para o grupo decidir o que vai ser demonstrado);

### Itens bônus
- [x] Paginar as listas envolvidas;
- [x] Os dados deverão ser gravados em arquivos, simulando uma base de dados;

### Regras de negócio
- RN1: Os veículos não podem ser repetidos;
- RN2: Tipos de veículos que serão considerados: Carro, Moto, Caminhões;
- RN3: Os aluguéis e devoluções terão o local, data e horario;
- RN4: Os veículos que estiverem alugados não poderão estar disponíveis;
- RN5: Agências não podem estar duplicadas;
- RN6: Clientes não podem estar duplicados;
- RN7: Regras de devolução:
   - Caso pessoa fisica tenha ficado com o carro mais que 5 dias terá direito a 5% de desconto.
   - Caso pessoa juridica tenha ficado com o carro mais que 3 dias terá direito a 10% de desconto.

### Valores base da diária por tipo de veículo:
Tipo de Veículo	| Valor por dia
--------------- | -------------
Moto	          | R$ 100,00
Carro	          | R$ 150,00
Caminhão	      | R$ 200,00
