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