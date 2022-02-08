--------------------------------------------------------------------------------
-- Company: 
-- Engineer:
--
-- Create Date:   09:29:45 09/24/2021
-- Design Name:   
-- Module Name:   /home/ochaouch/2A/archi_ord/tp1/test_add4.vhd
-- Project Name:  projet_additionneur
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: add4
-- 
-- Dependencies:
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
--
-- Notes: 
-- This testbench has been automatically generated using types std_logic and
-- std_logic_vector for the ports of the unit under test.  Xilinx recommends
-- that these types always be used for the top-level I/O of a design in order
-- to guarantee that the testbench will bind correctly to the post-implementation 
-- simulation model.
--------------------------------------------------------------------------------
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
USE IEEE.std_logic_arith.all;
USE IEEE.std_logic_unsigned.all;

 
-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--USE ieee.numeric_std.ALL;
 
ENTITY test_add4 IS
END test_add4;
 
ARCHITECTURE behavior OF test_add4 IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT add4
    PORT(
         A : IN  std_logic_vector(3 downto 0);
         B : IN  std_logic_vector(3 downto 0);
         carry_in : IN  std_logic;
         S : OUT  std_logic_vector(3 downto 0);
         carry_out : OUT  std_logic
        );
    END COMPONENT;
    

   --Inputs
   signal A : std_logic_vector(3 downto 0) := (others => '0');
   signal B : std_logic_vector(3 downto 0) := (others => '0');
   signal carry_in : std_logic := '0';

 	--Outputs
   signal S : std_logic_vector(3 downto 0);
   signal carry_out : std_logic;
   
 
   constant periode : time := 50 ns;
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: add4 PORT MAP (
          A => A,
          B => B,
          carry_in => carry_in,
          S => S,
          carry_out => carry_out
        );
	
	genere_A: process
		begin
			wait for periode;
			A <= A+1;
		end process;

	genere_B: process
		begin
			wait for 16*periode;
			B <= B+1;
		end process;
		
	genere_Carryin: process
		begin
			wait for 256*periode;
			carry_in <= '1';
		end process;
	

   

END;
