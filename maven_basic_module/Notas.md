# Notas para el modulo
El modulo en cuestion, es otro projecto del cual nuestro projecto padre
depende, el modulo hijo hereda las caracteristicas definidas en el *POM*
del padre, a si salvandonos de tener que copiar dependencias
El pom hijo tiene que referenciar a su padre como tal, tambien
como es hijo del padre, este puede no incluir ID de grupo ni version ni artifactId, y se 
presumira que esta herendando las del padre. 
El hijo buscara a su padre si se suigue la estructura que aqui se presenta,
donde es un directorio hijo dentro del directorio padre,
pero de no tenerlos asi, va a tener que aclararse una ruta 
con la tag _"<relativePath>"_ la cual sirve para escribir un path relativo
el cual tiene que terminar en el *POM* de el direcotrio padre

## Etiquetas importantes de recordad
1. _*<parent></parent>*_ _(Aclara el padre del modulo)_
2. _*<relativePath><relativePath>*_ _(Aclara el path al *POM* padre, relativo al hijo)_

## Resumen
Esto es solo para que el *POM* hijo pueda reconocer y heredar del padre.
Para hacer que un padre reconosca a un hijo y pueda usarlo, este debe de 
tener escrito en su *POM* sobre este, esto se menciona en las notas
del projecto padre de este.