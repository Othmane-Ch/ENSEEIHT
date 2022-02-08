----------------------------------------------------------------------------------
-- Company:
-- Engineer:
--
-- Create Date:    16:56:02 01/14/2022
-- Design Name:
-- Module Name:    ControleR - Behavioral
-- Project Name:
-- Target Devices:
-- Tool versions:
-- Description:
--
-- Dependencies:
--
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity ControleR is
    Port ( clk : in  STD_LOGIC;
           reset : in  STD_LOGIC;
           read : in  STD_LOGIC;
           tmpclk : in  STD_LOGIC;
           tmprxd : in  STD_LOGIC;
           FErr : out  STD_LOGIC;
           OErr : out  STD_LOGIC;
           DRdy : out  STD_LOGIC;
           data : out  STD_LOGIC_VECTOR (7 downto 0));
end ControleR;

architecture Behavioral of ControleR is

	type t_etat is ( start,reception1, reception ,controle,reception_Fin, fin);
	signal etat : t_etat;
begin
  process(clk,reset)
	 variable data_aux : STD_LOGIC_VECTOR (7 downto 0);
	 variable cpt_bit : natural;
	 variable p : std_logic;
  begin
	   if (reset= '0') then
	      --On inialise le compteur à 7 
	      cpt_bit := 7;
	      --On initialise tout à 0 et on passe à l'état start 
	      data_aux := (others=> '0');
	      FErr <= '0';
	      OErr <= '0';
	      DRdy <= '0';
	      data <= (others=> '0');
	      etat <= start;
	      --Au front montant de l'horloge
	   elsif (rising_edge(clk)) then
		    case etat is
		    	when start =>
		    	        --Si tmpclk est passé à 1 on va recevoir le bit de start mais on va l'enregistrer 
		    		if(tmpclk = '1' ) then
		    		      --On met tout à 0
		    		      data_aux := (others=> '0');
				      FErr <= '0';
				      OErr <= '0';
				      DRdy <= '0';
				      data <= (others=> '0');
		    		      etat <= reception1;
		    		      p:='0';
		    	        end if;
		    	   --On commence la reception du premier bit apres le bit de start 
			when reception1 =>
			
			    if  (tmpclk = '1' ) then
			      --On enregistrer dans le buffer la valeur en entrée de tmprxd
			      data_aux(cpt_bit) := tmprxd;
			      --On initialise le bit de parite 
		    	      p := data_aux(cpt_bit);
		    	      -- On décremente le compteur 
		    	     cpt_bit := cpt_bit - 1;
		    	     -- On passe à l'état reception pour recevoir le reste de data 
		    	     etat <= reception;
		    	     end if;
			when reception =>
	    	            if  (tmpclk = '1' ) then
	    	              --On enregistre le bit 
			  	data_aux(cpt_bit) := tmprxd;
			      -- On calcule le bit de parite 
			  	p := p xor data_aux(cpt_bit);
			  	if (cpt_bit =0) then 
			  	        --Si on a plus de bits à recevoir on passe à l'état de controle pour verifier le bit de parite 
			  		etat<= controle;
			  	else
			   		--Sinon on décrémente le compteur 
			   		cpt_bit := cpt_bit-1;
				end if;
			  
			  end if;

			when controle   => 
	      	          if  (tmpclk = '1' ) then
	      	                --Si le bit de parite est erroné on met FErr à 1 pour signaliser l'erreur
				if(tmprxd = not p) then
					FErr <= '1';
				end if;
				--On passe à l'étape de verification du bit de stop 
				etat <= reception_Fin;
			   end if;
		      when reception_Fin => 
		           if  (tmpclk = '1' ) then
		                 --Si le bit de stop est erroné on met FErr pour signalisé l'erreur et Ddry à 0
				if(not(tmprxd ='1')) then
				       FErr <= '1';
					DRdy <= '0';
					etat <= start;
				else 
				-- Si le bit de stop est correcte on met en sortie la data reçu et Drdy à 1 
				      DRdy <= '1';
				      data <= data_aux;
				      etat <= fin;
				end if;
			   end if;
			when fin  =>
		            --à la fin on rend Drdy à 0 et si il y a une erreur de lecture on signale ça en mettant OErr à 1 
			  Drdy <= '0';
			  if(read = '0') then
				 OErr <= '1';
			  else
				etat <= start;
			  end if;
			end case;
		end if;
	 end process;
end Behavioral;
