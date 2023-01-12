# Joc de daus MongoDb

## Objectius
- Reforçar el coneixement de la utilització de JPA
- Aprendre a utilitzar bases de dades NoSQL


## Descripció
- El joc de daus s’hi juga amb dos daus. En cas que el resultat dels dos daus sigui 7, la partida és guanyada, sinó és perduda. Per poder jugar al joc, t’has de registrar com a user amb un nom. Un user pot veure un llistat de totes les tirades que ha fet i el percentatge d’èxit.  

- Per poder realitzar una tirada, un usuari s’ha de registrar amb un nom no repetit. Al crear-se, se l’hi assigna un identificador numèric únic i una data de registre. Si l’usuari així ho desitja, pots no afegir cap nom i es dirà “ANÒNIM”. Pot haver-hi més d’un user “ANÒNIM”.
- Cada user pot veure un llistat de totes les tirades que ha fet, amb el valor de cada dau i si s’ha guanyat o no la partida. A més, pot saber el seu percentatge d’èxit per totes les tirades que ha realitzat.
- No es pot eliminar una partida en concret, però si que es pot eliminar tot el llistat de tirades per un user.
- El software ha de permetre llistar tots els users que hi ha al sistema, el percentatge d’èxit de cada user i el percentatge d’èxit mig de tots els users en el sistema.
- El software ha de respectar els principals patrons de disseny.


## Notes
Has de tindre en compte els següents detalls de construcció:

- Crea un user
```
POST /players/addJugador
```
 
- Modifica el nom del user
```
PUT /players/update/{id}
```

- Un user específic realitza una tirada dels daus.
```
POST /players/tiradaDaus/{id}
```
 
- Elimina les tirades del user
```
DELETE /players/deleteTirades/{id}
```

- Retorna el llistat de tots els users del sistema amb el seu
  percentatge mig d’èxits
```
GET /players/
```

- Retorna el llistat de jugades per un user.
```
GET /players//tiradesJugador/{id}
```

- Retorna el ranking mig de tots els users del sistema.
  És a dir, el percentatge mig d’èxits.
```
GET /players/ranking
```

- Retorna el user amb pitjor percentatge d’èxit 
```
GET /players/ranking/loser
``` 

- Retorna el user amb pitjor percentatge d’èxit
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