----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    09:08:52 09/24/2021 
-- Design Name: 
-- Module Name:    add4 - structural 
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

entity add4 is
    Port ( A : in  STD_LOGIC_VECTOR (3 downto 0);
           B : in  STD_LOGIC_VECTOR (3 downto 0);
           carry_in : in  STD_LOGIC;
           S : out  STD_LOGIC_VECTOR (3 downto 0);
           carry_out : out  STD_LOGIC);
end add4;

architecture structural of add4 is

	

	COMPONENT additionneur
	PORT(
		X : IN std_logic;
		Y : IN std_logic;
		Cin : IN std_logic;          
		S : OUT std_logic;
		Cout : OUT std_logic
		);
	END COMPONENT;
	
	signal i1, i2, i3 : std_logic;

begin

	a1: additionneur PORT MAP(
		X => A(0),
		Y => B(0),
		Cin => carry_in,
		S => S(0),
		Cout => i1	);
	
	a2: additionneur PORT MAP(
		X => A(1) ,
		Y => B(1),
		Cin => i1,
		S => S(1),
		Cout => i2
	);
	
	a3: additionneur PORT MAP(
		X => A(2) ,
		Y => B(2),
		Cin =>i2,
		S => S(2),
		Cout => i3
	);
	
	a4: additionneur PORT MAP(
		X => A(3) ,
		Y => B(3),
		Cin => i3,
		S => S(3),
		Cout => carry_out
	);

end structural;

