# tapr-2024-turmaa-colegio-java

## Documentação do projeto
[Diagramas](https://univillebr-my.sharepoint.com/:u:/g/personal/walter_s_univille_br/EbLNg-hQDmdIjM6sIIFvjA0BHpsa_cRHPT0BpNIaea0yXw?e=tPsYS0)

## Extensões do VSCode
[Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack?wt.mc_id=AZ-MVP-5003638)

[Spring Boot Extensio Pack](https://marketplace.visualstudio.com/items?itemName=vmware.vscode-boot-dev-pack?wt.mc_id=AZ-MVP-5003638)

[Rest Client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client?wt.mc_id=AZ-MVP-5003638)

## Criação do projeto
1. Pressionar F1
2. Spring Initializr: create a Maven Project
3. Versão 3.3.4
4. Languages: Java
5. Package: br.univille
6. Name: microserv<nome do subdominio>
7. Pacote: jar
8. Jave: 17
9. Dependencias: Spring Web e Devtools

10. Criar um namespace com o nome de cada Bounded Context
11. Criar um namespace chamado Entities e dentro dele criar as entidades
```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── br
│   │   │       └── univille
│   │   │           └── microservcolegio
│   │   │               ├── MicroservcolegioApplication.java
│   │   │               └── secretaria
│   │   │                   └── entity
│   │   │                       └── Aluno.java
```

## Cosmos DB
[Introdução (https://learn.microsoft.com/en-us/azure/cosmos-db/introduction?wt.mc_id=AZ-MVP-5003638)](https://learn.microsoft.com/en-us/azure/cosmos-db/introduction?wt.mc_id=AZ-MVP-5003638)

[Databases, Containers e Itens (https://learn.microsoft.com/en-us/azure/cosmos-db/resource-model?wt.mc_id=AZ-MVP-5003638)](https://learn.microsoft.com/en-us/azure/cosmos-db/resource-model?wt.mc_id=AZ-MVP-5003638)

```
docker run \
    --publish 8081:8081 \
    --publish 10250-10255:10250-10255 \
    --name cosmosdb-linux-emulator \
    --detach \
    mcr.microsoft.com/cosmosdb/linux/azure-cosmos-emulator:latest    
```
### Instalação do certificado
```
curl --insecure https://localhost:8081/_explorer/emulator.pem > ~/emulatorcert.crt
```
```
sudo cp ~/emulatorcert.crt /usr/local/share/ca-certificates/
```
```
sudo update-ca-certificates
```
### IMPORTANTE: nas configurações do CodeSpace desabilitar a opção http.proxyStrictSSL

### Extensão do VSCode
[Azure Databases](https://marketplace.visualstudio.com/items?itemName=ms-azuretools.vscode-cosmosdb?wt.mc_id=AZ-MVP-5003638)

### Endpoint do simulador
```
AccountEndpoint=https://localhost:8081/;AccountKey=C2y6yDjf5/R+ob0N8A7Cgv30VRDJIWEHLM+4QDU5DE2nQ9nDuVTqobD4b8mGGyPMbIZnqyMsEcaGQy67XIw/Jw==;
```

### Geração do SSL Key Store
- IMPORTANTE: utilizar a senha univille
```
curl --insecure https://localhost:8081/_explorer/emulator.pem > ~/emulatorcert.crt
sudo cp ~/emulatorcert.crt /usr/local/share/ca-certificates/
sudo update-ca-certificates

cd /workspaces/tapr-2024-turmab-<NOME DO SEU PROJETO>-java
keytool -importcert -file ~/emulatorcert.crt -keystore native.jks -alias cosmosdb
```
- Alterar o arquivo launch.json para incluir os parâmetros de VM
```
    "vmArgs": ["-Djavax.net.ssl.trustStore=/workspaces/tapr-2024-turmab-<NOME DO SEU PROJETO>-java/native.jks",
               "-Djavax.net.ssl.trustStorePassword=univille"]
```

### Modelagem de dados
[Modeling Data](https://learn.microsoft.com/en-us/azure/cosmos-db/nosql/modeling-data?wt.mc_id=AZ-MVP-5003638)

### Particionamento
[Partitioning](https://learn.microsoft.com/en-us/azure/cosmos-db/partitioning-overview?wt.mc_id=AZ-MVP-5003638)

### Configuração do pom.xml
- Realizar o downgrade da versao do Spring Boot
```
    <parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.1.4</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
```

- Incluir as seguintes dependencias
```
<dependencies>
	
		<dependency>
			<groupId>com.azure.spring</groupId>
			<artifactId>spring-cloud-azure-starter-data-cosmos</artifactId>
			<version>5.5.0</version>
		</dependency>
		<dependency>
			<groupId>com.azure</groupId>
			<artifactId>azure-spring-data-cosmos</artifactId>
			<version>5.5.0</version>
		</dependency>

		<dependency>
			<groupId>com.azure</groupId>
			<artifactId>azure-identity</artifactId>
			<version>1.10.0</version>
		</dependency>

		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.1.0</version>
		</dependency>


		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>com.azure.spring</groupId>
				<artifactId>spring-cloud-azure-dependencies</artifactId>
				<version>5.5.0</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>
```

- Criar o arquivo .env na raiz do projeto setando o profile default para dev
```
SPRING_PROFILES_ACTIVE=dev
```
### Erro CORS
- [O que é o erro de CORS](https://pt.wikipedia.org/wiki/Cross-origin_resource_sharing#:~:text=Cross-origin%20resource%20sharing%20%E2%80%93%20Wikip%C3%A9dia%2C%20a%20enciclop%C3%A9dia%20livre,pertence%20o%20recurso%20que%20ser%C3%A1%20recuperado.%20%5B%201%5D)


#### Solução
- Criaruma pasta config
- Criar a classe WebConfig.java
```
package br.edu.univille.microservcolegio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer  {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");

    }
}
```

## CRUD API REST
### Verbo GET e POST
- Objetivo: Retornar uma lista de objetos ou salvar um objeto

#### AlunoService.java
- Criar os métodos na interface do serviço

```
package br.univille.microservcolegio.secretaria.service;
import java.util.List;

import br.univille.microservcolegio.secretaria.entity.Aluno;

public interface AlunoService {
    
    List<Aluno> getAll();
    Aluno save(Aluno aluno);

```
#### AlunoServiceImpl.java
- Implementar a lógica de consulta na classe concreta do serviço
```
package br.univille.microservcolegio.secretaria.service.impl;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.univille.microservcolegio.secretaria.entity.Aluno;
import br.univille.microservcolegio.secretaria.repository.AlunoRepository;
import br.univille.microservcolegio.secretaria.service.AlunoService;

@Service
public class AlunoServiceImpl 
    implements AlunoService{

    @Autowired
    private AlunoRepository repository;

    @Override
    public List<Aluno> getAll() {
        var retorno = repository.findAll();
        List<Aluno> listaAlunos = new ArrayList<Aluno>();
        retorno.forEach(listaAlunos::add);

        return listaAlunos;
    }

    @Override
    public Aluno save(Aluno aluno) {
        return repository.save(aluno);
    }

```

#### AlunoAPIController.java
- Implememntar no controlador os métodos para buscar do banco todos os aluno e salvar um aluno

```
package br.univille.microservcolegio.secretaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.univille.microservcolegio.secretaria.entity.Aluno;
import br.univille.microservcolegio.secretaria.service.AlunoService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/alunos")
public class AlunoAPIController {

    @Autowired
    private AlunoService service;

    @GetMapping
    public ResponseEntity<List<Aluno>> get(){
        var listaAlunos = service.getAll();

        return new ResponseEntity<List<Aluno>>(listaAlunos,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Aluno> post(@RequestBody Aluno aluno){
        if(aluno == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        var alunoSalvo = service.save(aluno);

        return new ResponseEntity<Aluno>(alunoSalvo, HttpStatus.OK);
    }

}
```

#### teste.rest
- Implementação do teste do verbo GET e POST

```
### Buscar todos os alunos
GET http://localhost:8080/api/v1/alunos

### Inserir um aluno
POST http://localhost:8080/api/v1/alunos
Content-Type: application/json

{
    "nome" : "zezinho"
}
```