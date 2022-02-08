with Ada.Text_IO;           use Ada.Text_IO;
with Ada.Integer_Text_IO;   use Ada.Integer_Text_IO;
with Google_Naive;

procedure test_google_naive is

   type Double is digits 10 ;

   package Double_Text_Io is new Ada.Text_IO.Float_IO (Double);

   use Double_Text_Io;

   Fichier : constant String := "Fichier_Test.net";
   Alpha : constant Double := 0.85;

   package Google_Naive_Test is new Google_Naive(2, Double);

   use Google_Naive_Test;


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
      for I in 0..2 loop
         if I = 0 then
            Modifier(V,I,0.2587);
         elsif I = 1 then
            Modifier(V,I,2.33654);
         else
            Modifier(V,I,1.2587);
         end if;
      end loop;
   end Initialiser_Vecteur_Poids;




   procedure Construire_exemple_Sujet (A, B : out T_Google; C : out T_Vecteur)  is
   begin
      Initialiser(A);
      Initialiser(B);
      Initialiser(C,1.0);

      For i in 0..2 loop
         For j in 0..2 loop
            Modifier(A,i,j,Double(j*i));
            Modifier(B,i,j,1.0);
         end loop;
      end loop;
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




   procedure Tester_Calcul_Google is
      G  : T_Google;
   begin
      Put_Line ("=== Tester_Calcul_Google..."); New_Line;
      Calcul_Google(G, Fichier, Alpha);
      Afficher(G);
      New_Line;
      pragma Assert(Composante(G,0,0) = (1.0-Alpha)/3.0);
      pragma Assert(Composante(G,1,0) = Alpha/2.0 + (1.0-Alpha)/3.0);
      pragma Assert(Composante(G,2,2) = 1.0/3.0);
      pragma Assert(Composante(G,0,0) = Composante(G,1,1));
      pragma Assert(Composante(G,2,0) = Composante(G,2,1));
   end Tester_Calcul_Google;




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




   procedure Tester_Produit_Matriciel is
      A : T_Google;
      B : T_Google;
      V : T_Vecteur;
      somme : Double;
   begin
      Put_Line ("=== Tester_Produit_Matriciel..."); New_Line;
      Construire_Exemple_Sujet (A, B, V);

      Produit_Matriciel(V,A);

      For i in 0..2 loop
         somme := 0.0;
         For j in 0..2 loop
            somme := somme + Double(i*j);
         end loop;
         Pragma Assert(Composante(V,i)=somme);
      end loop;

      Initialiser(V,1.0);
      Produit_Matriciel(V,B);

      For I in 0..2 loop
         pragma Assert(Composante(V,i) = 3.0);
      end loop;
   end Tester_Produit_Matriciel;




begin
   Construire_Fichier;
   Tester_Produit_Matriciel;
   Tester_Sont_Egaux;
   Tester_Calcul_Google;
   Tester_Remplir_Fichier;
   Put_Line("Fin des tests. Ok!");
end test_google_naive;
