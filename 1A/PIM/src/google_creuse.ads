with Vecteurs_Creux;

generic
   Capacite : Integer;
   type Double is digits <>;

package Google_Creuse is

   type T_Google is limited private;
   Type T_Vecteur is limited private;

   -- Initialiser la matrice en initialisant chaque vecteur creux
   -- Param�tres :
   --      M : out T_Goole
   procedure Initialiser ( M : out T_Google);


   -- Initialiser le vecteur en initialisant chaque termes � N
   -- Param�tres :
   --      V : out T_Vecteur
   --      N : in Double
   procedure Initialiser ( V : out T_Vecteur; N : in Double);


   -- D�truire la matrice M
   -- Param�tres :
   --      M : in out T_Google
   procedure Detruire (M : in out T_Google);


   -- R�cup�rer la composante de la matrice � l'indice (I,J)
   -- Param�tres :
   --      M : in T_Google
   --      I, J : in Integer
   function Composante (M : in T_Google; I,J : in Integer) return Double;


   -- R�cup�rer la composante du vecteur � l'indice I
   -- Param�tres :
   --      M : in T_Google
   --      I : in Integer
   function Composante (V : in T_Vecteur; I : in Integer) return Double;


   -- Modifier la composante (I,J) de la matrice par la Valeur
   -- Param�tres :
   --      M : in out T_Google
   --      I, J : in Integer
   --      Valeur : in Double
   procedure Modifier (M : in out T_Google; I,J : in Integer; Valeur : in Double);


   -- Modifier la composante (I,J) de la matrice par la Valeur
   -- Param�tres :
   --      M : in out T_Google
   --      I, J : in Integer
   --      Valeur : in Float
   procedure Modifier (V : in out T_Vecteur; I : in Integer; Valeur : in Double);


   -- Retourner un bool�en d�finissant si deux matrices sont �gales ou non
   -- Param�tres :
   --      M, A : in T_Google
   function Sont_Egaux (M, A : in T_Google) return Boolean;


   -- Afficher la matrice � des fins de mise au point
   -- Param�tres :
   --      M : in T_Google
   procedure Afficher (M : in T_Google);


                 ------------------------------------------------------------------------------------------------------------


   -- R�cup�rer les informations n�cessaires pour le calcul du pagerank
   -- Param�tres :
   --      M : out T_Google  -- Un tableau de T_Vecteurs_Creux pour faciliter le calcul du vecteur de poids
   --      Tab : out T_Vecteur  -- Un tableau comportant le nombre de page r�f�renc� par la page num�ro ligne
   --      Fichier : in String  -- Le nom du fichier .net
   procedure Recuperer_Donnees_Fichier(M : out T_Google; Tab : out T_Vecteur; Fichier : in String);


   -- Calculer le produit matriciel entre le vecteur de poids et G, ou G est la matrice de Google.
   -- Cette procedure r�lise l'op�ration suivante : V := V.G
   -- Param�tres :
   --      V : in out T_Vecteur  -- Le vecteur de poids
   --      M : in T_Google  -- La matrice d�finit dans le sous-programme Recuperer_Donnees_Fichier()
   --      Tab : in T_Vecteur  -- Le tableau d�finit dans le sous-programme Recuperer_Donnees_Fichier()
   --      a : in Double  -- Le r�el alpha
   procedure Produit_Matriciel_Google (V : in out T_Vecteur; M : in T_Google; Tab : in T_Vecteur; a : in Double);


   -- Cr�er et remplir les fichiers .p et .ord � partir du vecteur de poids V
   -- Param�tres :
   --      Vect_poids : in T_Vecteur  -- Le veceur a trier et afficher
   --      N : in Integer
   --      Nb_Iter : in Integer
   --      Alpha : in Double
   --      Fichier : in String  -- Le nom du fichier .net n�cessaire pour nommer les deux nouveaux fichiers
   procedure Remplir_Fichier (Vect_Poids : in T_Vecteur; N : in Integer; Nb_Iter : in Integer; Alpha : in Double; Fichier : in String);


private

   subtype Index is Integer range 0..Capacite;

   type T_Vecteur is array(Index) of Double;

   package Vect_Creus is new Vecteurs_Creux(Double, Index, T_Vecteur);

   use Vect_Creus;

   type T_Google is array (0..Capacite) of T_Vecteur_Creux;


end Google_Creuse;
