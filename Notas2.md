# Introduccion a Maven

Esto es solo un apunte de la guia de maven [Iniciando con Maven](https://maven.apache.org/guides/getting-started/)Por favor lee eso si te parece que alguna anotacion hecha aqui no tiene sentido
o es muy vaga. Tambien, se aconseja que hallas leido lo que hay en *Notas.md* primero para familiarizarte con el *POM*

## El *POM*

Para dar mas detalle en el tema del archivo pom

>````
><project xmlns="http://maven.apache.org/POM/4.0.0" >xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 >http://maven.apache.org/xsd/maven-4.0.0.xsd">
>  <modelVersion>4.0.0</modelVersion>
>  <groupId>com.mycompany.app</groupId>
>  <artifactId>my-app</artifactId>
>  <version>1.0-SNAPSHOT</version>
>  <name>my-app</name>
>  <!-- FIXME change it to the project's website -->
>  <url>http://www.example.com</url>
>  <properties>
>    <project.build.sourceEncoding>UTF-8</project.build.>sourceEncoding>
>    <maven.compiler.release>17</maven.compiler.release>
>  </properties>
>  <dependencyManagement>
>    <dependencies>
>      <dependency>
>        <groupId>org.junit</groupId>
>        <artifactId>junit-bom</artifactId>
>        <version>5.11.0</version>
>        <type>pom</type>
>        <scope>import</scope>
>      </dependency>
>    </dependencies>
>  </dependencyManagement>
>  <dependencies>
>    <dependency>
>      <groupId>org.junit.jupiter</groupId>
>      <artifactId>junit-jupiter-api</artifactId>
>      <scope>test</scope>
>    </dependency>
>    <!-- Optionally: parameterized tests support -->
>    <dependency>
>      <groupId>org.junit.jupiter</groupId>
>      <artifactId>junit-jupiter-params</artifactId>
>      <scope>test</scope>
>    </dependency>
>  </dependencies>
>  <build>
>    <pluginManagement><!-- lock down plugins versions to >avoid using Maven defaults (may be moved to parent pom) -->
>       ... lots of helpful plugins
>    </pluginManagement>
>  </build>
></project>
>

Esto incluye varios de los elementos clave que debe de tener un archivo *POM*_(Project Object Model)_ apropiado. estos son
- *project* este es la tag que engloba todas las otras tags que usara nuestro projecto
- *modelVersion* indica la version del POM que estas usando
- *groupId* es el nombre de la organizacion que creo el projecto en cuestion y es parte del nombre completo del projecto en general
- *artifactId* es el nombre del _"artefacto"_ principal que sera creado con este projecto usualmente son artefactos de tipo JAR pero otros tipos de archivos tambien lo usaran como su nombre, un artefacto generado por maven usualmente tendre un nombre que sigue el patron ``<artifactId>-<version>.<extension>` 
- *version* indica en que version de tu projecto te encuentras por ahora, por lo general solo tiene escrito _"SNAPSHOT"_ para simbolizar que el projecto todavia esta en desarrollo
- *name* indica el nombre que se mostrara, y es usado por maven para generar documentación.
- *url* direccion al sitio web donde descargar el projecto, tambien aparece en la documentación de maven.
- *dependencies* es donde se listan las dependencias de tu projecto.
- *build* maneja cosas como aclarar la estructura de deirectorios de tu prjecto por ejemplo.

estas son algunas de las tags clave, pero hay muchas MUCHAS (creo que demaciadas) mas. A si que aqui dejo el link de la pagina de referencia del *POM* (si vas a seguir trabajando con maven aconsejaria dejarla en los favoritos de tu buscador)
[Lista de Tags del *POM*](https://maven.apache.org/ref/3.9.9/maven-model/maven.html)

### Comandos rapidos
recuerda que puedes compilar tu codigo con el comando mvn compile, pero eso no compila las tests, eso lo haces con el comando mvn test. Si solo deseas compilar las test pero no correrlas usas mvn test-compile. Puedes crear un JAR con el comando mvn package creara un JAR y lo dejara en el escritorio eclarado como "target", y usar el comando mvn clean para eliminar los constenids del targer antes de continuar

## Que es el SNAPSHOT

Es una convencion de maven para aclarar que el projecto aun es inestable  y en proceso de desarrollo.
    Un projecto con la etiqueta SNAPSHOT, se presume como en dearrollo y 
por lo general tendra un nombre del estilo version == x.y.SNAPSHOT para aclarar
que todavia esa en proceso de desarrollo y que no hay garantias de que funcione,
por el contrario una version final simplemente quitara el SNAPSHOT de su final.
Luego cuando se vuelva a desarrollar se puede seguir algo como 
x.(y+1).SNAPSHOT, luego cuando esta estable se le quita y asi.

## Uso de plugins

Estos sirven para customisar el proceso de creacion de artifacts de maven
para esto o bien configuras o agregas plugins. 
El ejemplo a continuación permite hacer que maven acepte 
programas escritos en java 5.0

>``
...
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-compiler-plugin</artifactId>
      <version>3.3</version>
      <configuration>
        <source>1.5</source>
        <target>1.5</target>
      </configuration>
    </plugin>
  </plugins>
</build>
...
>

La seccion que dice `<configuration></configuration>` esta dedicada a pasar argumentos para maven ( tengo entendido )

## Como añader recursos en el JAR 

Si estas haciendo cualquier tipo de programa que requiera datos extra, como 
assets o archivos de configuracion, necesitas saber esto

Basicamente maven ya tiene una carpeta dedicada a recursos junto con su estructura estandar.
esta carpeta es donde encontrar debes dejar tus recursos durante el diseño, 
luego maven empaquetara en el target lo
que sea que este en esa carpeta al mismo nivel que el JAR. Presisamente
guardara lo que este en la carpeta que le diga
`${project.baseDir}/src/main/resources`
recuerda que los recursos de la carpeta
main no son los mismos que estan en la carpeta
test/resources a si que toma eso en cuenta si estas tieniendo problemas con no encontrar
recursos al momento de hacer pruebas.
Estos recursos son colocados en la base del JAR a si que son referenciados iniciando con _*"/"*_
al comienso de su nombre ( no te guies por como se ven en el target, a mi me confundio eso al comienso ). ( un ejemplo de uso seria si esas haciendo un videojuego y quieres guardar los assets
en el JAR para poder referenciarlos luego )

### NOTA 
 
en caso de que tu projecto no tenga el paquete de recursos por defecto, solo crealo a mano.
como pom sigue la estructura marcada mas arriba, este lo leera de una sin tener que configurar
nada.

## Como filtrar recursos

A veces necesitas pasarle valores a un archivo, pero esos valores solo pueden pasarse durante
el proceso de compilado, para esto maven ofrece el siguiente procedimiento

>``
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.mycompany.app</groupId>
  <artifactId>my-app</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>jar</packaging>
  <name>Maven Quick Start Archetype</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
  <build>
    <resources>
      <resource>
        <directory>src/main/resources</directory>
        <filtering>true</filtering>
      </resource>
    </resources>
  </build>
</project>
>

En este ejemplo se a primero, con la etiqueta `<filtering>true</filtering>` se a aclarado que 
se tiene que filtrar datos.

la etiqueta build aclara que todo lo que esta adentro son cosas relacionadas con el _"buildong process"_ y la etiqueta resources sobre los recursos que se van a tocar.
    Si bien tubimos que aclarar todo, por defecto maven ya sabe donde buscar los recursos, 
solo paso que ahora lo tuvimos que aclarar por haber cambiado el estado de filtering
el cual se encuentra en false como estado base.
    Todo lo referenciado en el *POM* se referencia con su nombre dado durante su definicion,
pom es aceptado como un alias para referirse a _"Objeto base del projecto"_ 
el caso que estaras viendo en el projecto aqui son solo pruebas, que sirven para definir propiedades
por fuera de el *POM*

tambien puedes definir valores desde el *POM* si no te parece que llena demaciado el archivo
EJ
>``
  <properties>
    <my.filter.value>hello</my.filter.value>
  </properties>
>

## Dependencias externas

Son las cosas que nuestro projecto necesita para trabajar, son declaradas en el pom como en el
siguiente ejemplo

>``
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <groupId>com.mycompany.app</groupId>
  <artifactId>my-other-app</artifactId>
  ...
  <dependencies>
    ...
    <dependency>
      <groupId>com.mycompany.app</groupId>
      <artifactId>my-app</artifactId>
      <version>1.0-SNAPSHOT</version>
      <scope>compile</scope>
    </dependency>
  </dependencies>
</project>
>

El programa primero revisara si la dependencia ya esta instalada, y si no lo esta intentara instalarla
por cuenta propia descargandola desde el [depositorio default](https://repo.maven.apache.org/maven2/) 
o desde algun repositorio que hallas conigurado.

las primeras 3 tags son las mismas que usas para declarar un projecto, pero el tag `<scope></scope>`
es el tag que aclara como es que tu projecto la usara puede tener valores como _compile, test o runtime_

## Publicar en un repositorio online, documentación y otras apps

Esto esta un poco bastante en el futuro si es que estas leyendo esto, a si que solo volvere
a dejar el link donde puedes leer sobre esto [Link](https://maven.apache.org/guides/getting-started/)

## Multiples builds

Esto se discutia en el Notas 1.