# googlePositionsToMyPositions

Utiliser Google Takeout pour recuperer toutes vos données

https://takeout.google.com/settings/takeout

Une fois l'archive recu et extraite vous pouvez creer un fichier application.properties :

Exemple :

```
#chemin vers le fichier indiquant les endroits à ignorer lors de l'indexation
ignoreFilePath=ignore file path
#chemin vers le repertoire "Semantic Location History" où sont stockés les fichiers json
sourceFolderPath=source folder
#zone en metre autour des point ignores dans lesquels on ignore les points
radiusIgnoreZone=600

```

Puis il faut generer le fichier ignoreFilePath, il doit etre de la forme :

```

47.1,2.1,libelle
lattitude,longitude,libelle
lattitude,longitude,libelle

```

Chaque Ligne representant un endroit que l'on souhaite ne pas indexer.


Pour finir on les inser dans DynamoDB, avant de les inserer on verifie qu'une entré n'existe pas deja, on
peut donc fournir à chaque fois l'export complet de google sans creer de doublons.
