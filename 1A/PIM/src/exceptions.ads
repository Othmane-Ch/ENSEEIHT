-- Définition d'une exception commune à toutes les SDA.
package Exceptions is

   Element_Absent_Exception : Exception;  -- un �l�ment est absent de la liste
   Indice_Error : Exception;  -- un indice est invalide
   Commande_Error : Exception;  -- la commande est inconnue
   Fichier_Error : Exception;  -- le fichier n'est pas du bon type
   Alpha_Error : Exception;  -- alpha n'est pas entre 0 et 1

end Exceptions;
