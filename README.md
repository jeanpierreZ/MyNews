# MyNews

Renouez avec l'actualité

OpenClassrooms Développeur d'application - Android 5ème projet

Enoncé :

Introduction

Vous avez codé toute la journée, la fatigue vous gagne. Vous commencez à loucher et à bailler à vous décrocher la mâchoire.
Il vous faut votre dose de café pour tenir. En sortant de chez vous pour vous rendre au Starbucks du coin, 
vous vous surprenez à lire un grand titre sur une façade de kiosque : “Donald Trump félicite Emmanuel Macron pour sa victoire”. 
Tiens donc, quelle victoire ? D’ailleurs c’est qui Macron ? Et Trump ?

De retour chez vous, votre Latte Glacé à la main devant Wikipedia, vous découvrez avec stupeur que vous avez deux wagons de retard. 
Obama n’est plus le Président américain ? Sarkozy n’est plus le Président français depuis autant d'années ?

Cela ne peut plus durer, il est grand temps d’être à la page. Vous pourriez télécharger une application de news toute faite, 
mais vous préférez la développer vous-même : vous pourrez ainsi déterminer les sujets qui vous intéressent et 
créer une application qui vous ressemble. Vous décidez de la baptiser MyNews.

L'application MyNews

Vous décidez de vous reposer sur les news publiées par le New York Times. Par chance, le New York Times propose une API permettant 
d’accéder à l’ensemble des articles publiés par le journal, et même au-delà : critiques de livres et de films, 
articles les plus partagés, etc. Parmi ces fonctionnalités, vous décidez d’exploiter les trois suivantes : 
Les sujets du moment (via la Top Stories API) ; 
Les articles les plus populaires (via la Most Popular API) ; 
Les articles sur les sujets qui vous intéressent (via la Article Search API).

Une loupe permet d’effectuer une recherche parmi l’ensemble des articles de la base de données du New York Times 
(et non sur les articles affichés à l’écran).

Notifications

Afin de ne passer à côté d’aucune news pertinente, vous décidez d’ajouter une fonctionnalité permettant de vous avertir 
lorsqu’un nouvel article est publié, selon des critères que vous aurez prédéfinis. 
Pour ce faire, vous décidez d’ajouter un nouvel écran de paramétrage, 
accessible depuis le point de menu de l’écran principal de l’application.

Si les notifications sont activées, alors l’application ira vérifier une fois par jour si des news correspondent aux critères 
de recherche, puis vous avertira grâce à un message de notification.

Icon launcher made by Freepik from www.flaticon.com.

Jean-Pierre Zingraff
