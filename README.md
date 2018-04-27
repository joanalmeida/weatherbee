# weatherbee

## Dependencias

- Java 1.8
- Instancia de mysql si se quiere persistir

## Corriendo la app

Editar el archivo application-demo.properties

```js
spring.datasource.url=jdbc:mysql://localhost:3306/weatherbee //Apuntar a la instancia correcta de mysql
spring.datasource.username=USUARIO
spring.datasource.password=PASSWORD
```

Desde el root de la app, ejecutar el siguiente comando
```sh
mvn clean package -Dmaven.test.skip=true
java -jar -Dspring.profiles.active=demo target/weatherbee-0.0.1-SNAPSHOT.jar
```

Si se quiere persistir la data, correr con el profile de demo
```sh
java -jar -Dspring.profiles.active=demo target/weatherbee-0.0.1-SNAPSHOT.jar
```

Sino, se puede correr con el perfil por defecto y se utilizara la base en memoria que provee H2
```sh
java -jar target/weatherbee-0.0.1-SNAPSHOT.jar
```
