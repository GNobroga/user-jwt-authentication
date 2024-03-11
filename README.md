# UserShield

É um projeto simples que fiz envolvendo autenticação manual via JWT sem utilizar o Resource Server, apenas com filtro

## Para executar no docker

Clone o projeto e abra o diretório e execute o comando:

```bash
    docker-compose up -d
```

ou caso não deseje utilizar com o docker compose, faça o build da imagem:

**-t** - Indica o nome da imagem e o : a tag associada a ela.

**-f** - Especifica o nome e o caminho para o arquivo de build.

```bash
    docker image build  -t minha-tag-aqui:0.1 -f Dockerfile .
```

após isso, crie e execute um container baseado na imagem buildada:

**--name** - É o nome do container 

**-dt** - Significa que eu quero que o container execute em background (segundo plano) e tenha uma terminal exclusivo.

**-p** - Faz o binding de portas do host com portas expostas pelo container

```bash
    docker container run --name meucontainer -dt -p 8080:8080 minha-tag-aqui:0.1 
```

pronto!! A aplicação estará rodando na porta 8080 do host.

## Endpoints

Account tem suporte aos verbos **GET**, **DELETE**, **PUT**, **POST**.

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
    "accessToken": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoIiwiZXhwIjoxNzEwMTE0ODE2LCJzdWIiOiJrYW1lbG91dGkiLCJyb2xlcyI6IkNPTU1PTiJ9.FJRFUsRVMhn4RJQ0wN8ntpUHV8z0EIf8T2qJsENJNqlu3c8Onval5l8nsFEz9sgC5MOPHQpzef0CFBVOG0dvIi1MbKrwxH9poTA4jKDVXzPeHgoYk0y7HA1oe3J6ycdBa5NaB4XGTHS4Nj0tyGimrJXdT9AX28NlmhFnFHRObgq6EwejY3gaIB8vmDK6TvDceEPa1XyAy_D8QSK5BLQq0Ljio0p_PTruhw_WDhcXlkx_Gz9YqBcLbiJt6cWtMbzvRGZw2Dz63VO6_bEDll5YC3IEGF2La-P3h8i2MMMwy3ENTmYjPqAodSt-78PGyISl8vf9d5EM9-SanVLpX_J-mA"

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