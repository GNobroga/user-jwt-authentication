# user-shield

É um simples projeto que envolve autenticação via JWT, utilizando filtros e as classes fornecidas pelo Java. Apesar de ser simples, empreguei diversos conceitos interessantes neste projeto, tais como Specification, Example, Criteria, entre outros.

## Para executar no docker

Clone o projeto e navegue até o diretório correspondente.
Utilize o seguinte comando para executar o projeto com o Docker Compose:

```bash
    docker-compose up -d
```

Caso prefira não utilizar o Docker Compose, construa a imagem com o seguinte comando:

```bash
    docker image build  -t minha-tag-aqui:0.1 -f Dockerfile .
```

Após a construção da imagem, crie e execute um container com base na imagem criada:

```bash
    docker container run --name meucontainer -dt -p 8080:8080 minha-tag-aqui:0.1 
```

Certifique-se de substituir "minha-tag-aqui" pelo nome desejado para a imagem e ajuste as opções conforme necessário. Este comando cria e executa um container em segundo plano, atribuindo um terminal exclusivo e fazendo o mapeamento das portas do host com as portas expostas pelo container.


## Endpoints

Account tem suporte aos verbos **GET**, **DELETE**, **PUT**, **POST**. Os endpoints: 

**/account** - Permite utilizar o POST sem autenticação.
**/auth/token** - Permite utilizar o POST sem autenticação.
**/h2-console** - Permissão total.

Para utilizar o verbo **GET**, o usuário autenticado precisa ter a role **COMMON**. E **ADMIN** para qualquer outra rota que exija autenticação.

#### /account (GET)

```json
{
    "result": [
        {
            "id": "5aee3478-0ab1-4de0-9b59-07702aa36918",
            "name": "Gabriel Cardoso",
            "email": "kamelouti@gmail.com",
            "username": "kamelouti",
            "roles": [
                "COMMON"
            ]
        }
    ],
    "totalElements": 1,
    "totalPages": 10,
    "currentPage": 0
}
```

#### /account (POST)

Payload


```json
 {
    "email": "kamelouti@gmail.com",
    "name": "Gabriel Cardoso",
    "username": "kamelouti",
    "password": "kamelouti"
}
```

Resposta

```json
  {
    "result": {
        "id": "ba2f5a45-f64f-4abe-b065-0eb066bf14e0",
        "name": "Gabriel Cardoso",
        "email": "kamelouti@gmail.com",
        "username": "kamelouti",
        "roles": [
            "COMMON"
        ]
    }
}
```

### /auth/token

Payload

```json
{
    "username": "kamelouti",
    "password": "kamelouti"
}
```

Resposta

```json
{
    "tokenType": "Bearer",
    "accessToken": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoIiwiZXhwIjoxNzEwMTE0ODE2LCJzdWIiOiJrYW1lbG91dGkiLCJyb2xlcyI6IkNPTU1PTiJ9.FJRFUsRVMhn4RJQ0wN8ntpUHV8z0EIf8T2qJsENJNqlu3c8Onval5l8nsFEz9sgC5MOPHQpzef0CFBVOG0dvIi1MbKrwxH9poTA4jKDVXzPeHgoYk0y7HA1oe3J6ycdBa5NaB4XGTHS4Nj0tyGimrJXdT9AX28NlmhFnFHRObgq6EwejY3gaIB8vmDK6TvDceEPa1XyAy_D8QSK5BLQq0Ljio0p_PTruhw_WDhcXlkx_Gz9YqBcLbiJt6cWtMbzvRGZw2Dz63VO6_bEDll5YC3IEGF2La-P3h8i2MMMwy3ENTmYjPqAodSt-78PGyISl8vf9d5EM9-SanVLpX_J-mA",
    "refreshToken": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoIiwiZXhwIjoxNzEwMTI1NjE2LCJzdWIiOiJrYW1lbG91dGkiLCJyb2xlcyI6IkNPTU1PTiIsInJlZnJlc2hUb2tlbiI6dHJ1ZX0.fk17CU8cUniP_qwJBqWKbUEB3KKApIxYDmGK4PXtoprAuL-bZPQpyleUiTQDvN11WirRjLnoxS3zY0tsyV3jILbdNi_F6xO-rMWTDvxKim9uFFyhq-AFTlPGf68mTml8_QRaDaeQKRHjm4jFibfmxW1EOpbn23bmusXNHCtEaAk8zHbm0F-F9F1lHvosXKVzCa0Uc5mToUzExE_G0l-jRzLlyKBBikGHz4glhtwKhzwKagMJ3sZaqPKpLxNy3VqBBHDvh708wohZgxz5X9j-1qSh1T_XYgP1Vr-z9fAzHi7UJOpHTE1bfLwoTMwwL_SqNJkGRKMaj4n-UYJXwGIt3g"
}

```

#### /auth/refresh-token 

```json
{
    "refreshToken": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoIiwiZXhwIjoxNzEwMTE0ODE2LCJzdWIiOiJrYW1lbG91dGkiLCJyb2xlcyI6IkNPTU1PTiJ9.FJRFUsRVMhn4RJQ0wN8ntpUHV8z0EIf8T2qJsENJNqlu3c8Onval5l8nsFEz9sgC5MOPHQpzef0CFBVOG0dvIi1MbKrwxH9poTA4jKDVXzPeHgoYk0y7HA1oe3J6ycdBa5NaB4XGTHS4Nj0tyGimrJXdT9AX28NlmhFnFHRObgq6EwejY3gaIB8vmDK6TvDceEPa1XyAy_D8QSK5BLQq0Ljio0p_PTruhw_WDhcXlkx_Gz9YqBcLbiJt6cWtMbzvRGZw2Dz63VO6_bEDll5YC3IEGF2La-P3h8i2MMMwy3ENTmYjPqAodSt-78PGyISl8vf9d5EM9-SanVLpX_J-mA"

}
```

Retorno

```json
{
    "tokenType": "Bearer",
    "accessToken": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoIiwiZXhwIjoxNzEwMTE1MDk3LCJzdWIiOiJrYW1lbG91dGkiLCJyb2xlcyI6IkNPTU1PTiJ9.PClUc7cxojCXzGYFwfMyah486ecGTsTaERQY35cg29JrsQr_RvBmVmkKGHTbH5KMrbR0-S39B9fWU3drDIUVDW8B62TbmlLqFql67fIvqAWUhTzZzymrxTMmUNRm-VmDOYmrhctMLy_FO0j18gvu5_G0GdB5ysYIO8dEPfa2Rrz57yul-BTBlSKLQbf9dYdHbQcso1TlexNnXEVbgY47WcCuWRyF-NkCzQxX4PSCrzIvzsz-TXfOneDOEADk1ikN1G0zk3ARFMB-hjxV_HHZx1SKLA7q02r16DjZvZOQVR8AF-P_XE2pDMh7-0Zase7uK9RZK8ybHIk0CvSwlc2idg",
    "refreshToken": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoIiwiZXhwIjoxNzEwMTIyMjk3LCJzdWIiOiJrYW1lbG91dGkiLCJyb2xlcyI6IkNPTU1PTiIsInJlZnJlc2hUb2tlbiI6dHJ1ZX0.SvnXw9HnnVD2VzR1ItUpGRPkrhSKRJGliZKj_aXeuWN-2vxD9yZBWAz8_jHpVeqknlT5XPQlb11yVVgv1AF78eJchvpw_gcQi6E5xPAlZTNZ1lygLt7qbJxwtNG7wtxFYxU2ibhSOk2KBJCgMazUi8ogPlLEvXAY8qqnpkZIzq_cA6siAAE9nRIQmmaW1Bfp3n2xFNp2RdJQb9K4Mq9PkqAl8KUvyDiohzwku6AEgTt5lSEfmjTRe9xkPeJ0EY1eTHJYdYaRA423DtxPXAcyyv1tYJvwrVbSEA3CPJhUy2p23AiQ6pzKJCS5_JOnwNG0G1xT_9bNL05Fj-4h4qQ8oA"
}
```

### Tecnologias

#### Auth0/java-jwt

#### ModelMapper

#### Spring Data JPA

#### CriteriaAPI

#### H2

##### Feito por amor por mim <3
