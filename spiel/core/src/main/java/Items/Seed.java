package Items;

import Pflanzen.Pflanze;

public abstract class Seed extends Item{
    private Pflanze pflanze;
    public Seed(Pflanze pflanze){
        this.pflanze = pflanze;
    }
    @Override
    public Pflanze getPflanze(){
        return pflanze;
    }
    @Override
    public abstract void loadAnimation();
}
