#  Union - Find

Introduction
==

Le union-find est un principe algorithmique utilisant une structure de donnée d'arbre de pere pour créer un systeme ensembliste. Deux opération sont utilisé dans cet ensemble:
	FIND(x) : pour retourner le pere de l'ensemble
	UNION(x,y) : pour regrouper x et y dans le meme ensemble si il n'y sont pas

L'objectif du projet est d'implémenter une structure de union-find dans l'objectif de mettre au point un algorithme optimal pour compter le nombre de tache noir dans une image en noir et blanc



Mode d'emploi
==

Pour compiler le programme, il suffit de lancer un terminal, se positionner dans le repertoire qui contient Main.java, et lancer la commande:

 " javac Main.java "

Pour executer le programme, on procede comme suit :

 " java Main image R N"

image correspond a l'image ou l'on veut compter les tache noir

N est un entier qui correspond au plus petit nombre de pixel noir que le programme contera dans son calcul

R correspond au voisin (choisir 1 ou 2 a chaque fois)

par exemple, on peut lancer :

java Main Image_test/cells-inv 2 15

