# Java Backend project 

## Tecnologías usadas

Java 17 o superior
Gradle 8.0 o superior
Docker
H2 DataBase 

## Ejecución local
1. Clonar el proyecto desde github
2. En una terminal ir al directorio raíz del proyecto
3. Ejecutar **gradlew build** para compilar
4. Ejecutar **gradlew bootRun** para ejecutar
5. El proyecto se ejecuta en el puerto 8080 

## Ejecución con Docker
1. Ejecutar los pasos 1-3 para ejecución local 
2. En una terminal, en el directorio raíz del proyecto, ejecutar los comandos:

``` docker build -t pichincha-test .```

```docker run -d -p 8080:8080 pichincha-test'```

El nombre pichincha-test es el nombre del contenedor que se utilizará, puede reemplazarse con uno de su preferencia

## BDD:

En el directorio src/main/resources se encuentra el script de Base de Datos y archivo de configuraciones

## POSTMAN:
2. Importar el archivo GlobantPichinchaJava.postman_collection.json en Postman para ejecutar pruebas de los endpoints
