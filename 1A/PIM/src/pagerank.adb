with Text_IO;                   use Text_IO;
with Ada.Integer_Text_IO;       use Ada.Integer_Text_IO;
with Ada.Text_IO;               use Ada.Text_IO;
with Ada.Command_Line;          use Ada.Command_Line;
with Ada.Strings.Unbounded;     use Ada.Strings.Unbounded;
with Exceptions;                use Exceptions;
with Google_Creuse;
with Google_Naive;

procedure Pagerank is

   type Double is digits 10 ;


   -- Gérer les arguments de la ligne de commande
   -- Paramètres :
   --      I : in out Integer  -- Permet de connaitre quel argument est en train d'être pris en compte
   --      Choix_Matrice : out Character
   --      Nb_Iter : in out Integer
   --      Alpha : in out Double
   --      Arg : in out Unbounded_String  -- La chaine de caratère contenant l'argument I de la ligne de commande
   procedure Ligne_Commande (I : in out Integer; Choix_Matrice : out Character; Nb_Iter : in out Integer; Alpha : in out Double; Arg : in out Unbounded_String) is
   begin
      while I <= Argument_Count-1 loop
         Arg := To_Unbounded_String(Argument(I));
         if  To_String(Arg)(1) = '-' then
            case To_String(Arg)(2) is
            when 'P' =>
               -- Utiliser l'implantation Google_Naive
               Choix_Matrice := 'N';
            when 'I' =>
               -- Spécifier le nombre maximum d'itération
               I := I+1;
               Nb_Iter := Integer'Value(Argument(I));
            when 'A' =>
               -- Modifier la valeur d'alpha
               I := I+1;
               if Double'Value(Argument(I)) < 1.0 and then Double'Value(Argument(I)) > 0.0 then
                  alpha := Double'Value(Argument(I));
               else
                  raise Alpha_Error;
               end if;
            when others =>
               raise Commande_Error;
            end case;
         else
            raise Commande_Error;
         end if;
         I := I +1;
      end loop;
   end Ligne_Commande;


   -- Déterminer le nombre de noeuds à partir du fichier joint
   -- Paramètres :
   --       Fichier : in String  -- Le le nom du fichier joint
   --       N : out Integer
   procedure Nb_Noeuds (Fichier : in String; N : out Integer) is

      F : File_Type;  -- Le fichier réseau
      Point_net : String(1..4);
      C : Integer;
      I : Integer := 4;
   begin
      -- Vérifier que ce soit bien un fichier réseau
      for J in reverse Fichier'Length-3..Fichier'Length loop
         Point_net(I) := Fichier(J);
         I := I-1;
      end loop;
      if Point_net = ".net" then
         null;
      else
         raise Fichier_Error;
      end if;

      Open(F,In_File,Fichier);

      -- Déterminer le nombre de noeuds
      Get(F,C);
      N := C;

      Close(F);
   end Nb_Noeuds;


   -- Choisir et Calculer la matrice de Google et le vecteur poids en fonction de la commande
   -- Paramètres :
   --        Fichier : in String  -- Le le nom du fichier joint
   --        N : in INteger
   --        Iter : in Integer
   --        Intelligent : in Boolean
   --        Alpha : in Float
   procedure Choix_Qualité (Fichier : in String; N : in Integer; Nb_Iter : in Integer; Choix_Matrice : in Character; Alpha : in Double) is


      -- Calculer la matrice de Google et le vecteur poids pour le module Google_Naive
      -- Paramètres :
      --       Tab : in T_TH -- La table de hachage contenant chaque noeuds et les noeuds vers lesquels ils redirigent
      --       N : in Integer
      --       Iter : in Integer
      --       Alpha : in Float
      procedure Calcul_Naive(Fichier : in String; N : in Integer; Nb_Iter : in Integer; alpha : in Double) is

         package Ma_Matrice is new Google_Naive(N-1, Double);

         use Ma_Matrice;

         Vect_Poids : T_Vecteur;  -- Le vecteur poids
         G : T_Google;  -- La matrice de Google
      begin
         -- Déterminer la matrice de google
         Calcul_Google(G,Fichier,Alpha);

         Put_Line("La matrice de google a ete recupere depuis le fichier joint. On calcul dorenavant le vecteur de poids.");
         New_Line;

         -- Calculer le vecteur poids
         Initialiser(Vect_Poids,1.0/Double(N));
         for I in 0..Nb_Iter loop
            Produit_Matriciel(Vect_Poids,G);
         end loop;

         Put_Line("Le vecteur de poids a ete calcule. On remplit dorenavant les deux fichiers .p et .ord.");
         New_Line;

         -- Générer les fichiers .ord et .p
         Remplir_Fichier(Vect_Poids, N, Nb_Iter, Alpha, Fichier);
      end Calcul_Naive;


      -- Calculer la matrice de Google et le vecteur poids pour le module Google_Creuse
      -- Paramètres :
      --       Tab : in T_TH -- La table de hachage contenant chaque noeuds et les noeuds vers lesquels ils redirigent
      --       N : in Integer
      --       Iter : in Integer
      --       Alpha : in Float
      procedure Calcul_Creuse (Fichier : in String; N : in Integer; Nb_Iter : in Integer; alpha : in Double) is


         package Ma_Matrice is new Google_Creuse(N-1, Double);

         use Ma_Matrice;

         Vect_Poids, Tab : T_Vecteur;  -- Le vecteur de poids et un tableau de données pour permettre un calcul du vecteur de poids simplifié
         M : T_Google;  -- Une matrice composée de vecteurs creux pour permettre un calcul du vecteur de poids simplifié
      begin
         -- Récupérer les informations nécessaires pour le calcul du pagerank
         Recuperer_Donnees_Fichier(M,Tab,Fichier);

         Put_Line("La matrice de google a ete recupere depuis le fichier joint. On calcul dorenavant le vecteur de poids.");
         New_Line;

         -- Calculer le vecteur de poids
         Initialiser(Vect_Poids, 1.0/Double(N));
         for I in 0..Nb_Iter loop
            Produit_Matriciel_Google(Vect_Poids, M, Tab, alpha);
         end loop;

         Put_Line("Le vecteur de poids a ete calcule. On remplit dorenavant les deux fichiers .p et .ord.");
         New_Line;

         -- Générer les fichiers .ord et .p
         Remplir_Fichier(Vect_Poids, N, Nb_Iter, Alpha, Fichier);

         Detruire(M);
      end Calcul_Creuse;


   begin

      -- Utiliser Google_Naive ou Google_Creuse en fonction de la commande
      if Choix_Matrice = 'N' then
         Put_Line("On utilise le module Google_Naive.");
         New_Line;
         Calcul_Naive(Fichier,N,Nb_Iter,alpha);
      else
         Put_Line("On utilise le module Google_Creuse.");
         New_Line;
         Calcul_Creuse(Fichier,N,Nb_Iter,alpha);
      end if;
   end Choix_Qualité;


   Nb_iter : Integer := 150; -- Le nombre d'itération maximum pour calculer le vecteur poids
   Choix_Matrice : Character; -- Le caractère déterminant si on choisit Google_Creuse ou Google_Naive
   alpha : Double := 0.85; -- Le réel nécessaire au calcul de la matrice de google
   N : Integer; -- Le nombre de noeuds à traiter
   I : Integer := 1;
   Arg : Unbounded_String;
begin
   if Argument_Count = 0 then
      Put_Line("Le programme n'a pas de problème de compilation");
   else
      -- Gerer les arguments de la ligne de commande
      Ligne_Commande(I, Choix_Matrice, Nb_Iter, Alpha, Arg);

      -- Déterminer le nombre de noeuds à partir du fichier joint
      Nb_noeuds(Argument(I),N);

      -- Choisir et Calculer la matrice de Google et le vecteur poids en fonction de la commande
      Choix_Qualité(Argument(I),N,Nb_iter,Choix_Matrice,alpha);

      Put_Line("Le programme s'est termine sans erreur, les deux fichiers ont ete rempli.");
   end if;

exception
   when Commande_Error =>
      case To_String(Arg)(I) is
         when 'p' =>
            Put_Line("La commande " & Argument(I) & " est inconnue. Voulez vous dire -P ?");
         when 'i' =>
            Put_Line("La commande " & Argument(I) & " est inconnue. Voulez vous dire -I ?");
         when 'a' =>
            Put_Line("La commande " & Argument(I) & " est inconnue. Voulez vous dire -A ?");
         when others =>
            Put_Line("La commande " & Argument(I) & " est inconnue. Essayez avec -P ou -I 'Entier' ou -A 'Reel'.");
      end case;

   when Fichier_Error =>
      Put_Line("Le fichier '" & Argument(I) & "' n'est pas un fichier .net.");

   when Constraint_Error =>
      Put_Line("La commande " & Argument(I) & " est inconnue. Essayez avec -P ou -I 'Entier' ou -A 'Reel'.");

   when Alpha_Error =>
      Put_Line("Alpha doit etre entre 0 et 1.");
end Pagerank;
