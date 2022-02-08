with Ada.Strings.Unbounded;     use Ada.Strings.Unbounded;
with Ada.Integer_Text_IO;       use Ada.Integer_Text_IO;
with Ada.Text_IO;               use Ada.Text_IO;


package body Google_Naive is

   package Double_Text_Io is new Ada.Text_IO.Float_IO (Double);

   use Double_Text_Io;

   procedure Initialiser ( M : out T_Google) is
   begin
      for I in 0..Capacite loop
         Initialiser(M(I),0.0);
      end loop;
   end Initialiser;


   procedure Initialiser(V : out T_Vecteur; N : in Double) is
   begin
      for I in 0..Capacite loop
         V(I) := N;
      end loop;
   end Initialiser;


   function Composante (M : in T_Google; I,J : in Integer) return Double is
   begin
      return M(I)(J);
   end Composante;


   function Composante (V : in T_Vecteur; I : in Integer) return Double is
   begin
      return V(I);
   end Composante;


   procedure Modifier (M : in out T_Google; I,J : in Integer; Valeur : in Double) is
   begin
      M(I)(J) := Valeur;
   end Modifier;


   procedure Modifier (V : in out T_Vecteur; I : in Integer; Valeur : in Double) is
   begin
      V(I) := Valeur;
   end Modifier;


   function Sont_Egaux (M, A : in T_Google) return Boolean is
      Egaux, Oui: Boolean := True;
   begin
      for I in 0..Capacite loop
         for J in 0..Capacite loop
            Egaux := (M(I)(J) = A(I)(J));
            if not Egaux then
               Oui := False;
            else
               null;
            end if;
         end loop;
      end loop;

      return Oui;
   end Sont_Egaux;


   procedure Afficher (M : in T_Google) is
   begin
      for I in 0..Capacite loop
         for J in 0..Capacite loop
            Put ("-->[ ");
            Put (J, 0);
            Put (" | ");
            Put (M(I)(J), 0);
            Put (" ]");
         end loop;
         New_Line;
      end loop;
   end Afficher;

                  ------------------------------------------------------------------------------------------------------------
                                            -- Partie propre au calcul du pagerank


   procedure Calcul_Google(G : out T_Google; Fichier : in String; a : in Double) is


      -- Retourner si la ligne I de la matrice M est nulle ou non
      -- Paramètres :
      --       M : in T_Goole
      --       I : in Integer  -- Le numéro de la ligne à vérifier
      function Ligne_Nulle(M: in T_Google; I : in Integer) return Boolean is
         K: Integer ;
      begin
         K:=0;
         for j in 0..Capacite loop
            if Composante(M,I,J)=0.0 then
               K:=K+1;
            else
               null;
            end if;
         end loop;

         return K = Capacite+1;
      end Ligne_Nulle;


      C, Ligne: Integer;
      F : File_Type;
      Tab, Ligne_vide : T_Vecteur;
   begin
      Initialiser(G);
      Initialiser(Tab, 0.0);

      Open(F,In_File,Fichier);

      -- Sauter la première ligne
      Get(F,C);

      begin
         while not End_Of_File(F) loop

            Get(F, Ligne);

            Get(F,C);

            -- Permet de ne pas prendre en compte les doublons
            if Composante(G,Ligne,C) = 0.0 then

               Tab(Ligne) := Tab(Ligne) + 1.0;  -- Garder en mêmoire le nombre de page référencé par la page numéro ligne

               Modifier(G,Ligne,C,1.0);  -- Remplir la matrice H, pas tout à fait égal à celle du sujet, ligne par ligne
            else
               null;
            end if;

         end loop;

      exception
         when End_Error =>
            null;
      end;

      Close(F);

      Initialiser(Ligne_vide,1.0/Double(Capacite + 1));

      for I in 0..Capacite loop
         if ligne_nulle(G,I) then
            G(I) := Ligne_vide;
         else
            for J in 0..Capacite loop
               Modifier(G,I,J,G(I)(J)*a/Tab(I)+(1.0-a)/Double(Capacite+1));
            end loop;
         end if;
      end loop;
   end Calcul_Google;


   procedure Produit_Matriciel (V : in out T_Vecteur; M : in T_Google) is
      Somme : Double;
      Memoire : T_Vecteur;
   begin
      Memoire := V;

      -- Calculer le produit matriciel entre le vecteur de poids et la matrice de google
      for I in 0..Capacite loop
         Somme := 0.0;
         for J in 0..Capacite loop
            Somme := Somme + Memoire(J)*Composante(M,J,I);
         end loop;
         V(I) := Somme;
      end loop;
   end Produit_Matriciel;


   procedure Remplir_Fichier (Vect_Poids : in T_Vecteur; N : in Integer; Nb_Iter : in Integer; Alpha : in Double; Fichier : in String) is


      -- Remplir les fichiers .p et .ord à partir du vecteur de poids V
      -- Paramètres :
      --      Fichier1 : in File_Type  -- Le fichier .p
      --      Fichier2 : in File_Type  -- Le fichier .ord
      --      V : in T_Vecteur  -- Le vecteur de poids
      procedure Remplissage_Fichiers (Fichier1, Fichier2 : in File_Type; V : in T_Vecteur) is
      Indice : Integer := 0 ;
         V_bis : T_Vecteur;
      begin
         V_bis := V;

         -- Remplir les deux nouveaux fichiers
         for I in 0..Capacite loop

            -- Récupérer l'indice de l'élément le plus élevé des N-I plus petits éléments du vecteur de poids
            for J in 0..Capacite loop
               if V_bis(J) > V_bis(Indice) then
                  Indice := J;
               else
                  null;
               end if;
            end loop;

            -- Remplir les deux fichiers avec les données correspondantes
            Put(Fichier2,Indice,0);
            New_Line(Fichier2);
            Put(Fichier1,V_bis(Indice),1 , 10);
            New_Line(Fichier1);

            V_bis(Indice) := 0.0;
         end loop;
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


end Google_Naive;
