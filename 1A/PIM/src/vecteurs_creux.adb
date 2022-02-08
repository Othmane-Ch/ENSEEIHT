with Ada.Text_IO;                 use Ada.Text_IO;
with Ada.Integer_Text_IO;         use Ada.Integer_Text_IO;
with Ada.Unchecked_Deallocation;
with Exceptions;                  use Exceptions;

package body Vecteurs_Creux is

   package Double_Text_Io is new Ada.Text_IO.Float_IO (Double);

   use Double_Text_Io;

   procedure Free is
     new Ada.Unchecked_Deallocation (T_Cellule, T_Vecteur_Creux);


   procedure Initialiser (V : out T_Vecteur_Creux) is
   begin
      V:=Null;
   end Initialiser;


   procedure Detruire (V: in out T_Vecteur_Creux) is
      A_detruire : T_Vecteur_Creux;
      begin
      while V /= Null loop
         A_Detruire := V;
         V := V.all.Suivant;
         Free(A_detruire);
      end loop;
   end Detruire;


   function Est_Nul (V : in T_Vecteur_Creux) return Boolean is
   begin
      return V = Null;
   end Est_Nul;


   function Composante (V : in T_Vecteur_Creux ; Indice : in Integer) return Double is
   begin
      If Indice = 0 then
         raise Indice_Error;
      else
         If V = Null then
            return 0.0;
         elsif V.all.Indice = Indice then
            return V.all.Valeur;
         elsif V.all.Indice > Indice then
            return 0.0;
         else
            return Composante(V.all.Suivant,Indice);
         end if;
      end if;
   end Composante;


   procedure Modifier (V : in out T_Vecteur_Creux ;
                       Indice : in Integer ;
                       Valeur : in Double ) is
      A_Detruire : T_Vecteur_Creux;
   begin
      if Indice = 0 then
         raise Indice_Error;
      else
         if V=null then
            if Valeur = 0.0 then
               null;
            else
               V := new T_Cellule'(Indice, Valeur, Null);
            end if;
         elsif Indice < V.all.Indice then
            if Valeur = 0.0 then
               null;
            else
               V := new T_Cellule'(Indice, Valeur, V);
            end if;
         elsif Indice = V.all.Indice then
            if Valeur = 0.0 then
               A_Detruire := V;
               V := V.all.Suivant;
               Free(A_Detruire);
            else
               V.all.Valeur := Valeur;
            end if;
         else
            Modifier (V.all.Suivant,Indice,Valeur);
         end if;
      end if;
   end Modifier;


   function Sont_Egaux (V1, V2 : in T_Vecteur_Creux) return Boolean is
   begin
      if V1 = Null and V2 = Null then
         return True;
      elsif V1 =Null or V2 = Null then
         return false;
      elsif V1.all.Valeur = V2.all.Valeur then
         return Sont_Egaux(V1.all.Suivant,V2.all.suivant);
      else
        return false;
      end if;


   end Sont_Egaux;


   procedure Produit (V : in out T_Vecteur_Creux; Lambda : in Double) is
   begin
      if V = null then
         null;
      else
         V.all.Valeur := V.all.Valeur * Lambda;
         Produit(V.all.Suivant,Lambda);
      end if;
   end Produit;


   procedure Additionner (V1 : in out T_Vecteur_Creux; V2 : in T_Vecteur_Creux) is
      Curseur2 : T_Vecteur_Creux;
   begin
      Curseur2 := V2;
      while Curseur2 /= Null loop
         Modifier(V1,Curseur2.all.Indice, Curseur2.all.Valeur + Composante(V1,Curseur2.all.Indice));
         Curseur2 := Curseur2.all.Suivant;
      end loop;
   end Additionner;


   function Norme2 (V : in T_Vecteur_Creux) return Double is
      Curseur : T_Vecteur_Creux;
      Res : Double := 0.0;
   begin
      if V = Null then
         return 0.0;
      else
         Curseur := V;
         while Curseur /= Null loop
            Res := Res + Curseur.all.Valeur*Curseur.all.Valeur;
            Curseur := curseur.all.suivant;
         end loop;
      end if;
      return Res;
   end Norme2;


   Function Produit_Scalaire (V1, V2: in T_Vecteur_Creux) return Double is
      Curseur1, Curseur2 : T_Vecteur_Creux;
      Res : Double := 0.0;
   begin
      if V1 = Null or V2 = Null then
         return 0.0;
      else
         Curseur1 := V1;
         while Curseur1 /= Null loop
            Curseur2 := V2;
            while Curseur2 /= Null loop
               if Curseur2.all.Indice = Curseur1.all.Indice then
                  Res := Res + Curseur2.all.Valeur * Curseur1.all.Valeur;
                  Curseur2 := Null;
               elsif Curseur2.all.Indice > Curseur1.all.Indice then
                  Curseur2 := Null;
               else
                  Curseur2 := Curseur2.all.suivant;
               end if;
            end loop;
            Curseur1 := Curseur1.all.suivant;
         end loop;
         return Res;
      end if;


   end Produit_Scalaire;


   procedure Afficher (V : in T_Vecteur_Creux) is
   begin
      if V = Null then
         Put ("--E");
      else
         -- Afficher la composante V.all
         Put ("-->[ ");
         Put (V.all.Indice-1, 0);
         Put (" | ");
         Put (V.all.Valeur, Fore => 0);
         Put (" ]");

         -- Afficher les autres composantes
         Afficher (V.all.Suivant);
      end if;
   end Afficher;


   function Ieme_Indice (V : in T_Vecteur_Creux; I : in Integer) return Integer is
      Curseur : T_Vecteur_Creux;
   begin
      if I > Nombre_Composantes_Non_Nulles(V) then
         raise Indice_Error;
      else
         Curseur := V;
         for J in 1..I-1 loop
            Curseur := Curseur.all.Suivant;
         end loop;
         return Curseur.all.Indice;
      end if;
   end Ieme_Indice;


   function Nombre_Composantes_Non_Nulles (V: in T_Vecteur_Creux) return Integer is
   begin
      if V = Null then
         return 0;
      else
         return 1 + Nombre_Composantes_Non_Nulles (V.all.Suivant);
      end if;
   end Nombre_Composantes_Non_Nulles;


                  ------------------------------------------------------------------------------------------------------------
                                            -- Partie propre au calcul du pagerank


   function Produit_Scalaire_Google (V : in T_Vecteur; Colonne : in T_Vecteur_Creux; Tab : in T_Vecteur; N : in Integer; Alpha : in Double) return Double is


      -- Sous-programme permettant de simplifier Produit_Scalaire_Google
      procedure Boucle_For (somme : in out Double; Indice_Début, Indice_Fin : in Integer; Tab, V : in T_Vecteur; Alpha : in Double) is
      begin
         for I in Indice_Début..Indice_Fin loop

            -- Différencier le cas ou la ligne I de la matrice H est nulle de si elle ne l'est pas
            if Tab(Index(I)) = 0.0 then
               somme := somme + V(Index(I))*(1.0/Double(N));
            else
               somme := somme + V(Index(I))*(1.0-Alpha)/Double(N);
            end if;
         end loop;
      end Boucle_For;


      Curseur : T_Vecteur_Creux;
      Indice : Integer := 0 ;
      somme : Double := 0.0;
   begin

      -- Calculer le produit scalaire entre le vecteur de poids et un vecteur creux correspondant à une colonne de la matrice de Google
      Curseur := Colonne;

      if Curseur = null then
         Boucle_For(somme, 0, N-1, Tab, V, Alpha);
      else

         while Curseur /= null loop

            -- Calculer la somme du produit entre la Ième valeur du vecteur de poids et la Ième valeur de la collone de G
            -- définit par le vecteur creux Colonne, sachant que Colonne(I) = 0.0
            Boucle_For(somme, Indice, Curseur.all.Indice-2, Tab, V, Alpha);

            -- Ajouter à la somme calculé précédemment la valeur suivante
            somme := somme + V(Index(Curseur.all.Indice-1))*((1.0-Alpha)/Double(N) + Curseur.all.Valeur*Alpha/Tab(Index(Curseur.all.Indice-1)));
            Indice := Curseur.all.Indice;

            -- Si tous les autres termes de Colonne sont nuls, on finit de calculer la somme
            if Curseur.all.Suivant = null then
               Boucle_For(somme, Indice, N-1, Tab, V, Alpha);
            else
               null;
            end if;

            Curseur := Curseur.all.Suivant;
         end loop;
      end if;

      Detruire(Curseur);

      return somme;
   end Produit_Scalaire_Google;


end Vecteurs_Creux;
