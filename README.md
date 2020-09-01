# googlePositionsToMyPositions

Utiliser Google Takeout pour recuperer toutes vos données

https://takeout.google.com/settings/takeout

Une fois l'archive recu et extraite vous pouvez creer un fichier application.properties :

Exemple :

```

#chemin vers le repertoire "Semantic Location History" où sont stockés les fichiers json
sourceFolderPath=source folder
#zone en metre autour des point ignores dans lesquels on ignore les points
radiusIgnoreZone=600

```

Le projet prend en compte des ignorePlaces qui sont configurées dans une table DynamoDB.

Pour finir on les insere dans DynamoDB, avant de les inserer on verifie qu'une entrée n'existe pas deja. 

On peut donc fournir à chaque fois l'export complet de google sans creer de doublons.
