(*  Exercice à rendre **)
(*  pgcd : int -> int -> int *)
(*  calcul le pgcd de deux entiers *)
(*  a,b : les entiers dont on veut calculer le pgcd *)
(*  renvoie le pgcd de a et b *)
(*  précondition : a et b positif*)
(*  postcondition : le pgcd doit etre  positif*)

(* *)
let  pgcd a b  =   
    let rec pgcd_aux x y = 
        if x = y then x
        else 
            if x > y then pgcd_aux (x-y) y
            else   pgcd_aux x (x-y)
    in match a,b with 
        | 0,b  ->  b
        | a,0  ->  a
        |  _   -> pgcd_aux (abs a) (abs b) 


        
(*  TO DO : tests unitaires *)

let%test _ = pgcd 7 2 = 1
let%test _ = pgcd 49 7 = 7
let%test _ = pgcd 117 21 = 3
let%test _ = pgcd 56 12 = 4
let%test _ = pgcd 24 8 = 8





