library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
USE ieee.std_logic_arith.ALL;
USE ieee.std_logic_unsigned.ALL;


entity compteur is
    Port ( enable : in  STD_LOGIC;
           reset : in  STD_LOGIC;
           RxD : in  STD_LOGIC;
           tmprxd : out  STD_LOGIC;
           tmpclk : out  STD_LOGIC);
end compteur;

architecture structural of compteur is

   type t_etat is ( start, attente8,attente16 ,stop);
   signal etat : t_etat;
begin

 process(enable,reset)
        variable cpt_attente : natural;
	variable cpt_bit : natural;
  begin
    if (reset ='0') then
       --On initialise tout et on passe à l'état start pour attendre un ordre d'émission
			cpt_attente := 0;
			cpt_bit := 0;
			etat <= start;
			 tmpclk <= '0';
			 tmprxd <= '1';
    elsif (rising_edge(enable)) then
	   case etat is
			when start =>
			-- Quand on reçoint un ordre d'émission on passe à l'état attente pour attendre 8 top d'enable
					if( RxD = '0') then
						cpt_attente := 0;
				                 cpt_bit := 0;
				                  tmpclk <= '0';
		                  		 tmprxd <= '1';
						etat <= attente8;
					end if;
			when attente8 =>
			    --On teste si les tops d'enable sont passés sinon on incrémente le compteur de l'attente
					if(cpt_attente = 7) then
						cpt_bit := cpt_bit + 1;
						tmpclk <= '1';
						tmprxd <= RxD;
						-- On met tmpclk à 1 et Rxd dans tmprxd et on initialise le compteur d'attente et on passe à l'attente 16 
						cpt_attente := 0;
						etat <= attente16;
					else
					  cpt_attente := cpt_attente + 1;
					  tmpclk <= '0';
					end if;
			when attente16 =>
			     --On teste si le compteur d'attente a terminé 
					if(cpt_attente = 15) then
					--On teste le compteur de bits ( parce qu'on va recevoir 11 bits ) 
					   if( cpt_bit = 11) then
							tmpclk <= '0';
							--Si les bits sont reçus on passe à l'état stop 
							etat <= stop;
						else
						        --Sinon on incrémente le compteur bit pour compter le nombre de bits reçus sur Rxd
							cpt_bit := cpt_bit + 1;
							cpt_attente := 0;
							tmpclk <= '1';
							tmprxd <= RxD;
							etat <= attente16;
						end if;
					else
					--On incrémente le compteur d'attente 
					   tmpclk <= '0';
					   cpt_attente := cpt_attente + 1;
					end if;
			when stop =>
			                --quand la reception est terminée on met tmpclk à 0 et on revient à l'état start pour attendre un ordre de reception 
					tmpclk <= '0';
					tmprxd <= RxD;
					etat <= start;
			end case;
		end if;
 end process;
end structural;
