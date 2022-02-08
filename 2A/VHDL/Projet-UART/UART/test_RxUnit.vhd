-- TestBench Template

  LIBRARY ieee;
  USE ieee.std_logic_1164.ALL;
  USE ieee.numeric_std.ALL;

  ENTITY test_RxUnit IS
  END test_RxUnit;

  ARCHITECTURE behavior OF test_RxUnit IS

    component RxUnit is
		  port (
			 clk, reset       : in  std_logic;
			 enable         : in  std_logic;
			 read             : in  std_logic;
			 rxd              : in  std_logic;
			 data             : out std_logic_vector(7 downto 0);
			 Ferr, OErr, DRdy : out std_logic);
	 end component;

	 component clkUnit is
			 port (
				clk, reset : in  std_logic;
				enableTX   : out std_logic;
				enableRX   : out std_logic);
	 end component;

    SIGNAL clk : std_logic ;
    SIGNAL reset : std_logic;
    SIGNAL rd : std_logic;
    SIGNAL rxd : std_logic ;

    SIGNAL octet :  std_logic_vector(7 downto 0);
    SIGNAL data :  std_logic_vector(7 downto 0);
    SIGNAL Ferr, OErr, DRdy : std_logic;
    SIGNAL enableTX   : std_logic;
    SIGNAL enableRX   : std_logic;

    -- Clock period definitions
    constant clk_period : time := 2 ns;

  BEGIN

  -- Component Instantiation
          ClkUnit_inst: clkUnit PORT MAP(
                  clk, reset, enableTX, enableRX);
          RxUnit_inst: RxUnit PORT MAP(
                  clk, reset, enableRX, rd, rxd,data, FErr, OErr, DRdy);
    -- Clock process definitions
    clk_process :process

    begin
      clk <= '0';
      wait for clk_period/2;
      clk <= '1';
      wait for clk_period/2;
    end process;
  --  Test Bench Statements
     reset <= '0', '1' after 80 ns;
	  process
	      variable parite :  std_logic;
	  begin
    --Emission normale
	         rd <= '0';
		 rxd <='1';
		 --On commence l'émission
		 wait until enableTX = '1';
		 -- le bit de start
		 rxd <= '0';
		 wait until enableTX = '0';
		 --l'octet
		 octet <= "01010101";
		 --bit fort
	         parite := '0';
		 --reste des bits
		 for i in 7 downto 0 loop
			  wait until enableTX = '1';
			  rxd<= octet(i);
			  parite := parite xor octet(i);
			  wait until enableTX ='0';
		 end loop;
		 --bit de parite
		 wait until enableTX = '1';
		 rxd <=parite;
		 wait until enableTX = '0';
		 --bit de stop
		 wait until enableTX ='1';
		 rxd <= '1';
		 wait until enableTX ='0';

		 wait until (DRdy ='1' or FErr='1');

		 if DRdy ='1' then
		  rd <= '1';
		 end if;

		 wait until (DRdy ='0' or FErr = '0');

		 wait for 100 ns;

     --Emission avec bit de parite erroné
	         rd <= '0';
		 rxd <='1';
		 --On commence l'émission
		 wait until enableTX = '1';
		 -- le bit de start
		 rxd <= '0';
		 wait until enableTX = '0';
		 --l'octet
		 octet <= "10101010";
		 --bit fort
	         parite := '0';
		 --reste des bits
		 for i in 7 downto 0 loop
			  wait until enableTX = '1';
			  rxd<= octet(i);
			  parite := parite xor octet(i);
			  wait until enableTX ='0';
		 end loop;
		 --bit de parite erroné
		 wait until enableTX = '1';
		 rxd <= not(parite);
		 wait until enableTX = '0';
		 --bit de stop
		 wait until enableTX ='1';
		 rxd <= '1';
		 wait until enableTX ='0';

		 wait until (DRdy ='1' or FErr='1');

		 if DRdy ='1' then
		  rd <= '1';
		 end if;

		 wait until (DRdy ='0' or FErr = '0');

		 wait for 100 ns;

   	 --Emission avec bit de stop erroné
	         rd <= '0';
		 rxd <='1';
		 --On commence l'émission
		 wait until enableTX = '1';
		 -- le bit de start
		 rxd <= '0';
		 wait until enableTX = '0';
		 --l'octet
		 octet <= "10101010";
		 --bit fort
	         parite := '0';
		 --reste des bits
		 for i in 7 downto 0 loop
			  wait until enableTX = '1';
			  rxd<= octet(i);
			  parite := parite xor octet(i);
			  wait until enableTX ='0';
		 end loop;
		 --bit de parite
		 wait until enableTX = '1';
		 rxd <= (parite);
		 wait until enableTX = '0';
		 --bit de stop erroné
		 wait until enableTX ='1';
		 rxd <= '0';
		 wait until enableTX ='0';

		 wait until (DRdy ='1' or FErr='1');

		 if DRdy ='1' then
		  rd <= '1';
		 end if;

		 wait until (DRdy ='0' or FErr = '0');

		 wait for 100 ns;

     end process;

  END;
