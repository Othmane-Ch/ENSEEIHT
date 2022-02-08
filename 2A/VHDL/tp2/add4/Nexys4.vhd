library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.std_logic_arith.all;
use IEEE.std_logic_unsigned.all;

entity Nexys4 is
  port (
    -- les 16 switchs
    swt : in std_logic_vector (15 downto 0);
    -- les 5 boutons noirs
    btnC, btnU, btnL, btnR, btnD : in std_logic;
    -- horloge
    mclk : in std_logic;
    -- les 16 leds
    led : out std_logic_vector (15 downto 0);
    -- les anodes pour sélectionner les afficheurs 7 segments à utiliser
    an : out std_logic_vector (7 downto 0);
    -- valeur affichée sur les 7 segments (point décimal compris, segment 7)
    ssg : out std_logic_vector (7 downto 0)
  );
end Nexys4;

architecture synthesis of Nexys4 is

  -- rappel du (des) composant(s)
  COMPONENT add4
	PORT(
		A : IN std_logic_vector(3 downto 0);
		B : IN std_logic_vector(3 downto 0);
		carry_in : IN std_logic;          
		S : OUT std_logic_vector(3 downto 0);
		carry_out : OUT std_logic
		);
	END COMPONENT;
	COMPONENT dec7seg
	PORT(
		v : IN std_logic_vector(3 downto 0);          
		seg : OUT std_logic_vector(7 downto 0)
		);
	END COMPONENT;
	
	signal i1 : std_logic_vector(3 downto 0);
	
begin

  -- valeurs des sorties (à modifier)

  -- convention afficheur 7 segments 0 => allumé, 1 => éteint
  -- aucun afficheur sélectionné
  an(7 downto 0) <= (others => '0');
  -- 16 leds éteintes
  led(15 downto 1) <= (others => '0');

  -- connexion du (des) composant(s) avec les ports de la carte
  Inst_add4: add4 PORT MAP(
		A => swt(3 downto 0),
		B => swt(7 downto 4),
		carry_in => btnC,
		S => i1,
		carry_out => led(0)
	);

  Inst_dec7seg: dec7seg PORT MAP(
		v => i1,
		seg => ssg
	);

end synthesis;
