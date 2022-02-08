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
  
  COMPONENT compteur 
	PORT(
		clk : in  STD_LOGIC;
      reset : in  STD_LOGIC;
	   cpt : out  STD_LOGIC_VECTOR (3 downto 0);
		carry : out  STD_LOGIC);
	END COMPONENT;
	
	COMPONENT diviseurClk 
		 generic(facteur : natural);
		 PORT(
			clk, reset : in  std_logic;
			nclk       : out std_logic);
	END COMPONENT;
   
	COMPONENT dec7seg
	PORT(
		v : IN std_logic_vector(3 downto 0);          
		seg : OUT std_logic_vector(7 downto 0)
		);
	END COMPONENT;
	
	signal i1 : std_logic;
	signal i2 : std_logic_vector(3 downto 0);

	
begin

  -- valeurs des sorties (à modifier)

  -- convention afficheur 7 segments 0 => allumé, 1 => éteint
  -- aucun afficheur sélectionné
  an(7 downto 1) <= (others => '1');
  an(0) <= '0';
  -- 16 leds éteintes
  led(15 downto 1) <= (others => '0');

  -- connexion du (des) composant(s) avec les ports de la carte
  Inst_diviseurClk: diviseurClk generic map(100000000)
	PORT MAP(
		clk => mclk,
		reset => not btnC,
		nclk => i1
	);
	
	Inst_compteur: compteur PORT MAP(
		clk => i1,
        reset => not btnC,
	    cpt => i2 ,
		carry => led(0) 
	);
    
    
    
    Inst_dec7seg: dec7seg PORT MAP(
		v => i2,
		seg => ssg
	);

end synthesis;
