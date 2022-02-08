generic
   type Double is digits <>;
   type Index is range <>;
   type T_Vecteur is array (Index) of Double;

package Vecteurs_Creux is

   type T_Vecteur_Creux is limited private;


   -- Initialiser un vecteur creux � null
   -- Param�tres :
   --      V : out T_Vecteur_Creux
   procedure Initialiser (V :  out T_Vecteur_Creux);


   -- D�truire le vecteur V
   -- PAram�tres :
   --      V : in out T_Vecteur_Creux
   procedure Detruire (V: in out T_Vecteur_Creux);


   -- Retourner un bool�en d�finissant si un vecteur est nul ou non
   -- Param�tres :
   --      V : in T_Vecteur_Creux
   function Est_Nul (V : in T_Vecteur_Creux) return Boolean;


   -- R�cup�rer la composante (valeur) du vecteur V � l'indice Indice
   -- Param�tres :
   --      V : in T_Vecteur_Creux
   --      Indice : in Integer
   function Composante (V : in T_Vecteur_Creux ; Indice : in Integer) return Double;


   -- Modifier une composante (Indice, Valeur) d'un vecteur creux
   -- Param�tres :
   --      V : in out T_Vecteur_Creux
   --      Indice : in Integer
   --      Valeur : in Float
   procedure Modifier (V : in out T_Vecteur_Creux ;
                       Indice : in Integer ;
                       Valeur : in Double ) ;


   -- Retourner un bool�en d�finissant si deux vecteurs sont �gaux ou non
   -- Param�tres :
   --      V1, V2 : in T_Vecteur_Creux
   function Sont_Egaux (V1, V2 : in T_Vecteur_Creux) return Boolean;


   -- Multiplier tous les termes du vecteur par un scalaire
   -- Param�tres :
   --      V : in out T_Vecteur_Creux
   --      Lambda : in Float
   procedure Produit (V : in out T_Vecteur_Creux; Lambda : in Double);


   -- Additionner V1 avec V2. Ce sous-programme r�lise l'op�ration suivante :
   -- V1 := V1 + V2
   -- Param�tres :
   --      V1 : in out T_Vecteur_Creux
   --      V2 : in T_Vecteur_Creux
   procedure Additionner (V1 : in out T_Vecteur_Creux; V2 : in T_Vecteur_Creux);


   -- Norme au carré du vecteur V
   -- PAram�tres :
   --      V : in T_Vecteur_Creux
   function Norme2 (V : in T_Vecteur_Creux) return Double;


   -- Le produit scalaire de deux vecteurs
   -- Param�tres :
   --      V1, V2 : in T_Vecteur_Creux
   function Produit_Scalaire (V1, V2: in T_Vecteur_Creux) return Double;


   -- Afficher le vecteur creux � des fins de mise au point
   -- Param�tres :
   --      V : in T_Vecteur_Creux
   procedure Afficher (V : in T_Vecteur_Creux);


   -- Obtenir le Ieme indice de velauer non nul
   -- Param�tres :
   --      V : in T_Vecteur_Creux
   --      I : in Integer  -- L'indice
   function Ieme_Indice (V : in T_Vecteur_Creux; I : in Integer) return Integer;


   -- Nombre de composantes non nulles du vecteur V.
   --
   -- Ce sous-programme ne fait normalement pas partie de la spécification
   -- du module.  Il a été ajouté pour faciliter l'écriture des programmes
   -- de test.
   function Nombre_Composantes_Non_Nulles (V: in T_Vecteur_Creux) return Integer;


                     ------------------------------------------------------------------------------------------------------------


   -- Retourne le produit scalaire entre le vecteur de poids et un vecteur creux correspondant � une colonne de la matrice de Google
   -- Param�tres :
   --      V : in T_Vecteur  -- Le vecteur de poids
   --      Colonne : in T_Vecteur_Creux
   --      Tab : in T_Vecteur
   --      N : in Integer  -- Le nombre de pages
   --      Alpha : in Double
   --
   -- Ce sous programme ne fonctionne qu'avec la matrice M d�finit dans le sous-programme Recuperer_Donnees_Fichier() du module Google_Creuse
   function Produit_Scalaire_Google (V : in T_Vecteur; Colonne : in T_Vecteur_Creux; Tab : in T_Vecteur; N : in Integer; Alpha : in Double) return Double;


private

   type T_Cellule;

   type T_Vecteur_Creux is access T_Cellule;

   type T_Cellule is
      record
         Indice : Integer;
         Valeur : Double;
         Suivant : T_Vecteur_Creux;
         -- Invariant :
         --   Indice >= 1;
         --   Suivant = Null or else Suivant.all.Indice > Indice;
         --   	-- les cellules sont stockés dans l'ordre croissant des indices.
      end record;

end Vecteurs_Creux;
