library IEEE;
use IEEE.std_logic_1164.all;

entity RxUnit is
  port (
    clk, reset       : in  std_logic;
    enable           : in  std_logic;
    read             : in  std_logic;
    rxd              : in  std_logic;
    data             : out std_logic_vector(7 downto 0);
    Ferr, OErr, DRdy : out std_logic);
end RxUnit;

architecture RxUnit_arch of RxUnit is

        
	 component compteur is
    port ( enable : in  STD_LOGIC;
           reset : in  STD_LOGIC;
           RxD : in  STD_LOGIC;
           tmprxd : out  STD_LOGIC;
           tmpclk : out  STD_LOGIC);
    end component;


	 component ControleR is
    port ( clk : in  STD_LOGIC;
           reset : in  STD_LOGIC;
           read : in  STD_LOGIC;
           tmpclk : in  STD_LOGIC;
           tmprxd : in  STD_LOGIC;
           FErr : out  STD_LOGIC;
           OErr : out  STD_LOGIC;
           DRdy : out  STD_LOGIC;
           data : out  STD_LOGIC_VECTOR (7 downto 0));
	end component;

         --On définit les signaux internes entre le compteur et le ControleR dans RxUnit
	 signal tmpclk1, tmprxd1 : std_logic;

	begin
         --On fait les branchements du compteur avec le ControleR et on les instancie
	compteur16_inst : compteur port map ( enable,reset, rxd, tmprxd1 , tmpclk1);
	ControleR_inst : ControleR port map( clk, reset,read, tmpclk1 , tmprxd1 ,Ferr, OErr,DRdy, data);

end RxUnit_arch;
