----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    14:18:27 10/08/2021 
-- Design Name: 
-- Module Name:    compteur - Behavioral 
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
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;
-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity compteur is
    Port ( clk : in  STD_LOGIC;
           reset : in  STD_LOGIC;
           cpt : out  STD_LOGIC_VECTOR (3 downto 0);
           carry : out  STD_LOGIC);
end compteur;

architecture Behavioral of compteur is

begin
	process(clk,reset)
		variable cpt_aux : STD_LOGIC_VECTOR (3 downto 0) := (others => '0');
		begin
			if (reset = '0') then 
				cpt_aux := (others => '0');
				carry <= '0';
				cpt <= cpt_aux;
			elsif(rising_edge(clk)) then 
				cpt_aux := cpt_aux + 1;
				cpt <= cpt_aux;
				if(cpt_aux = 0) then 
					carry <= '1';
				else 
					carry <= '0';
				end if;
			end if;
	end process;


end Behavioral;

