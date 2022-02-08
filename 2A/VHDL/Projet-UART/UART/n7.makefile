
SRC = ../clkUnit/clkUnit.vhd             \
      RxUnit.vhd \
      compteur.vhd\
      ControleR.vhd\
      test_RxUnit.vhd

# for simulation:
TEST = test_RxUnit
# duration (to adjust if necessary)
TIME = 4000ns
PLOT = output
