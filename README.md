# googlePositionsToMyPositions

Utiliser Google Takeout pour recuperer toutes vos données

https://takeout.google.com/settings/takeout

Une fois l'archive recu et extraite vous pouvez creer un fichier application.properties :

Exemple :

```

ignoreFilePath=C:\\TakeOut\\ignore.csv
#chemin vers le repertoire "Semantic Location History" où sont stockés les fichiers json
sourceFolderPath=source folder
#zone en metre autour des point ignores dans lesquels on ignore les points
radiusIgnoreZone=600

```

Le projet prend en compte des ignorePlaces qui sont configurées dans une table DynamoDB.

Pour finir on les insere dans DynamoDB, avant de les inserer on verifie qu'une entrée n'existe pas deja. 

On peut donc fournir à chaque fois l'export complet de google sans creer de doublons.

Pour lancer le programme : 

```

java -jar .\googleLocationsExtractor-0.0.1-SNAPSHOT.jar --spring.config.location=C:\path\application.properties

```

Exemple de fichier ignore.csv :

```

maison;48.1;2.555555
bretoncelles;48.6666666;0.25555

```