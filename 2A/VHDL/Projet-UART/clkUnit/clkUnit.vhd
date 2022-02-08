library IEEE;
use IEEE.std_logic_1164.all;
USE ieee.std_logic_arith.ALL;
USE ieee.std_logic_unsigned.ALL;

entity clkUnit is
  
 port (
   clk, reset : in  std_logic;
   enableTX   : out std_logic;
   enableRX   : out std_logic);
    
end clkUnit;

architecture behavorial of clkUnit is

begin
  process (clk,reset)
     --On définit un compteur   
     variable cpt_aux : STD_LOGIC_VECTOR (3 downto 0 ) := (others => '0');
   begin 
	    --enableRX prend toujours la valeur de clk sauf si reset est nulle
	    enableRX <= clk and reset;
		 if (reset ='0') then 
			cpt_aux := (others => '0');
			--enableTX est nulle quand reset est nulle 
			enableTX <='0';
		 elsif (rising_edge(clk)) then 
		     -- Si 16 tops d'horloge se sont passés enableTX passe à 1 
			  if (cpt_aux =15) then 
				enableTX<='1';
				cpt_aux :=(others => '0');
			 else
			   --Sinon enableTX égale à 1 
				enableTX <='0';
				--On incrémente le compteur
				cpt_aux := cpt_aux +1;
			end if;
		end if;

		end process;
end behavorial;
