with Ada.Text_IO;           use Ada.Text_IO;
with Ada.Integer_Text_IO;   use Ada.Integer_Text_IO;
with Google_Creuse;

procedure test_google_creuse is

   type Double is digits 10;

   package Double_Text_Io is new Ada.Text_IO.Float_IO (Double);

   use Double_Text_Io;

   Fichier : constant String := "Fichier_Test.net";
   Alpha : constant Double := 0.85;

   package Google_Creuse_Test is new Google_Creuse(2, Double);

   use Google_Creuse_Test;




   procedure Construire_Fichier is
      File : File_Type;
   begin
      Create(File, Out_File, Fichier);
      Put(File, 3, 0);
      New_Line(File);
      For I in 0..2 loop
         if I = 1 then
            Put(File, I, 0);
            Put(File, 0, 2);
            New_Line(File);
            Put(File, I, 0);
            Put(File, 2, 2);
            New_Line(File);
         elsif I = 0 then
            Put(File, I, 0);
            Put(File, 1, 2);
            New_Line(File);
         else
            null;
         end if;
      end loop;
      Close(File);
   end Construire_Fichier;




   procedure Initialiser_Vecteur_Poids(V : out T_Vecteur) is
   begin
      Modifier(V,0,0.2587);
      Modifier(V,1,2.33654);
      Modifier(V,2,1.2587);
   end Initialiser_Vecteur_Poids;




   procedure Construire_exemple_Sujet (A : out T_Google; TabA : out T_Vecteur; V : out T_Vecteur)  is
   begin
      Initialiser(A);
      Initialiser(V,1.0);


      Modifier(A,0,0,1.0);
      Modifier(A,0,1,1.0);
      Modifier(A,0,2,0.0);
      Modifier(A,1,0,1.0);
      Modifier(A,1,1,0.0);
      Modifier(A,1,2,1.0);
      Modifier(A,2,0,1.0);
      Modifier(A,2,1,1.0);
      Modifier(A,2,2,1.0);


      Modifier(TabA,0,3.0);
      Modifier(TabA,1,2.0);
      Modifier(TabA,2,2.0);

   end Construire_exemple_Sujet;





   procedure Tester_Sont_Egaux is
      A, B  :  T_Google;
   begin
      Put_Line ("=== Tester_Sont_Egaux..."); New_Line;
      Initialiser(A);
      Initialiser(B);
      For i in 0..2 loop
         For j in 0..2 loop
            Modifier(A,i,j,1.0);
            Modifier(B,i,j,Double(1));
         end loop;
         Pragma Assert(Sont_Egaux(A,B));
      end loop;
   end Tester_Sont_Egaux;





   procedure Tester_Recuperer_Donnees_Fichier is
      M  : T_Google;
      Tab : T_Vecteur;
   begin
      Put_Line ("=== Tester_Recuperer_Donnees_Fichier..."); New_Line;
      Recuperer_Donnees_Fichier(M, Tab, Fichier);
      Afficher(M);
      New_Line;

      pragma Assert(Composante(M,0,0) = 0.0);
      pragma Assert(Composante(M,0,1) = 1.0);
      pragma Assert(Composante(M,0,2) = 0.0);
      pragma Assert(Composante(M,1,0) = 1.0);
      pragma Assert(Composante(M,1,1) = 0.0);
      pragma Assert(Composante(M,1,2) = 0.0);
      pragma Assert(Composante(M,2,0) = 0.0);
      pragma Assert(Composante(M,2,1) = 1.0);
      pragma Assert(Composante(M,2,2) = 0.0);

      pragma Assert(Composante(Tab,0) = 1.0);
      pragma Assert(Composante(Tab,1) = 2.0);
      pragma Assert(Composante(Tab,2) = 0.0);
      Detruire(M);
   end Tester_Recuperer_Donnees_Fichier;





   procedure Tester_Produit_Matriciel_Google is
      A : T_Google;
      B : T_Google;
      TabA, TabB : T_Vecteur;
      V : T_Vecteur;
   begin
      Put_Line ("=== Tester_Produit_Matriciel..."); New_Line;
      Construire_Exemple_Sujet (A, TabA, V);
      Recuperer_Donnees_Fichier(B, TabB, Fichier);

      Produit_Matriciel_Google(V,A,TabA,Alpha);

      Pragma Assert(Composante(V,0) = (Alpha/Composante(TabA,0)+(1.0-Alpha)/3.0)+(Alpha/Composante(TabA,1)+(1.0-Alpha)/3.0)+(1.0-Alpha)/3.0);
      Pragma Assert(Composante(V,1) = (Alpha/Composante(TabA,0)+(1.0-Alpha)/3.0)+(1.0-Alpha)/3.0+(Alpha/Composante(TabA,2)+(1.0-Alpha)/3.0));
      Pragma Assert(Composante(V,2) = (Alpha/Composante(TabA,0)+(1.0-Alpha)/3.0)+(Alpha/Composante(TabA,1)+(1.0-Alpha)/3.0)+(Alpha/Composante(TabA,2)+(1.0-Alpha)/3.0));

      Initialiser(V,1.0/3.0);
      Produit_Matriciel_Google(V,B,TabB,Alpha);

      Pragma Assert(Composante(V,0) = 1.0/3.0*((1.0-Alpha)/3.0 + (Alpha/2.0 + (1.0-Alpha)/3.0) + 1.0/3.0));
      Pragma Assert(Composante(V,1) = 1.0/3.0*((Alpha/1.0 + (1.0-Alpha)/3.0) + (1.0-Alpha)/3.0 + 1.0/3.0));
      Pragma Assert(Composante(V,2) = 1.0/3.0*((1.0-Alpha)/3.0 + (Alpha/2.0 + (1.0-Alpha)/3.0) + 1.0/3.0));

      Detruire(B);
      Detruire(A);
   end Tester_Produit_Matriciel_Google;





   procedure Tester_Remplir_Fichier is
      Fichier1, Fichier2 : File_Type;
      V : T_Vecteur;
      Indice, N, Nb_Iter : Integer;
      Poids, alph : Double;
   begin
      Put_Line ("=== Tester_Remplir_Fichiers..."); New_Line;
      Initialiser_Vecteur_Poids(V);

      Remplir_Fichier(V, 3, 150, Alpha, Fichier);

      Open(Fichier1,In_File,"Fichier_Test.p");
      Open(Fichier2,In_File,"Fichier_Test.ord");

      Get(Fichier1, N);
      pragma Assert(N = 3);
      Get(Fichier1, alph);
      pragma Assert(alph = Alpha);
      Get(Fichier1, Nb_Iter);
      pragma Assert(Nb_Iter = 150);

      for I in 0..2 loop
         Get(Fichier2, Indice);
         Get(Fichier1, Poids);

         if I = 0 then
            pragma Assert(Indice = 1);
            pragma Assert(Poids = 2.33654);
         elsif I = 1 then
            pragma Assert(Indice = 2);
            pragma Assert(Poids = 1.2587);
         else
            pragma Assert(Indice = 0);
            pragma Assert(Poids = 0.2587);
         end if;
      end loop;

      Close(Fichier1);
      Close(Fichier2);
   end Tester_Remplir_Fichier;





   begin
   Construire_Fichier;
   Tester_Sont_Egaux;
   Tester_Recuperer_Donnees_Fichier;
   Tester_Produit_Matriciel_Google;
   Tester_Remplir_Fichier;
   Put_Line("Fin des tests. Ok!");
end test_google_creuse;
