# Joc de daus MongoDb

## Objectius
- Reforçar el coneixement de la utilització de JPA
- Aprendre a utilitzar bases de dades NoSQL


## Descripció
- El joc de daus s’hi juga amb dos daus. En cas que el resultat dels dos daus sigui 7, la partida és guanyada, sinó és perduda. Per poder jugar al joc, t’has de registrar com a jugador amb un nom. Un jugador pot veure un llistat de totes les tirades que ha fet i el percentatge d’èxit.  

- Per poder realitzar una tirada, un usuari s’ha de registrar amb un nom no repetit. Al crear-se, se l’hi assigna un identificador numèric únic i una data de registre. Si l’usuari així ho desitja, pots no afegir cap nom i es dirà “ANÒNIM”. Pot haver-hi més d’un jugador “ANÒNIM”.
- Cada jugador pot veure un llistat de totes les tirades que ha fet, amb el valor de cada dau i si s’ha guanyat o no la partida. A més, pot saber el seu percentatge d’èxit per totes les tirades que ha realitzat.
- No es pot eliminar una partida en concret, però si que es pot eliminar tot el llistat de tirades per un jugador.
- El software ha de permetre llistar tots els jugadors que hi ha al sistema, el percentatge d’èxit de cada jugador i el percentatge d’èxit mig de tots els jugadors en el sistema.
- El software ha de respectar els principals patrons de disseny.


## Notes
Has de tindre en compte els següents detalls de construcció:

- Crea un jugador
```
POST /players/addJugador
```
 
- Modifica el nom del jugador
```
PUT /players/update/{id}
```

- Un jugador específic realitza una tirada dels daus.
```
POST /players/tiradaDaus/{id}
```
 
- Elimina les tirades del jugador
```
DELETE /players/deleteTirades/{id}
```

- Retorna el llistat de tots els jugadors del sistema amb el seu
  percentatge mig d’èxits
```
GET /players/
```

- Retorna el llistat de jugades per un jugador.
```
GET /players/tiradesJugador/{id}
```

- Retorna el ranking mig de tots els jugadors del sistema.
  És a dir, el percentatge mig d’èxits.
```
GET /players/ranking
```

- Retorna el jugador amb pitjor percentatge d’èxit 
```
GET /players/ranking/loser
``` 

- Retorna el jugador amb pitjor percentatge d’èxit
```
GET /players/ranking/winner
```


## Fases
FASE 1  
• **Persistència**: utilitza com a base de dades mysql 

FASE 2  
• **Canvia la configuració** i utilitza MongoDB per persistir les dades

##Recursos

https://stackoverflow.com/questions/8384029/auto-increment-in-mongodb-to-store-sequence-of-unique-user-id

https://java.hotexamples.com/es/examples/org.springframework.data.mongodb.core/MongoOperations/-/java-mongooperations-class-examples.html
