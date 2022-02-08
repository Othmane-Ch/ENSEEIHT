library IEEE;
use IEEE.std_logic_1164.all; 

entity Nexys4Joystick is
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
    ssg : out std_logic_vector (7 downto 0);
    
    ss : out std_logic;
    
    sclk : out std_logic;
    
    mosi : out std_logic;
    
    miso : in std_logic  
  );
end Nexys4Joystick;

architecture synthesis of Nexys4Joystick is

  -- rappel du (des) composant(s)
  
	COMPONENT MasterJoystick
		PORT(
			 rst : in std_logic;
				clk : in std_logic;
				en : in std_logic;
				miso : in std_logic;
				led1 : in std_logic;
				led2 : in std_logic; 
				x : out std_logic_vector (9 downto 0);
				y : out std_logic_vector(9 downto 0);
				btn1 : out std_logic;
				btn2 : out std_logic;
				btnJ : out std_logic;
				ss : out std_logic;
				sclk : out std_logic;
				mosi : out std_logic;
				busy : out std_logic);
	END COMPONENT;
	
	COMPONENT diviseurClk 
		 generic(facteur : natural);
		 PORT(
			clk, reset : in  std_logic;
			nclk       : out std_logic);
	END COMPONENT;
	
	
    COMPONENT dec7seg
	PORT(
		value: IN std_logic_vector(3 downto 0);          
		seg : OUT std_logic_vector(7 downto 0)
		);
	END COMPONENT;
	
	COMPONENT All7Segments is
            PORT ( 
                   clk : in  std_logic;
                   reset : in std_logic;
                   e0 : in std_logic_vector (3 downto 0);
                   e1 : in std_logic_vector (3 downto 0);
                   e2 : in std_logic_vector (3 downto 0);
                   e3 : in std_logic_vector (3 downto 0);
                   e4 : in std_logic_vector (3 downto 0);
                   e5 : in std_logic_vector (3 downto 0);
                   e6 : in std_logic_vector (3 downto 0);
                   e7 : in std_logic_vector (3 downto 0);
                   an : out std_logic_vector (7 downto 0);
                   ssg : out std_logic_vector (7 downto 0));
    END COMPONENT;
    
    signal my_clk : std_logic;
    --signal i2 : std_logic_vector(3 downto 0);
    
    signal x : std_logic_vector(9 downto 0);
    signal y : std_logic_vector(9 downto 0);


begin

  -- valeurs des sorties (à modifier)
    
  -- convention afficheur 7 segments 0 => allumé, 1 => éteint
  --ssg <= (others => '1');
  -- aucun afficheur sélectionné
  --an(7 downto 0) <= (others => '1');
  -- 13 leds éteintes
  led(14 downto 3) <= (others => '0');

  -- connexion du (des) composant(s) avec les ports de la carte
  -- À COMPLÉTER 
   Inst_diviseurClk: diviseurClk generic map(100)
	PORT MAP(
		clk => mclk,
		reset => swt(0),
		nclk => my_clk
	);
    --Inst_dec7seg: dec7seg PORT MAP(
		--value => i2,
		--seg => ssg
	--);
	
	Inst_All7Segments: All7Segments PORT MAP(
		clk => mclk,
		reset => swt(0),
		e0 => x(3 downto 0),
		e1 => x(7 downto 4), 
		e2 => "00" & x(9 downto 8),
		e3 => "0000",
		e4 => y(3 downto 0),
		e5 => y(7 downto 4),
		e6 => "00" & y(9 downto 8),
		e7 => "0000",
		an => an,
		ssg => ssg
	);
	
	Inst_MasterJoystick: MasterJoystick PORT MAP(
        rst => swt(0),
        clk => my_clk,
        en => swt(1),
        miso => miso,
        led1 => btnD,
        led2 => btnU, 
        x => x,
        y => y,
        btn1 => led(1),
        btn2 => led(2),
        btnJ => led(0),
        ss => ss,
        sclk => sclk,
        mosi => mosi,
        busy => led(15) 
	);
	
    
end synthesis;
