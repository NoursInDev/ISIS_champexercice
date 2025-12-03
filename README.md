Cet énoncé fait suite à l’exercice UML « Champollion » traité en TP.

# Énoncé
![Figure 1](./uml.png)

*Figure 1 : diagramme UML simplifié*

La Figure 1 est un extrait simplifié de la correction de l’exercice UML « Champollion ».

Le diagramme UML illustre que le service prévu d’un enseignant définit quel volume horaire cet enseignant effectuera dans les UE, décliné en heures de Cours Magistral (`CM`), Travaux Dirigés (`TD`) et Travaux Pratiques (`TP`). Quand on planifie le service prévu d’un enseignant, on crée des Interventions qui précisent la date / heure, l’UE, la salle et le type de l’intervention concernée. Le système doit permettre de comparer le service prévu avec le service planifié pour un enseignant.

Dans la classe « `Enseignant` » :

- La méthode « `ajouteEnseignement` » permet de rajouter un enseignement au _service prévu_ d’un enseignant.
- La méthode « `heuresPrevues` » calcule le service prévu total d’un enseignant en heures « équivalent TD »  :
    - 1h de cours magistral vaut 1,5h « «équivalent TD »
    - 1h de TD vaut 1h «équivalent TD »
    - 1h de TP vaut 0,75h «équivalent TD »
    - Les calculs sont arrondis à l’entier le plus proche.
- La méthode « `heuresPrevuesPourUE` » fait le même calcul, mais en se limitant à l’UE passée en paramètre.
- La méthode « `ajouteIntervention` » ajoute une intervention planifiée pour cet enseignant. Quand on ajoute une intervention à un enseignant, on doit vérifier qu’on n’excède pas le service prévu pour cet enseignant, cette UE et ce type d’intervention. Dans le cas contraire, on lèvera une exception appropriée. On développera des tests unitaires pour vérifier que cette contrainte est respectée.
- La méthode « `resteAPlanifier` » donne la différence de volume horaire entre le service prévu et les interventions planifiées pour cet enseignant, avec l'UE et le type d’intervention fournis en paramètre.
- Un enseignant est en sous-service si son nombre d’heures prévues est inférieur à 192.

Vous disposez d’un squelette d’implémentation java. La classe `champollion.Main` donne un exemple d’utilisation des autres classes. Un jeu de test unitaire partiel est également fourni.

# Exercice

Complétez l’implémentation fournie, en respectant au mieux le diagramme de classes UML de la Figure 1. La classe `champollion.Main` doit être compilable et exécutable sans modification.

Développez les tests unitaires les plus complets possibles pour votre implémentation, en assurant une couverture de test de 100 % pour la classe « `Enseignant` ».

## A rendre

Adresse de votre dépôt github.