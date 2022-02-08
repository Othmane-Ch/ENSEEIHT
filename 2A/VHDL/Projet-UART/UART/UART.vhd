library IEEE;
use IEEE.std_logic_1164.all;

entity UARTunit is
  port (
    clk, reset : in  std_logic;
    cs, rd, wr : in  std_logic;
    RxD        : in  std_logic;
    TxD        : out std_logic;
    IntR, IntT : out std_logic;
    addr       : in  std_logic_vector(1 downto 0);
    data_in    : in  std_logic_vector(7 downto 0);
    data_out   : out std_logic_vector(7 downto 0));
end UARTunit;


architecture UARTunit_arch of UARTunit is

    	 -- Le composant :  ctrlUnit
    	 component ctrlUnit is
    	  port (
    		 clk, reset       : in  std_logic;
    		 rd, cs           : in  std_logic;
    		 DRdy, FErr, OErr : in  std_logic;
    		 BufE, RegE       : in  std_logic;
    		 IntR             : out std_logic;
    		 IntT             : out std_logic;
    		 ctrlReg          : out std_logic_vector(7 downto 0));
    	 end component;

    	 -- Le composant : clkUnit
    	 component clkUnit is

    	 port (
    		clk, reset : in  std_logic;
    		enableTX   : out std_logic;
    		enableRX   : out std_logic);
    	 end component;

    	 -- Le composant : TxUnit
    	 component TxUnit is
    	  port (
    		 clk, reset : in std_logic;
    		 enable : in std_logic;
    		 ld : in std_logic;
    		 txd : out std_logic;
    		 regE : out std_logic;
    		 bufE : out std_logic;
    		 data : in std_logic_vector(7 downto 0));
    	 end component;

    	 -- Le composant : RxUnit
    	 component RxUnit is
    	  port (
    		 clk, reset       : in  std_logic;
    		 enable           : in  std_logic;
    		 read             : in  std_logic;
    		 rxd              : in  std_logic;
    		 data             : out std_logic_vector(7 downto 0);
    		 Ferr, OErr, DRdy : out std_logic);
    	 end component;

    -- Les signaux internes
	  signal lecture, ecriture : std_logic;
  	signal donnees_recues : std_logic_vector(7 downto 0);
	  signal registre_controle : std_logic_vector(7 downto 0);
    signal enableTX1, enableRX1 : std_logic;
    signal regE, bufE, DRdy1, FErr1, OErr1  : std_logic;

  begin  -- UARTunit_arch

          lecture <= '1' when cs = '0' and rd = '0' else '0';
          ecriture <= '1' when cs = '0' and wr = '0' else '0';
          data_out <= donnees_recues when lecture = '1' and addr = "00"
                      else registre_controle when lecture = '1' and addr = "01"
                      else "00000000";

          --On fait les différents branchements entre les différents composants de UART
          clkUnit1 : clkUnit port map (clk, reset, enableTX1, enableRX1);
          TxUnit1 : TxUnit port map (clk, reset, enableTX1, ecriture,  TxD, regE, bufE, data_in);
          RxUnit1 : RxUnit port map (clk, reset, enableRX1, lecture, RxD, donnees_recues, Ferr1, OErr1, DRdy1);
          ctrlUnit1 : ctrlUnit port map (clk, reset, rd, cs, DRdy1, FErr1, OErr1, bufE, regE, IntR, IntT, registre_controle);

  end UARTunit_arch;
