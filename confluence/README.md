# integrations

Integration apps for various web services

## confluence api token

create a api token for confluence here:


https://id.atlassian.com/manage-profile/security/api-tokens

i.e.
```
token = "ATATT3xFfGF0P1yh-d4n4dExXImx9GoTBk5azzjucSOmJnfLV5aRrT6Fnjoaehs3pYCoiJVU7qvAu04k9HE-1suWAv1n4rncMTPveLHDpVhkK06cnQ4EohkzbnpgScAM4nnR7xcZslAeFgE5ojsmco2yNRa6kamH313En0RxbXFTQ8qDNB_Ar"
```

set:

`export CONFLUENCE_API_TOKEN="YOUR-API-TOKEN"`

get:

`echo $CONFLUENCE_API_TOKEN`


### application.properties
confluence.api-token=${CONFLUENCE_API_TOKEN}

`String apiToken = System.getenv("CONFLUENCE_API_TOKEN");`




## run with maven:

```
mvn clean install
mvn spring-boot:run
```


go to url:

http://localhost:8080



## api endpoints:

get spaces:

`curl -v http://localhost:8080/spaces`

create a page:

`curl -v -X POST http://localhost:8080/createPage/TEST/MyPage`



## build the jar and run:

```
maven clean build

ls -lb target/*.jar

java -jar target/confluenceIntegrationDemo-0.0.1-SNAPSHOT.jar
```

## java version 17:

```
sdk install java 17.0.12-oracle\n

sdk use java 17.0.12-oracle\n

sdk default java 17.0.12-oracle\n

java --version
```



Constructs an HTTP client with the Basic Auth header.
Fetches a list of Confluence spaces (as a simple test).
Creates a page in Confluence.








