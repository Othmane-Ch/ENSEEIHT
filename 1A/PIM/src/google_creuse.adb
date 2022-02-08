with Ada.Strings.Unbounded;     use Ada.Strings.Unbounded;
with Ada.Integer_Text_IO;       use Ada.Integer_Text_IO;
with Ada.Text_IO;
with Text_IO;                   use Text_IO;

package body Google_Creuse is

   package Double_Text_Io is new Ada.Text_IO.Float_IO (Double);

   use Double_Text_Io;

   procedure Initialiser(M: out T_Google) is
   begin
      for I in 0..Capacite loop
         Initialiser(M(I));
      end loop;
   end Initialiser;

   procedure Initialiser(V : out T_Vecteur; N : in Double) is
   begin
      for I in 0..Capacite loop
         V(I) := N;
      end loop;
   end Initialiser;


   procedure Detruire(M : in out T_Google) is
   begin
      for I in 0..Capacite loop
         Detruire(M(I));
      end loop;
   end Detruire;


   function Composante (M : in T_Google; I,J : in Integer) return Double is
   begin
      return Composante(M(I),J+1);
   end Composante;


   function Composante (V : in T_Vecteur; I : in Integer) return Double is
   begin
      return V(I);
   end Composante;


   procedure Modifier (M : in out T_Google; I,J : in Integer; Valeur : in Double) is
   begin
      Modifier(M(I),J+1,Valeur);
   end Modifier;


   procedure Modifier (V : in out T_Vecteur; I : in Integer; Valeur : in Double) is
   begin
      V(I) := Valeur;
   end Modifier;


   function Sont_Egaux (M, A : in T_Google) return Boolean is
      Egaux : Boolean;
      I : integer :=0;
   begin
      while I <= Capacite loop
         Egaux := Sont_Egaux(M(I),A(I));
         if Egaux = False then
            I := Capacite;
         else
            null;
         end if;
         I := I +1;
      end loop;

      return Egaux;
   end Sont_Egaux;


   procedure Afficher (M : in T_Google) is
   begin
      for I in 0..Capacite loop
         Afficher(M(I));
         New_Line;
      end loop;
   end Afficher;

               ------------------------------------------------------------------------------------------------------------
                                            -- Partie propre au calcul du pagerank


   procedure Recuperer_Donnees_Fichier(M : out T_Google; Tab : out T_Vecteur; Fichier : in String) is
      Colonne, Ligne : Integer := 0;
      F : File_Type;
   begin
      Initialiser(M);
      Initialiser(Tab, 0.0);

      Open(F,In_File,Fichier);

      -- Sauter la première ligne
      Get(F,Colonne);

      -- Récupérer les informations nécessaires pour le calcul du pagerank
      begin
         while not End_Of_File(F) loop

            Get(F,Ligne);

            Get(F,Colonne);

            -- Permet de ne pas prendre en compte les doublons
            if Composante(M,Colonne,Ligne) = 0.0 then

               Tab(Ligne) := Tab(Ligne) + 1.0;  -- Garder en mêmoire le nombre de page référencé par la page numéro ligne

               Modifier(M,Colonne,Ligne,1.0);  -- Remplir M colonne par collone pour faciliter le calcul du vecteur de poids
            else
               null;
            end if;
         end loop;
      exception
         when End_Error =>
            null;
      end;

      Close(F);
   end Recuperer_Donnees_Fichier;


   procedure Produit_Matriciel_Google (V : in out T_Vecteur; M : in T_Google; Tab : in T_Vecteur; a : in Double) is
      Memoire : T_Vecteur;
   begin
      Memoire := V;

      -- Calculer le produit matriciel entre le vecteur de poids et la matrice de google
      for I in 0..Capacite loop
         V(I) := Produit_Scalaire_Google(Memoire, M(I), Tab, Capacite+1, a);
      end loop;
   end Produit_Matriciel_Google;


   procedure Remplir_Fichier (Vect_Poids : in T_Vecteur; N : in Integer; Nb_Iter : in Integer; Alpha : in Double; Fichier : in String) is


      -- Remplir les fichiers .p et .ord à partir du vecteur de poids V
      -- Paramètres :
      --      Fichier1 : in File_Type  -- Le fichier .p
      --      Fichier2 : in File_Type  -- Le fichier .ord
      --      V : in T_Vecteur  -- Le vecteur de poids
      procedure Remplissage_Fichiers (Fichier1, Fichier2 : in File_Type; V : in T_Vecteur) is
         Indice, Vrai_Indice : Integer := 0 ;
         V_bis : T_Vecteur;
         Memoire : T_Vecteur_Creux;
      begin
         Initialiser(Memoire);
         V_bis := V;

         -- Remplir les deux nouveaux fichiers
         for I in 0..Capacite loop
            Indice := 0;

            -- Récupérer l'indice de l'élément le plus élevé des N-I plus petits éléments du vecteur de poids
            for J in I..Capacite loop
               if V_bis(J) > V_bis(Indice) then
                  Indice:= J;
               else
                  null;
               end if;
            end loop;
            -- Récupérer le vrai indice de l'élément le plus élevé des N-I plus petits éléments du vecteur de poids
            Vrai_Indice := Integer(Composante(Memoire, Indice+1));

            if Vrai_Indice = 0 then

               -- Remplir les deux fichiers avec les données correspondantes
               Put(Fichier2,Indice,0);
               New_Line(Fichier2);
               Put(Fichier1,V_bis(Indice),1 , 10);
               New_Line(Fichier1);

               -- Garder en mêmoire l'indice seulement si nécessaire
               if I /= Indice then
                  Vrai_Indice := Integer(Composante(Memoire,I+1));

                  -- Mettre en mêmoire le vrai indice du Ième élément
                  if Vrai_Indice = 0 then
                     Modifier(Memoire, Indice+1,Double(I)+1.0);
                  else
                     Modifier(Memoire, Indice+1,Double(Vrai_Indice));
                  end if;
               else
                  null;
               end if;

            else

               -- Remplir les deux fichiers avec les données correspondantes
               Put(Fichier2,Vrai_Indice-1,0);
               New_Line(Fichier2);
               Put(Fichier1,V_bis(Indice),1 , 10);
               New_Line(Fichier1);
               Vrai_Indice := Integer(Composante(Memoire,I+1));

               -- Mettre en mêmoire le vrai indice du Ième élément
               if Vrai_Indice = 0 then
                  Modifier(Memoire, Indice+1,Double(I)+1.0);
               else
                  Modifier(Memoire, Indice+1,Double(Vrai_Indice));
               end if;
            end if;

            -- Déplacer le Ième élément à la place de l'élément le plus élevé des N-I plus petits éléments du vecteur de poids
            V_bis(Indice) := V_bis(I);
            V_bis(0) := 0.0;
         end loop;

         Detruire(Memoire);
      end Remplissage_Fichiers;

      Nom_Fichier1, Nom_Fichier2 : Unbounded_String;
      File1, File2 : File_Type;
   begin
      -- Créer les deux fichiers

      -- Fichier .p
      Nom_Fichier1 := To_Unbounded_String(Fichier);
      Delete(Nom_Fichier1,Length(Nom_Fichier1)-3,Length(Nom_Fichier1));
      Append(Nom_Fichier1,".p");
      Create(File1, Out_File, To_String (Nom_Fichier1));
      Put(File1, N, 0);
      Put(File1, Alpha, 2, 10);
      Put(File1, ' ');
      Put(File1, Nb_Iter, 0);
      New_Line(File1);

      -- Fichier .ord
      Nom_Fichier2 := To_Unbounded_String(Fichier);
      Delete(Nom_Fichier2,Length(Nom_Fichier2)-3,Length(Nom_Fichier2));
      Append(Nom_Fichier2,".ord");
      Create(File2, Out_File, To_String (Nom_Fichier2));

      -- Remplir les deux fichiers
      Remplissage_Fichiers(File1,File2,Vect_Poids);

      Close(File1);
      Close(File2);
   end Remplir_Fichier;


end Google_Creuse;
