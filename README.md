# Indra
La aplicación esta construida a partir del patrón de arquitectura MVVM. En esta se implementan patrones de diseño, con la ayuda de dagger2 que es inyección de dependencia, así mismo implementamos otros más como Singleton y Factory.
Para la persistencia de datos se ocupo la librería Room y para los servicios REST se ocupa Retrofit
El diseño se creo a partir de ejemplos de material-components
Cosas por hacer:
-	Hay clases que por falta de tiempo ya no se depuraron a nivel tanto archivo, como código. Se necesita un poco más de tiempo para eliminar redundancias, etc. Tal vez con herramientas como SonarQube.
-	No se crearon pruebas unitarias a nivel estres y falto el uso de librerías:
Las pruebas unitarias las realizaría con Mockito y para la creación de librerías crearía mi modulo el cual encapsularía código reutilizable para cualquier aplicación subiendo el repositorio a JitPack el cual genera diferentes variantes aar para n arquitecturas.
Todo el código escrito por mi está en Kotlin.

Derechos reservados a Ulai Sem Nava Campos

