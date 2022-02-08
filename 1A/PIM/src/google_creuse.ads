with Vecteurs_Creux;

generic
   Capacite : Integer;
   type Double is digits <>;

package Google_Creuse is

   type T_Google is limited private;
   Type T_Vecteur is limited private;

   -- Initialiser la matrice en initialisant chaque vecteur creux
   -- Paramètres :
   --      M : out T_Goole
   procedure Initialiser ( M : out T_Google);


   -- Initialiser le vecteur en initialisant chaque termes à N
   -- Paramètres :
   --      V : out T_Vecteur
   --      N : in Double
   procedure Initialiser ( V : out T_Vecteur; N : in Double);


   -- Détruire la matrice M
   -- Paramètres :
   --      M : in out T_Google
   procedure Detruire (M : in out T_Google);


   -- Récupérer la composante de la matrice à l'indice (I,J)
   -- Paramètres :
   --      M : in T_Google
   --      I, J : in Integer
   function Composante (M : in T_Google; I,J : in Integer) return Double;


   -- Récupérer la composante du vecteur à l'indice I
   -- Paramètres :
   --      M : in T_Google
   --      I : in Integer
   function Composante (V : in T_Vecteur; I : in Integer) return Double;


   -- Modifier la composante (I,J) de la matrice par la Valeur
   -- Paramètres :
   --      M : in out T_Google
   --      I, J : in Integer
   --      Valeur : in Double
   procedure Modifier (M : in out T_Google; I,J : in Integer; Valeur : in Double);


   -- Modifier la composante (I,J) de la matrice par la Valeur
   -- Paramètres :
   --      M : in out T_Google
   --      I, J : in Integer
   --      Valeur : in Float
   procedure Modifier (V : in out T_Vecteur; I : in Integer; Valeur : in Double);


   -- Retourner un booléen définissant si deux matrices sont égales ou non
   -- Paramètres :
   --      M, A : in T_Google
   function Sont_Egaux (M, A : in T_Google) return Boolean;


   -- Afficher la matrice à des fins de mise au point
   -- Paramètres :
   --      M : in T_Google
   procedure Afficher (M : in T_Google);


                 ------------------------------------------------------------------------------------------------------------


   -- Récupérer les informations nécessaires pour le calcul du pagerank
   -- Paramètres :
   --      M : out T_Google  -- Un tableau de T_Vecteurs_Creux pour faciliter le calcul du vecteur de poids
   --      Tab : out T_Vecteur  -- Un tableau comportant le nombre de page référencé par la page numéro ligne
   --      Fichier : in String  -- Le nom du fichier .net
   procedure Recuperer_Donnees_Fichier(M : out T_Google; Tab : out T_Vecteur; Fichier : in String);


   -- Calculer le produit matriciel entre le vecteur de poids et G, ou G est la matrice de Google.
   -- Cette procedure rélise l'opération suivante : V := V.G
   -- Paramètres :
   --      V : in out T_Vecteur  -- Le vecteur de poids
   --      M : in T_Google  -- La matrice définit dans le sous-programme Recuperer_Donnees_Fichier()
   --      Tab : in T_Vecteur  -- Le tableau définit dans le sous-programme Recuperer_Donnees_Fichier()
   --      a : in Double  -- Le réel alpha
   procedure Produit_Matriciel_Google (V : in out T_Vecteur; M : in T_Google; Tab : in T_Vecteur; a : in Double);


   -- Créer et remplir les fichiers .p et .ord à partir du vecteur de poids V
   -- Paramètres :
   --      Vect_poids : in T_Vecteur  -- Le veceur a trier et afficher
   --      N : in Integer
   --      Nb_Iter : in Integer
   --      Alpha : in Double
   --      Fichier : in String  -- Le nom du fichier .net nécessaire pour nommer les deux nouveaux fichiers
   procedure Remplir_Fichier (Vect_Poids : in T_Vecteur; N : in Integer; Nb_Iter : in Integer; Alpha : in Double; Fichier : in String);


private

   subtype Index is Integer range 0..Capacite;

   type T_Vecteur is array(Index) of Double;

   package Vect_Creus is new Vecteurs_Creux(Double, Index, T_Vecteur);

   use Vect_Creus;

   type T_Google is array (0..Capacite) of T_Vecteur_Creux;


end Google_Creuse;
