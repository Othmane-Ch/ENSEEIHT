package projetLong;



public class MapTest1{

	private MapRobot mapRob;
	private MapReel mapReel;

    public MapTest1(){
        //super();
    	mapRob = new MapRobot();
    	mapReel = new MapReel();
    }

    /**
    * Re definit l'acces entre 2 cases, la direction symbolise ici de quelle liaison il s'agit :
    * 1 = Nord
    * 2 = Est
    * 3 = Sud
    * 4 = Ouest
    */
    public void blokAcces(double proba, int direction, int x, int y) {
        switch (direction) {
            case 1:
                this.mapRob.obtenirInfoCase(x-1, y-1).setAccesNord(proba);
                this.mapReel.obtenirInfoCase(x-1, y-1).setAccesNord(proba);
                break;
            case 2:
            	this.mapRob.obtenirInfoCase(x-1, y-1).setAccesEst(proba);
            	this.mapReel.obtenirInfoCase(x-1, y-1).setAccesEst(proba);
                break;
            case 3:
                this.mapRob.obtenirInfoCase(x-1, y-1).setAccesSud(proba);
                this.mapReel.obtenirInfoCase(x-1, y-1).setAccesSud(proba);
                break;
            case 4:
                this.mapRob.obtenirInfoCase(x-1, y-1).setAccesOuest(proba);
                this.mapReel.obtenirInfoCase(x-1, y-1).setAccesOuest(proba);
                break;
        }
    }
    public void Map1() {

        // On bloque les laisons des cases lignes par lignes

            /*On s'occupe des bordures de la map*/

        for (int k = 1; k <= 35; k++) { 
            blokAcces(0,4,1,k);
            blokAcces(0,2,20,k);
        }

        for (int k = 1; k <= 20; k++) { 
            blokAcces(0,3,k,35);
            blokAcces(0,1,k,1);
        }

            /*Ligne 1*/


        blokAcces(0,3,1,7);
        blokAcces(0,1,1,8);

        blokAcces(0,3,1,27);
        blokAcces(0,1,1,28);

            /*Ligne 2*/


        blokAcces(0,3,2,27);
        blokAcces(0,1,2,28);

        blokAcces(0,3,2,28);
        blokAcces(0,1,2,29);

         for (int k = 8; k <= 27; k++) { 
            if ( k!=13 && k!=23 ) {
               blokAcces(0,2,2,k);
            } 
        }

            /*Ligne 3*/

         for (int k = 8; k <= 27; k++) { 
             if ( k!=13 && k!=23 ) {
                blokAcces(0,4,3,k);
             } 
         }

        blokAcces(0,3,3,7);
        blokAcces(0,1,3,8);

        blokAcces(0,3,3,18);
        blokAcces(0,1,3,19);

        blokAcces(0,3,3,27);
        blokAcces(0,1,3,28);

        blokAcces(0,3,3,28);
        blokAcces(0,1,3,29);

            /*Ligne 4*/
         

        blokAcces(0,3,4,7);
        blokAcces(0,1,4,8);

        blokAcces(0,3,4,11);
        blokAcces(0,1,4,12);

        blokAcces(0,3,4,18);
        blokAcces(0,1,4,19);

        blokAcces(0,3,4,28);
        blokAcces(0,1,4,29);

            /*Ligne 5*/


        for (int k = 1; k <= 7; k++) { 
            if ( k!=3 ) {
                blokAcces(0,2,5,k);
            } 
        }
        
        blokAcces(0,3,5,9);
        blokAcces(0,1,5,10);

        blokAcces(0,3,5,7);
        blokAcces(0,1,5,8);

        blokAcces(0,4,5,10);
        blokAcces(0,4,5,11);

        blokAcces(0,3,5,18);
        blokAcces(0,1,5,19);

        blokAcces(0,3,5,27);
        blokAcces(0,1,5,28);

        blokAcces(0,3,5,28);
        blokAcces(0,1,5,29);

        for (int k = 29; k <= 35; k++) { 
            if ( k!=30 ) {
                blokAcces(0,2,5,k);
            } 
        }

            /*Ligne 6*/


        blokAcces(0,3,6,7);
        blokAcces(0,1,6,8);

        blokAcces(0,2,6,9);

        blokAcces(0,3,6,9);
        blokAcces(0,1,6,10);

        blokAcces(0,3,6,18);
        blokAcces(0,1,6,19);

        blokAcces(0,3,6,27);
        blokAcces(0,1,6,28);

        blokAcces(0,3,6,28);
        blokAcces(0,1,6,29);

        blokAcces(0,3,6,32);
        blokAcces(0,1,6,33);

         for (int k = 1; k <= 7; k++) { 
            if ( k!=3 ) {
                blokAcces(0,4,6,k);
            } 
        }

         for (int k = 29; k <= 35; k++) { 
            if ( k!=30 ) {
                blokAcces(0,4,6,k);
            } 
        }

            /*Ligne 7*/


        blokAcces(0,3,7,7);
        blokAcces(0,1,7,8);

        blokAcces(0,4,7,9);

        blokAcces(0,3,7,18);
        blokAcces(0,1,7,19);

        blokAcces(0,3,7,27);
        blokAcces(0,1,7,28);

        blokAcces(0,3,7,28);
        blokAcces(0,1,7,29);

        blokAcces(0,3,7,32);
        blokAcces(0,1,7,33);

         for (int k = 16; k <= 35; k++) { 
            if ( k!=21 && k!=28 && k!=34 ) {
                blokAcces(0,2,7,k);
            } 
        }

            /*Ligne 8*/


        blokAcces(0,3,8,15);
        blokAcces(0,1,8,16);

        blokAcces(0,3,8,26);
        blokAcces(0,1,8,27);

        blokAcces(0,3,8,29);
        blokAcces(0,1,8,30);

         for (int k = 16; k <= 35; k++) { 
            if ( k!=21 && k!=28 && k!=34 ) {
                blokAcces(0,4,8,k);
            } 
        }

            /*Ligne 9*/


        blokAcces(0,3,9,7);
        blokAcces(0,1,9,8);

        blokAcces(0,3,9,15);
        blokAcces(0,1,9,16);

        blokAcces(0,3,9,29);
        blokAcces(0,1,9,30);

        for (int k = 8; k <= 16; k++) { 
            if ( k!=11 && k!=14 ) {
                blokAcces(0,2,9,k);
            } 
        }

        blokAcces(0,2,9,23);

            /*Ligne 10*/


        blokAcces(0,3,10,7);
        blokAcces(0,1,10,8);

        blokAcces(0,3,10,12);
        blokAcces(0,1,10,13);

        blokAcces(0,3,10,15);
        blokAcces(0,1,10,16);

        blokAcces(0,3,10,23);
        blokAcces(0,1,10,24);

        blokAcces(0,3,10,26);
        blokAcces(0,1,10,27);

        blokAcces(0,3,10,29);
        blokAcces(0,1,10,30);

        for (int k = 27; k <= 30; k++) { 
            blokAcces(0,2,10,k);
        }

         for (int k = 8; k <= 16; k++) { 
            if ( k!=11 && k!=14 ) {
                blokAcces(0,4,10,k);
            } 
        }

         blokAcces(0,4,10,23);

            /*Ligne 11*/


        blokAcces(0,3,11,7);
        blokAcces(0,1,11,8);

        blokAcces(0,3,11,12);
        blokAcces(0,1,11,13);

        blokAcces(0,3,11,15);
        blokAcces(0,1,11,16);

        blokAcces(0,3,11,23);
        blokAcces(0,1,11,24);

        blokAcces(0,3,11,26);
        blokAcces(0,1,11,27);

         for (int k = 27; k <= 30; k++) { 
            blokAcces(0,4,11,k);
        }

        for (int k = 1; k <= 15; k++) { 
            if ( k!=4 ) {
                blokAcces(0,2,11,k);
            } 
        }

            /*Ligne 12*/


         for (int k = 1; k <= 15; k++) { 
            if ( k!=4 ) {
                blokAcces(0,4,12,k);
            } 
        }

        for (int k = 14; k <= 15; k++) { 
            blokAcces(0,2,12,k);
        }

        blokAcces(0,3,12,23);
        blokAcces(0,1,12,24);

            /*Ligne 13*/


        blokAcces(0,3,13,13);
        blokAcces(0,1,13,14);

        blokAcces(0,3,13,15);
        blokAcces(0,1,13,16);

        blokAcces(0,3,13,23);
        blokAcces(0,1,13,24);

        blokAcces(0,3,13,26);
        blokAcces(0,1,13,27);

        for (int k = 1; k <= 13; k++) { 
            if ( k!=7 && k!=8 ) {
                blokAcces(0,2,13,k);
            } 
        }

        for (int k = 14; k <= 15; k++) { 
            blokAcces(0,4,13,k);
        }

        for (int k = 27; k <= 30; k++) { 
            blokAcces(0,2,13,k);
        }

            /*Ligne 14*/


        blokAcces(0,3,14,6);
        blokAcces(0,1,14,7);

        blokAcces(0,3,14,8);
        blokAcces(0,1,14,9);

        blokAcces(0,3,14,15);
        blokAcces(0,1,14,16);

        blokAcces(0,3,14,23);
        blokAcces(0,1,14,24);

        blokAcces(0,3,14,26);
        blokAcces(0,1,14,27);

        blokAcces(0,3,14,29);
        blokAcces(0,1,14,30);

        blokAcces(0,2,14,23);

         for (int k = 1; k <= 13; k++) { 
            if ( k!=7 && k!=8 ) {
                blokAcces(0,4,14,k);
            } 
        }

         for (int k = 27; k <= 30; k++) { 
            blokAcces(0,4,14,k);
        }

            /*Ligne 15*/

        
        blokAcces(0,3,15,6);
        blokAcces(0,1,15,7);
    
        blokAcces(0,3,15,8);
        blokAcces(0,1,15,9);

        blokAcces(0,3,15,12);
        blokAcces(0,1,15,13);

        blokAcces(0,3,15,15);
        blokAcces(0,1,15,16);

        blokAcces(0,3,15,29);
        blokAcces(0,1,15,30);

        blokAcces(0,4,15,23);

        for (int k = 9; k <= 12; k++) { 
            if ( k!=10 ) {
                blokAcces(0,2,15,k);
            } 
        }

            /*Ligne 16*/


        blokAcces(0,3,16,8);
        blokAcces(0,1,16,9);

        blokAcces(0,3,16,12);
        blokAcces(0,1,16,13);

        blokAcces(0,3,16,15);
        blokAcces(0,1,16,16);

        blokAcces(0,3,16,26);
        blokAcces(0,1,16,27);

        blokAcces(0,3,16,29);
        blokAcces(0,1,16,30);

        for (int k = 9; k <= 12; k++) { 
            if ( k!=10 ) {
                blokAcces(0,4,16,k);
            } 
        }

        for (int k = 13; k <= 35; k++) { 
            if ( k!=21 && k!=28 && k!=34 ) {
                blokAcces(0,2,16,k);
            } 
        }

            /*Ligne 17*/


        blokAcces(0,3,17,6);
        blokAcces(0,1,17,7);

        blokAcces(0,3,17,8);
        blokAcces(0,1,17,9);

        blokAcces(0,3,17,21);
        blokAcces(0,1,17,22);

        blokAcces(0,3,17,27);
        blokAcces(0,1,17,28);

        blokAcces(0,3,17,28);
        blokAcces(0,1,17,29);

        blokAcces(0,3,17,32);
        blokAcces(0,1,17,33);

        for (int k = 13; k <= 35; k++) { 
            if ( k!=21 && k!=28 && k!=34 ) {
                blokAcces(0,4,17,k);
            } 
        }

        for (int k = 13; k <= 21; k++) { 
            if ( k!=14 && k!=19 ) {
                blokAcces(0,2,17,k);
            } 
        }

            /*Ligne 18*/


        blokAcces(0,3,18,6);
        blokAcces(0,1,18,7);
    
        blokAcces(0,3,18,8);
        blokAcces(0,1,18,9);

        blokAcces(0,3,18,12);
        blokAcces(0,1,18,13);

        blokAcces(0,3,18,17);
        blokAcces(0,1,18,18);

        blokAcces(0,3,18,21);
        blokAcces(0,1,18,22);

        blokAcces(0,3,18,28);
        blokAcces(0,1,18,29);

        blokAcces(0,3,18,32);
        blokAcces(0,1,18,33);
        
        for (int k = 13; k <= 21; k++) { 
            if ( k!=14 && k!=19 ) {
                blokAcces(0,4,18,k);
            } 
        }

        for (int k = 33; k <= 35; k++) { 
            blokAcces(0,2,18,k);   
        }

            /*Ligne 19*/


        blokAcces(0,3,19,6);
        blokAcces(0,1,19,7);
    
        blokAcces(0,3,19,8);
        blokAcces(0,1,19,9);

        blokAcces(0,3,19,12);
        blokAcces(0,1,19,13);

        blokAcces(0,3,19,17);
        blokAcces(0,1,19,18);

        blokAcces(0,3,19,21);
        blokAcces(0,1,19,22);

        blokAcces(0,3,19,27);
        blokAcces(0,1,19,28);

        blokAcces(0,3,19,32);
        blokAcces(0,1,19,33);

        for (int k = 33; k <= 35; k++) { 
            blokAcces(0,4,19,k);   
        }

            /*Ligne 20*/


        blokAcces(0,3,20,6);
        blokAcces(0,1,20,7);
    
        blokAcces(0,3,20,8);
        blokAcces(0,1,20,9);

        blokAcces(0,3,20,12);
        blokAcces(0,1,20,13);

        blokAcces(0,3,20,17);
        blokAcces(0,1,20,18);

        blokAcces(0,3,20,21);
        blokAcces(0,1,20,22);

        blokAcces(0,3,20,27);
        blokAcces(0,1,20,28);

        blokAcces(0,3,20,28);
        blokAcces(0,1,20,29);


        // On postionne tous les elements "inattendus" (civils, feu, debris)

            /*civils*/


        this.mapReel.obtenirInfoCase(0,34).setEtat(EtatCase.rescape);

        this.mapReel.obtenirInfoCase(0,18).setEtat(EtatCase.rescape);

        this.mapReel.obtenirInfoCase(1,0).setEtat(EtatCase.rescape);
        this.mapReel.obtenirInfoCase(1,2).setEtat(EtatCase.rescape);

        this.mapReel.obtenirInfoCase(4,20).setEtat(EtatCase.rescape);
        this.mapReel.obtenirInfoCase(5,23).setEtat(EtatCase.rescape);

        this.mapReel.obtenirInfoCase(8,2).setEtat(EtatCase.rescape);
        this.mapReel.obtenirInfoCase(9,4).setEtat(EtatCase.rescape);

        this.mapReel.obtenirInfoCase(9,16).setEtat(EtatCase.rescape);
        this.mapReel.obtenirInfoCase(10,18).setEtat(EtatCase.rescape);
        this.mapReel.obtenirInfoCase(11,17).setEtat(EtatCase.rescape);
        this.mapReel.obtenirInfoCase(12,19).setEtat(EtatCase.rescape);
        this.mapReel.obtenirInfoCase(13,18).setEtat(EtatCase.rescape);
        this.mapReel.obtenirInfoCase(13,20).setEtat(EtatCase.rescape);

        this.mapReel.obtenirInfoCase(10,8).setEtat(EtatCase.rescape);
        this.mapReel.obtenirInfoCase(10,9).setEtat(EtatCase.rescape);

        this.mapReel.obtenirInfoCase(10,30).setEtat(EtatCase.rescape);
        this.mapReel.obtenirInfoCase(10,32).setEtat(EtatCase.rescape);
        this.mapReel.obtenirInfoCase(12,31).setEtat(EtatCase.rescape);
        this.mapReel.obtenirInfoCase(13,32).setEtat(EtatCase.rescape);
        this.mapReel.obtenirInfoCase(14,30).setEtat(EtatCase.rescape);

        this.mapReel.obtenirInfoCase(14,28).setEtat(EtatCase.rescape);

        this.mapReel.obtenirInfoCase(14,10).setEtat(EtatCase.rescape);

        this.mapReel.obtenirInfoCase(14,2).setEtat(EtatCase.rescape);
        this.mapReel.obtenirInfoCase(16,0).setEtat(EtatCase.rescape);
        this.mapReel.obtenirInfoCase(18,1).setEtat(EtatCase.rescape);
        this.mapReel.obtenirInfoCase(18,3).setEtat(EtatCase.rescape);

        this.mapReel.obtenirInfoCase(16,9).setEtat(EtatCase.rescape);

        this.mapReel.obtenirInfoCase(19,32).setEtat(EtatCase.rescape);

            /*debris*/

        
        for (int k = 1; k <= 3; k++) { 
            this.mapReel.obtenirInfoCase(k-1,5).setEtat(EtatCase.inaccessible);  
            this.mapReel.obtenirInfoCase(0,14+k).setEtat(EtatCase.inaccessible);
        }

        for (int k = 1; k <= 2; k++) { 
            this.mapReel.obtenirInfoCase(k-1,30).setEtat(EtatCase.inaccessible);
            this.mapReel.obtenirInfoCase(5+k,6).setEtat(EtatCase.inaccessible);  
            this.mapReel.obtenirInfoCase(6+k,22).setEtat(EtatCase.inaccessible);
            this.mapReel.obtenirInfoCase(10+k,27).setEtat(EtatCase.inaccessible);
            this.mapReel.obtenirInfoCase(15,20+k).setEtat(EtatCase.inaccessible);
            this.mapReel.obtenirInfoCase(17,28+k).setEtat(EtatCase.inaccessible);
            this.mapReel.obtenirInfoCase(16,1+k).setEtat(EtatCase.inaccessible);
            this.mapReel.obtenirInfoCase(10,19+k).setEtat(EtatCase.inaccessible);
        }

        this.mapReel.obtenirInfoCase(11,21).setEtat(EtatCase.inaccessible);

            /*feu*/


        for (int k = 1; k <= 2; k++) {
            for (int j = 1; j <= 2; j++) {
                this.mapReel.obtenirInfoCase(3+j,8+k).setEtat(EtatCase.feu);
            }
        }

        for (int k = 1; k <= 2; k++) {
            for (int j = 1; j <= 4; j++) {
                this.mapReel.obtenirInfoCase(17+k,16+j).setEtat(EtatCase.feu);
            }
        }

        for (int k = 1; k <= 3; k++) {
            for (int j = 1; j <= 4; j++) {
                this.mapReel.obtenirInfoCase(15+j,20+k).setEtat(EtatCase.feu);
                this.mapReel.obtenirInfoCase(2+j,27+k).setEtat(EtatCase.feu);
            }
        }

        for (int k = 1; k <= 4; k++) {
            for (int j = 1; j <= 5; j++) {
                this.mapReel.obtenirInfoCase(1+j,13+k).setEtat(EtatCase.feu);
            }
        }
        
    }
    public MapReel getMapReel() {
    	return mapReel;
    }
    public MapRobot getMapRobot() {
    	return mapRob;
    }



    
    

    
}
