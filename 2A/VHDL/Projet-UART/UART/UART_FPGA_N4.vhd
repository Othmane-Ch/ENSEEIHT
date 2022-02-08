library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.std_logic_arith.all;
use IEEE.std_logic_unsigned.all;

entity UART_FPGA_N4 is
  port (
  -- ne garder que les ports utiles ?
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
    -- les ports rajoutés (voir le fichier .ucf)
  	 TxD : out std_logic;
  	 RxD : in std_logic
  );
end UART_FPGA_N4;

architecture synthesis of UART_FPGA_N4 is

  -- Composant : echoUnit
  component echoUnit is
	  port (
	  clk, reset : in  std_logic;
	  cs, rd, wr : out  std_logic;
	  IntR       : in std_logic;         -- interruption de réception
	  IntT       : in std_logic;         -- interruption d'émission
	  addr       : out  std_logic_vector(1 downto 0);
	  data_in    : in  std_logic_vector(7 downto 0);
	  data_out   : out std_logic_vector(7 downto 0));
  end component;

  -- Composant : UARTunit
  component UARTunit is
	  port (
		 clk, reset : in  std_logic;
		 cs, rd, wr : in  std_logic;
		 RxD        : in  std_logic;
		 TxD        : out std_logic;
		 IntR, IntT : out std_logic;
		 addr       : in  std_logic_vector(1 downto 0);
		 data_in    : in  std_logic_vector(7 downto 0);
		 data_out   : out std_logic_vector(7 downto 0));
  end component;

  -- Composant : diviseurclk
  component diviseurClk is generic(facteur:natural);
	  port (
		 clk, reset : in  std_logic;
		 nclk       : out std_logic);
  end component;

  --Les signaux internes qu'on va utiliser
  signal aclk,cs ,rd ,wr , IntR , IntT : std_logic;
  signal   addr : std_logic_vector(1 downto 0);
  signal   d1,d2 : std_logic_vector(7 downto 0);

begin

  -- valeurs des sorties :

  -- convention afficheur 7 segments 0 => allumé, 1 => éteint
  --Les sorties non utilisées
  ssg <= (others => '1');
  -- aucun afficheur sélectionné
  an(7 downto 0) <= (others => '1');
  -- 16 leds éteintes
  led(15 downto 0) <= (others => '0');

      -- connexion du (des) composant(s) avec les ports de la carte
      -- Selon la fréquence nécessaire on a trouvé le facteur de diviseurClk 645 donc on instancie ce composant avec cette valeur en parametre
    		uut: diviseurClk
    		GENERIC MAP(645)
    		  PORT MAP (
    			 clk => mclk,
    			 reset => not btnC,
    			 nclk => aclk
    		  );
      --On instancie les différents composants et on fait les branchements nécessaires
    		UART :  UARTunit
    		port map (
    		 clk => aclk,
    		 reset => not btnC,
    		 cs => cs,
    		 rd => rd,
    		 wr => wr,
    		 RxD => RxD,
    		 TxD => TxD,
    		 IntR => IntR,
    		 IntT => IntT ,
    		 addr => addr,
    		 data_in => d1 ,
    		 data_out => d2  );

    		echoUnit1 :  echoUnit
    		 port map (
    		 clk => aclk,
    		 reset => not btnC,
    		 cs => cs,
    		 rd => rd,
    		 wr => wr,
    		 IntR => IntR,
    		 IntT => IntT ,
    		 addr => addr,
    		 data_in => d2,
    		 data_out => d1  );

end synthesis;
