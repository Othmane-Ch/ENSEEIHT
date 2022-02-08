
generic
   Capacite : Integer;
   type Double is digits <> ;

package Google_Naive is

   type T_Google is limited private;
   Type T_Vecteur is limited private;

   -- Initialiser la matrice en initialisant chaque coordonn�es � 0.0
   -- Param�tres :
   --      M : out T_Goole
   procedure Initialiser ( M : out T_Google);


   -- Initialiser le vecteur en initialisant chaque termes � N
   -- Param�tres :
   --      V : out T_Vecteur
   --      N : in Float
   procedure Initialiser ( V : out T_Vecteur; N : in Double);


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
   --      Valeur : in Float
   procedure Modifier (M : in out T_Google; I,J : in Integer; Valeur : in Double);


   -- Modifier la composante (I,J) de la matrice par la Valeur
   -- Param�tres :
   --      M : in out T_Google
   --      I, J : in Integer
   --      Valeur : in Float
   procedure Modifier (V : in out T_Vecteur; I : in Integer; Valeur : in Double);


   -- Retourner un bool�en d�finissant si deux matrices sont �gals ou non
   -- Param�tres :
   --      M, A : in T_Google
   function Sont_Egaux (M, A : in T_Google) return Boolean;


   -- Afficher la matrice � des fins de mise au point
   -- Param�tres :
   --      M : in T_Google
   procedure Afficher (M : in T_Google);


                 ------------------------------------------------------------------------------------------------------------


   -- Calculer la matrice de Google � partir du fichier .net
   -- Param�tres :
   --      G : out T_Google  -- La matrice de Google
   --      Fichier : in String  -- Le nom du fichier .net
   --      a : in Float  -- Le r�el Alpha
   procedure Calcul_Google(G : out T_Google; Fichier : in String; a : in Double);


   -- Calculer le produit matriciel entre V et M. Cette procedure r�lise l'op�ration
   -- suivante : V := V.M
   -- Param�tres :
   --      V : in out T_Vecteur
   --      M : in T_Google
   procedure Produit_Matriciel (V : in out T_Vecteur; M : in T_Google);


   -- Cr�er et remplir les fichiers .p et .ord � partir du vecteur de poids V
   -- Param�tres :
   --      Vect_poids : in T_Vecteur  -- Le veceur a trier et afficher
   --      N : in Integer
   --      Nb_Iter : in Integer
   --      Alpha : in Double
   --      Fichier : in String  -- Le nom du fichier .net n�cessaire pour nommer les deux nouveaux fichiers
   procedure Remplir_Fichier (Vect_Poids : in T_Vecteur; N : in Integer; Nb_Iter : in Integer; Alpha : in Double; Fichier : in String);

private

   type T_Google is array (0..Capacite) of T_Vecteur;
   type T_Vecteur is array(0..Capacite) of Double;

end Google_Naive;
