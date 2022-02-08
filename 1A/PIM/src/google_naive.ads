
generic
   Capacite : Integer;
   type Double is digits <> ;

package Google_Naive is

   type T_Google is limited private;
   Type T_Vecteur is limited private;

   -- Initialiser la matrice en initialisant chaque coordonnées à 0.0
   -- Paramètres :
   --      M : out T_Goole
   procedure Initialiser ( M : out T_Google);


   -- Initialiser le vecteur en initialisant chaque termes à N
   -- Paramètres :
   --      V : out T_Vecteur
   --      N : in Float
   procedure Initialiser ( V : out T_Vecteur; N : in Double);


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
   --      Valeur : in Float
   procedure Modifier (M : in out T_Google; I,J : in Integer; Valeur : in Double);


   -- Modifier la composante (I,J) de la matrice par la Valeur
   -- Paramètres :
   --      M : in out T_Google
   --      I, J : in Integer
   --      Valeur : in Float
   procedure Modifier (V : in out T_Vecteur; I : in Integer; Valeur : in Double);


   -- Retourner un booléen définissant si deux matrices sont égals ou non
   -- Paramètres :
   --      M, A : in T_Google
   function Sont_Egaux (M, A : in T_Google) return Boolean;


   -- Afficher la matrice à des fins de mise au point
   -- Paramètres :
   --      M : in T_Google
   procedure Afficher (M : in T_Google);


                 ------------------------------------------------------------------------------------------------------------


   -- Calculer la matrice de Google à partir du fichier .net
   -- Paramètres :
   --      G : out T_Google  -- La matrice de Google
   --      Fichier : in String  -- Le nom du fichier .net
   --      a : in Float  -- Le réel Alpha
   procedure Calcul_Google(G : out T_Google; Fichier : in String; a : in Double);


   -- Calculer le produit matriciel entre V et M. Cette procedure rélise l'opération
   -- suivante : V := V.M
   -- Paramètres :
   --      V : in out T_Vecteur
   --      M : in T_Google
   procedure Produit_Matriciel (V : in out T_Vecteur; M : in T_Google);


   -- Créer et remplir les fichiers .p et .ord à partir du vecteur de poids V
   -- Paramètres :
   --      Vect_poids : in T_Vecteur  -- Le veceur a trier et afficher
   --      N : in Integer
   --      Nb_Iter : in Integer
   --      Alpha : in Double
   --      Fichier : in String  -- Le nom du fichier .net nécessaire pour nommer les deux nouveaux fichiers
   procedure Remplir_Fichier (Vect_Poids : in T_Vecteur; N : in Integer; Nb_Iter : in Integer; Alpha : in Double; Fichier : in String);

private

   type T_Google is array (0..Capacite) of T_Vecteur;
   type T_Vecteur is array(0..Capacite) of Double;

end Google_Naive;
