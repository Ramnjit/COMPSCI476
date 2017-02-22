package zombiewar;

import zombiewar.impl.CharacterFactory;
import zombiewar.impl.Child;
import zombiewar.impl.Soldier;
import zombiewar.impl.Teacher;
import zombiewar.impl.CollegeStudent;
import zombiewar.impl.CommonInfected;
import zombiewar.impl.Predator;
import zombiewar.impl.Tank;
import zombiewar.intf.ICharacter;
import zombiewar.intf.ICharacterFactory;
import zombiewar.intf.ISurvivor;
import zombiewar.intf.IZombie;

/**
 *
 * @author thaoc
 */
public class Main {
  
  private static final ICharacterFactory factory = CharacterFactory.instance;
  
  public static IZombie[] randomZombies() {
    int numZombies = (int) (Math.random() * 10);
    IZombie[] zombies = new IZombie[numZombies];
    for (int i = 0; i < zombies.length; i++) {
      int zombieType = (int) (Math.random() * 2);
      switch(zombieType){
        case 0: zombies[i] = (IZombie) factory.make("common"); break;
        case 1: zombies[i] = (IZombie) factory.make("tank"); break;
        case 2: zombies[i] = (IZombie) factory.make("predator"); break;
      }
    }
    return zombies;
  }

  public static ISurvivor[] randomSurvivors() {
    int numZombies = (int) (Math.random() * 20);
    ISurvivor[] survivors = new ISurvivor[numZombies];
    for (int i = 0; i < survivors.length; i++) {
      int type = (int) (Math.random() * 3);
      switch(type){
        case 0: survivors[i] = (ISurvivor) factory.make("soldier"); break;
        case 1: survivors[i] = (ISurvivor) factory.make("teacher"); break;
        case 2: survivors[i] = (ISurvivor) factory.make("student"); break;
        case 3: survivors[i] = (ISurvivor) factory.make("child"); break;
      }
    }
    return survivors;
  }

  public static boolean allDead(ICharacter[] characters){
    boolean allDead = true;
    for(int i=0; i<characters.length; i++){
      allDead &= !characters[i].isAlive();
    }
    return allDead;
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    IZombie[] zombies = randomZombies();
    ISurvivor[] survivors = randomSurvivors();
    
    int nteacher=0, nchild=0, nsoldier = 0, ncollege = 0;
		for(int i=0; i<survivors.length; i++){
			if (survivors[i] instanceof Teacher) nteacher++;
			if (survivors[i] instanceof Child) nchild++;
			if (survivors[i] instanceof Soldier) nsoldier++;
			if (survivors[i]instanceof CollegeStudent) ncollege++;
		}
		System.out.print("We have " + survivors.length + " survivors trying to make it to safety : (");
		System.out.println(ncollege +  " college student, "  + nchild + " children, " + nteacher + " teachers, " + nsoldier + " soldiers)");
		
		int ncommon=0, ntank=0, npredator = 0;
		for(int i=0; i<zombies.length; i++){
			if (zombies[i] instanceof Tank) ntank++;
			if (zombies[i] instanceof CommonInfected) ncommon++;
			if (zombies[i] instanceof Predator) npredator++;
		}
		
		System.out.print("But there are " + zombies.length + " zombies waiting for them : (");
		System.out.println(npredator + " predator, " + ncommon + " common infected, " + ntank + " tanks)");
		
		
		while (!allDead(survivors) && !allDead(zombies)) {
			for (int i = 0; i < survivors.length; i++) {
				for (int j = 0; j < zombies.length; j++) {
					if (survivors[i].isAlive() && zombies[j].isAlive()) {
						survivors[i].attack(zombies[j]);
						if (!zombies[j].isAlive()){
							System.out.println(survivors[i] + " killed " + zombies[j]);
						}
					}
				}
			}
			
			for (int i = 0; i < zombies.length; i++) {
				for (int j = 0; j < survivors.length; j++) {
					if (zombies[i].isAlive() && survivors[j].isAlive()) {
						zombies[i].attack(survivors[j]);
						if (!survivors[j].isAlive()){
							System.out.println(zombies[i] + " killed " + survivors[j]);
						}
					}
				}
			}
		}
		
		if (allDead(survivors)) {
			System.out.println("None of the survivors made it.");
		} else {
			int count = 0;
			for(int i=0; i<survivors.length; i++) {
				if (survivors[i].isAlive()) count++;
			}
			System.out.println("It seems " + count + " have made it to safety.");
		}
	}
}
