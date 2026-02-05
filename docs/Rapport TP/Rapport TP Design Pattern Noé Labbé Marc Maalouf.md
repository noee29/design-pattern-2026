**Refactored CodeBase :**

Nous avons ajouté des blocs try-catch pour gérer les erreurs et éviter les crashs du système.  
Le code redondant a été supprimé et les fonctionnalités ont été séparées en méthodes distinctes afin d'améliorer la lisibilité et la maintenance.  
Les éléments susceptibles d'être modifiés par l'utilisateur, comme le menu ou le format d'affichage, ont été isolés dans des méthodes dédiées.

**Test Unitaire :**

Concernant la validation de la logique métier dans la classe GameCollection, les tests unitaires ont d'abord permis de s'assurer que les manipulations de données de base fonctionnent correctement. En particulier, nous avons vérifié que l'ajout d'un jeu avec la méthode addGame augmente bien la taille de la collection et que l'objet ajouté correspond à celui attendu. De la même manière, la méthode removeGame a été testée afin de confirmer qu'elle supprime uniquement le jeu ciblé, sans modifier les autres éléments présents dans la collection. Ces tests permettent de garantir l'intégrité des données manipulées par l'application.

Les cas limites ont également été pris en compte, notamment lorsque la collection est vide. En utilisant la méthode clear, nous avons testé des fonctionnalités comme getGames et viewAllGames afin de vérifier qu'elles ne provoquent pas d'erreurs. Cela permet de s'assurer que l'application reste fonctionnelle lors d'un premier lancement, lorsqu'aucune donnée n'est encore enregistrée, ce qui correspond à une situation réaliste pour un nouvel utilisateur.

Un autre point important concerne la gestion des entrées et sorties liées aux fichiers de sauvegarde. Des tests spécifiques, comme loadFromFile_shouldNotCrashWhenFileDoesNotExist, ont été mis en place pour vérifier que l'absence d'un fichier JSON ou CSV n'entraîne pas l'arrêt du programme. Ces tests renforcent la robustesse de l'application face à des erreurs courantes, comme un fichier manquant ou non encore créé.

Pour la partie liée au menu, les tests ont cherché à reproduire des scénarios d'utilisation plus complets. Par exemple, le test multipleOperations_shouldHandleAddAndRemove simule une suite d'actions comprenant plusieurs ajouts et suppressions de jeux. L'objectif est de vérifier que l'état global de l'application reste cohérent après ces différentes opérations.

Enfin, la logique de recherche a été testée de manière isolée grâce à la méthode findGameByTitle. Ces tests permettent de s'assurer que la recherche renvoie uniquement les résultats attendus, sans retourner de jeux incorrects. En complément, des tests utilisant assertDoesNotThrow sur certaines méthodes d'affichage garantissent que la navigation dans les menus ne génère pas d'erreurs.

**Lien du projet Git :**

<https://github.com/noee29/design-pattern-2026.git>

------------------------------------------------------------------------------
**Rapport TP 2 Marc Maalouf Noé Labbé 02/02/26**

**Refactored CodeBase :**

**Dans un premier temps, nous avons refactorisé la base de code en supprimant l’utilisation excessive des éléments statiques et en réorganisant l’application afin de mieux séparer les différentes responsabilités.**

**Les fonctionnalités ont été réparties dans plusieurs classes cohérentes, chacune ayant un rôle bien défini. Cette organisation permet d’améliorer la lisibilité du code et respecte le principe de responsabilité unique (SRP).**

**Elle rend également l’application plus simple à maintenir et plus facile à faire évoluer par la suite.**

**Test Unitaire :**


**Nous avons mis en place des tests unitaires en utilisant le pattern AAA.**



**Les dépendances externes ont été simulées avec Mockito afin d’isoler les tests.**



**Les classes d’entrée/sortie (UserInput et GamePrinter) ont été exclues ou testées minimalement, car elles ne contiennent pas de logique métier.**