library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity MasterOpl is
  port ( rst : in std_logic;
         clk : in std_logic;
         en : in std_logic;
         v1 : in std_logic_vector (7 downto 0);
         v2 : in std_logic_vector(7 downto 0);
         miso : in std_logic;
         ss   : out std_logic;
         sclk : out std_logic;
         mosi : out std_logic;
         val_nand : out std_logic_vector (7 downto 0);
         val_nor : out std_logic_vector (7 downto 0);
         val_xor : out std_logic_vector (7 downto 0);
         busy : out std_logic);
end MasterOpl;

architecture behavior of MasterOpl is
    type t_etat is (idle,attente,envoi1,envoi2,envoi3);
    signal etat : t_etat;
    
    signal en_er : std_logic;
    signal din_er : std_logic_vector(7 downto 0);
    signal dout_er : std_logic_vector(7 downto 0);
    signal busy_er : std_logic;
    
    component er_1octet
	port ( rst : in std_logic ;
         clk : in std_logic ;
         en : in std_logic ;
         din : in std_logic_vector (7 downto 0) ;
         miso : in std_logic ;
         sclk : out std_logic ;
         mosi : out std_logic ;
         dout : out std_logic_vector (7 downto 0) ;
         busy : out std_logic);
	end component;
	
begin

    Inst_er_1octet: er_1octet PORT MAP(
            rst => rst,
            clk => clk,
            en => en_er,
            din => din_er,
            miso => miso,
            sclk => sclk,
            mosi => mosi,
            dout => dout_er,
            busy => busy_er
        );
    process(clk, rst)
        variable cpt: natural;

        variable octet: natural;
    begin                       
        if (rst = '0') then
            -- réinitialisation des variables du process
            -- et des signaux calculés par le process
            octet := 1;
            -- l'indicateur de fonctionnement/occupation
            busy <= '0';
            ss <= '1';      
            
            val_nand <= (others => '0');
            val_nor <= (others => '0');
            val_xor <= (others => '0');    
            --l'état
            etat <= idle;       
        elsif(rising_edge(clk)) then 
                  case etat is
                        when idle =>
                            -- état d'attente d'un ordre 
                                if(en = '1') then
                                -- un ordre est détecté
                                    ss <= '0';
                                    -- on signale qu'on est occupé
                                    busy <= '1';
                                    -- on initialise le compteur d'attente pour 10 cycle
                                    cpt := 10;
                                    -- on change d'état 
                                    etat <= attente;
                                end if;
                        when attente =>
                                if(cpt = 0) then
                                    -- on donne un ordre au composent er_1octet  
                                    en_er <= '1';
                                    -- transmission selon les valeurs de octet
                                    if (octet = 1) then
                                        -- envoie de v1
                                        din_er <= v1;
                                        etat <= envoi1;
                                    elsif (octet = 2) then
                                        -- envoie de v2
                                        din_er <= v2;
                                        etat <= envoi2;
                                    elsif (octet = 3) then
                                        -- envoie du 3ème octet non utilisé par la suite
                                        din_er <= (others => '0');
                                        etat <= envoi3;
                                    end if;
                                else
                                    -- on décrémente le compteur 
                                    cpt := cpt - 1;
                                end if;
                        when envoi1 =>
                                en_er <= '0';
                                -- si on a terminé l'echange
                                if (busy_er = '0' and en_er ='0') then
                                    -- on affecte le val_nand
                                    val_nand <= dout_er;
                                    -- on initialise le compteur d'attente pour 3 cycle
                                    cpt := 3;
                                    -- on passe au deuxième octet
                                    octet := 2;
                                    -- on change d'état
                                    etat <= attente;
                                end if;
                                
                        when envoi2 => 
                                en_er <= '0';
                                -- si on a terminé l'echange
                                if (busy_er = '0' and en_er = '0') then
                                    val_nor <= dout_er;
                                    -- on initialise le compteur d'attente pour 3 cycle
                                    cpt := 3;
                                    -- on passe au troisième octet
                                    octet := 3;
                                    -- on change d'état
                                    etat <= attente;
                                end if;
                                                                
                        when envoi3 => 
                                en_er <= '0';
                                -- si on a terminé l'echange
                                if (busy_er = '0' and en_er = '0') then
                                    val_xor <= dout_er;
                                    -- on initialise le compteur d'attente pour 3 cycle
                                    cpt := 3;
                                    octet := 1;
                                    -- on signale qu'on a terminé
                                    ss <= '1';
                                    busy <= '0';
                                    -- on change d'état
                                    etat <= idle;
                                end if;
                        end case;                          
        end if;                                     
    end process;        
end behavior;
