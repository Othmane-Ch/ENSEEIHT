library IEEE;
use IEEE.std_logic_1164.all;

entity TxUnit is
  port (
    clk, reset : in std_logic;
    enable : in std_logic;
    ld : in std_logic;
    txd : out std_logic;
    regE : out std_logic;
    bufE : out std_logic;
    data : in std_logic_vector(7 downto 0));
end TxUnit;

architecture behavorial of TxUnit is
 -- Etats de TxUnit
 type t_etat is ( repos, chargementReg, lancement, emission_1, emission_data, emission_parite, emission_stop);
  signal etat : t_etat;
	
begin

 process (clk, reset) 
	-- variables utilisés
	variable bufferT : std_logic_vector(7 downto 0);
	variable registreT : std_logic_vector(7 downto 0);
	variable bufE_aux : std_logic;
	variable regE_aux : std_logic;
	variable p : std_logic;
	variable cpt : natural;
 begin
    -- si on réinitialise avec un reset
    if(reset = '0') then
			-- initalisation
         bufferT := (others=> '0');    
         registreT :=(others => '0');
			bufE_aux := '1';
			regE_aux :='1';
			bufE <= bufE_aux;
			regE <= regE_aux;
			txd<='1';
			-- On passe à l'état repos pour attendre un ordre de transmission
			etat <= repos;
	 -- Si on a un front montant d'horloge on test sur les états 
    elsif(rising_edge(clk)) then
	   case etat is 
			when repos => 
				 --  Quand ld passe à 1 on passe au chargement de la data dans le reg
				  if (ld = '1') then
						  bufferT := data;
						  bufE_aux := '0';
						  bufE <= bufE_aux;
						  -- on passe à l'état suivant 
						  etat <= chargementReg;
				  end if;
			when chargementReg =>
				  -- on charge le registre 
				  registreT := bufferT;
				  bufE_aux := '1';
				  bufE <= bufE_aux;
				  regE_aux := '0';
				  regE <= regE_aux;
				  etat <= lancement;
			when lancement =>
				  -- on commence l'envoi dès que enable = 1
				  if (enable = '1') then
					  txd <= '0';
					  cpt := 7;
					  etat <= emission_1;
				  end if;
				  -- toujours remplir buffer si possible
				  if(ld = '1' and bufE_aux = '1') then 
						  bufferT := data;
						  bufE_aux := '0';
						  bufE <= bufE_aux;
				  end if;
			when emission_1 => 
				 -- on continue l'emission
			    if (enable ='1') then 
				 txd <= registreT(cpt);
				 p := registreT(cpt);
				 etat <= emission_data;
				 end if;
				 -- toujours remplir buffer si possible
				 if(ld = '1' and bufE_aux = '1') then 
						  bufferT := data;
						  bufE_aux := '0';
						  bufE <= bufE_aux;
				  end if;
				  
			when emission_data =>
				  if (cpt > 0 and enable = '1') then
						cpt := cpt - 1;
						txd <= registreT(cpt);
						-- calcul du bit de parité
						p := p xor registreT(cpt);
				  elsif ( cpt = 0) then
						etat <= emission_parite;
				  end if;
				  -- toujours remplir buffer si possible
				  if(ld = '1' and bufE_aux = '1') then 
						  bufferT := data;
						  bufE_aux := '0';
						  bufE <= bufE_aux;
				  end if;
				  
			when emission_parite =>
				  if  (enable ='1') then
						-- emission du bit de parité
						txd <= p;
						regE_aux := '1';
						regE <= regE_aux;
						-- passage à l'état stop
						etat <= emission_stop;
				  end if;
				  -- toujours remplir buffer si possible
				  if(ld = '1' and bufE_aux = '1') then 
						  bufferT := data;
						  bufE_aux := '0';
						  bufE <= bufE_aux;
				  end if;
			
			when emission_stop => 
				  -- on envoie le bit de stop et on revients au repos
				  if (enable ='1' and bufE_aux ='1') then 
					  txd <='1';
					  etat <= repos;
				  -- sinon on passe directement au chargement 
				  elsif (enable ='1' and bufE_aux ='0') then
						txd <='1';
						etat <= chargementReg;
				  end if;
				  -- toujours remplir buffer si possible
				  if(ld = '1' and bufE_aux = '1') then 
						  bufferT := data;
						  bufE_aux := '0';
						  bufE <= bufE_aux;
				  end if;
	   end case; 
    end if;	  
 end process;        
end behavorial;