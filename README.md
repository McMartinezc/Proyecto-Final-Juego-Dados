# Proyecto API - Juego de Dados

Este proyecto consiste en el desarrollo de una API que permita jugar al "juego de dados". En este juego, se lanzan dos dados y, si la suma de ambos es igual a 7, el jugador gana la partida; de lo contrario, la pierde. Los jugadores tienen la opción de registrarse con un nombre único o de forma anónima. Cada jugador puede realizar tiradas de dados, y el sistema mantendrá un registro de todas ellas, incluyendo los valores de los dados y el resultado de la partida.

## Funcionalidades Principales

1. Registro de Jugadores: Los usuarios pueden registrarse con un nombre único o de forma anónima. Se les asignará un identificador numérico único y una fecha de registro.

2. Realizar Tiradas: Los jugadores registrados pueden realizar tiradas de dados, y el sistema guardará un registro de cada tirada con los valores de los dados y el resultado (ganada o perdida).

3. Listar Tiradas y Calcular Porcentaje de Éxito: Cada jugador puede ver un listado de todas sus tiradas realizadas y calcular su porcentaje de éxito en el juego.

4. Eliminar Tiradas de un Jugador: Los jugadores pueden eliminar el listado de tiradas realizado, pero no pueden eliminar tiradas individuales.

5. Listar Jugadores y sus Porcentajes de Éxito: El sistema permitirá listar todos los jugadores registrados junto con su porcentaje de éxito promedio en las tiradas.

6. Ranking de Jugadores: Se podrá obtener el ranking medio de todos los jugadores en el sistema, tanto el jugador con el peor porcentaje de éxito como el que tiene el mejor.

## Fases de Desarrollo

- Fase 1: Utilizar una base de datos MySQL para persistir los datos.
- Fase 2: Cambiar la persistencia a MongoDB para almacenar los datos.
- Fase 3: Agregar seguridad a la API mediante autenticación JWT en todas las rutas de acceso.

El proyecto se diseñará siguiendo los principales patrones de diseño para garantizar una arquitectura robusta y escalable. Cada fase del desarrollo se implementará de forma incremental, y se realizarán pruebas exhaustivas para asegurar su correcto funcionamiento. Además, se seguirán buenas prácticas de programación y se documentará adecuadamente el código para facilitar la colaboración y el mantenimiento a lo largo del tiempo.
