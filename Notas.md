# Notas sobre Maven

Estas son mis notas despues de leer el tutorial de ["introduccion al *POM*"](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html) de maven. Si quieres una version mas en
detalle, ve a ver esa versión.

### Uso
Usas maven para independizar a tu equipo de trabajo de tipos de IDEs,
controlar versiones de projecto, controlar releases, organizar projectos por modulos y controlar dependencias de projecto.

### Introduccion ( *El POM* )
Un archivo tipo *POM* es un .xml que usa Maven para saver como traar a tu projecto, contiene toda o casi toda la informacion que maven necesita para saver como trabajar con tu projecto. 
    Puedes crear un *POM* basico con el quick-start de maven pero tambien
existe el *POM* mas basico posible EJ:
>&lt;project&gt;
>
>   &lt;modelVersion&gt;4.0.0&lt;/modelVersion&gt;
>
>   &lt;groupId&gt;example-name&lt;/groupId&gt;
>   &lt;artifactId&gt;example-name&lt;/artifactId&gt;
>   &lt;version&gt;algun numero&lt;/version&gt;
>
>&lt;/project&gt;
Donde la version de modelo o <modelVersion>de momento es obligatorio que sea "4.0.0"
Un *POM* tambien puede especificar cosas como "donde estan los sources" 
o "donde dejo la compilacion" Esto en caso de no especificarse, se resuelve siguiendo la estructura estandar de directorios de maven la cual se ve en este ejemplo.
    Estos datos Maven los obtiene del "*SUPER POM*" que es un *POM* que
existe por defecto junto con maven que trae la configuracion Default
todos los *POM* heredan de el si no se les aclara un padre.

<span style = "color:Red" > NOTA: el tema de herencia esta escrito en el *Notas.md* de el projecto hijo de este </span> 

## Agregación de projectos ( *Si no puedo subdividir esto se volvera un lio!!!* )

Primero, esto asegura que los comandos corridos contra el padre
se ejecuten contra los hijos tambien, a si no tienes que hacer algo como
compilar primero a los hijos y despues al padre.
    Segundo, esto tiene una serie de pasos para seguir si deseas hacerlo bien
1. Cambiar el "_Packaging_" del padre al valor "*pom*"
2. Especificar en el padre los directorios de sus submodulos

para ejemplo lease el pom de aqui.

Como resumen de esto, ahora tenemos tambien las nuevas tags de
_&lt;module&gt;&lt;/module&gt;_ en la cual escribes el path relativo a la 
carpeta donde se contiene el *POM* de nuestro modulo (Se que en
el tutorial dice que es hasta el *POM* del modulo, pero el mismo
tutorial solo escribe hasta el directorio donde esta contenido a 
si que nos quedamos con eso)

## Agregacion VS Herencia:

Estas herramientas nos sirven por que por un lado, la herencia nos deja
tener un solo modulo con las configuraciones comunes, y luego limitarnos a
referenciarlo en sus hijos, dejando que sus hijos solo tengan las configuraciones unicas de estos.
    La Agregacion nos permite subdividir un projecto en varios 
mini projectos dependientes de este o no que pueden ser ahora compilados
con solo un comando, simplificando el proceso de compilado. 
    Como puedes por tanto tener tanto *herencia* como *agregacion*,
sirve tener una lista de pasos para cuando tienes esa situación.
    Para poder tener hijos de un padre, y ese padre pueda interactuar con
sus hijos, debes de seguir los siguientes pasos.
- Cambia el _&lt;packagin>_ del *POM* padre a _*pom*_
- Referencia al padre en cada hijo con 
>&lt;parent&gt;
>   &lt;artifactId&gt;algo&lt;/artifactId&gt;
>   &lt;groupId&gt;algo&lt;/groupId&gt;
>   &lt;version&gt;algo&lt;/version&gt;
>   &lt;relativePath&gt;solo si tu estructura no es la normal&lt;/relativePath&gt;
>&lt;/parent&gt;
- recuerdale al padre quienes son sus hijos y sus ubicaciónes con:
>&lt;modules&gt;
>   &lt;module&gt;moduelo1&lt;/moduel&gt;
>   ....
>   &lt;module&gt;modulon&lt;/moduel&gt;
>&lt;/modules&gt;

### Notas de lo que interprete del tutorial:
No es necesario que los modulos esten relacionados, aveces puede que 
solo quieras compilar varios projectos simultaneamente, y para eso
puedes convertirlos a todos en hijos de un projecto padre ( un ejemplo que
se me ocurre seria como cuando en SO tenias varios modulos que no 
necesariamente dependian de un modulo padre, pero te interesaba tratrlos 
todos como un grupo ).
    Mismo caso con herencia, no tienen por que ser todos tus projectos 
sub projectos de otros, puede darse que simplemente tengas muchos projectos
con similares configuraciones, y te sea mas facil tener un solo pom
donde tengas escritas esas configuraciones ( Ejemplo, si todos tus
projectos dependen de jUnit5 pero sacaron una nueva version, seria mas 
comodo tener un solo archivo con esos datos y referenciarlo, a tener que 
actualizar todos tus archivos de manera individual cada que sale una 
nueva version de jUnit5 ).

## Variables

Maven trata de que no te repitas, pero eso no es posible a si que 
maven a dejado herramientas para hacer el repetirse lo menos doloroso,
esas son las variables. 
    Como cosa a notar antes de seguir, es que las variables son definidas
para maven <span style = "color:orange" >*despues* </span>de aplicar la 
herencia, lo que qiere decir que las definiciones de los hijos "Pisan" o 
remplazan a las del padre, si es que los hijos estan usando variables
que usa el padre.
    Tienes algunas variables especificas de projecto, las cuales 
son faciles de reconocer por que empiesan con el sufijo <span style = "color:green">_project._</span> y son para setear cosas del projecto como
lo pueden ser por ejemplo el groupId, el artifact, el formato de fecha,
etc. 
    Un ejemplo es la _maven.build.timestamp.format_ la cual para usarla,
necesitas apegarte a los formatos aclarados [aqui](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)
y define el formato en el que se escribira la fecha de una build

luego puedes hacer cosas como definir tus propias variables, eso se hace
con la tag &lt;properties&gt;&lt;/properties&gt; un codigo de ejemplo seria
>&lt;properties&gt;
>   &lt;variable1&gt;.....&lt;/variable1&gt;
>   ......
>   &lt;variableN&gt;.....&lt;/variableN&gt;
>&lt;/properties&gt;
luego una vez ya definidas, las variables son referenciadas en codigo usando "<span style = "color:orange">" {$nombre_de_tu_variable}</span>
Este mismo principio aplica para las variables de projecto.
