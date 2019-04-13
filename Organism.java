import java.awt.*;
import java.util.*;
import java.lang.Math;

/******************************************
 * Class for the moving organisms
 *
 *
 *
 ******************************************/
public class Organism extends Cell {
	// the point at which the cells likelihood of choosing growth begins to drop off
	// x
	private int growthLimit;

	// how likely it is to use size
	// tan^-1(x)/x based on size
	private int growthInclination;

	// How often it uses examine
	// x^2 based on last use
	private int curiosity;

	// minimum energy for splitting
	// x
	private int maternalMin;

	// likelihood of splitting
	// tan^-1(x-y) + 1.5 based on energy
	private int maternalInclination;

	// likelihood of movement vs idle
	// complicated
	private int active;

	// likelihood of moving towards food
	private int temperament;

	// the x and y coordinates of the closest food (set after examine)
	// private double closestFoodX;
	// private double closestFoodY;

	private Cell[] closestFood = new Food[3];

	private Cell[] closestOrganism = new Organism[3];

	// How these values change when the organism is at critical energy levels
	// [0]; energy level required to activate
	// [1]; new growth limit
	// [2]; new growth inclination
	// [3]; new curiosity
	// [4]; new maternal min
	// [5]; new maternal inclination
	// [6]; new active level
	// [7]; new temperament
	private int preservation[] = new int[8];

	// stores the next choice the cell will execute
	private int choice = -1;
	private int prevchoice = -1;
	private int prevdirection = -1;
	private int movectr = 0;

	/*************************
	 * Constructor for Organism
	 * 
	 * @param x:
	 *            The starting x value on the coordinate plane
	 * @param y:
	 *            The starting y value on the coordinate plane
	 * @param diam:
	 *            starting size
	 * @param energy:
	 *            starting energy
	 * @param color:
	 *            color of organism
	 * @param tendencies:
	 *            tendencies of organism
	 */
	public Organism(double x, double y, double diam, int energy, Color color, int[] tendencies) {
		this.x = x;
		this.y = y;
		this.diam = diam;
		this.color = color;
		this.energy = energy;
		this.growthLimit = tendencies[0];
		this.growthInclination = tendencies[1];
		this.curiosity = tendencies[2];
		this.maternalMin = tendencies[3];
		this.maternalInclination = tendencies[4];
		this.active = tendencies[5];
		this.temperament = tendencies[6];
		for (int i = 0; i < 8; i++) {
			this.preservation[i] = tendencies[7 + i];

		}
	}

	/********************************************
	 * Description: Puts all the tendencies into a single array
	 * 
	 * @param None
	 * @return integer array of all tendencies (15 values) in the order shown: 
	 * [0]; new growth limit 
	 * [1]; new growth inclination 
	 * [2]; new curiosity 
	 * [3]; new maternal minimum 
	 * [4]; new maternal inclination 
	 * [5]; new active level 
	 * [6]; new temperament
	 * [7-14] preservation in same order
	 *******************************************/
	public int[] getTendencies() {
		int[] tendencies = new int[15];
		tendencies[0] = this.growthLimit;
		tendencies[1] = this.growthInclination;
		tendencies[2] = this.curiosity;
		tendencies[3] = this.maternalMin;
		tendencies[4] = this.maternalInclination;
		tendencies[5] = this.active;
		tendencies[6] = this.temperament;
		for (int i = 0; i < 8; i++) {
			tendencies[7 + i] = this.preservation[i];
		}
		return tendencies;
	}

	/*******************************************
	 * returns the choice made as well as makes its next choice.
	 * 
	 * @param None
	 * @return returns an integer based on the choice. Follows this pattern: 
	 * 1: Grow
	 * 2: examine 
	 * 3: split 
	 * 4: move 
	 * 5: idle 
	 * -1: choice failure
	 ***********************************************/
	public int choose() {
		boolean first;
		int result = -1;
		int growthLim;
		int growthInc;
		int curious;
		int maternalM;
		int maternalInc;
		int act;
		if (this.energy < preservation[0]) {
			growthLim = preservation[1];
			growthInc = preservation[2];
			curious = preservation[3];
			maternalM = preservation[4];
			maternalInc = preservation[5];
			act = preservation[6];
		} else {
			growthLim = this.growthLimit;
			growthInc = this.growthInclination;
			curious = this.curiosity;
			maternalM = this.maternalMin;
			maternalInc = this.maternalInclination;
			act = this.active;

		}
		do {
			int ctr = 0;
			long[] values = new long[5];
			long temp;
			long total = 0;
			first = this.choice == -1;
			result = this.choice;
			int multiplier = 0;
			if (this.diam < growthLim)// !< is not a command so i changed it to >=
			{
				multiplier = growthInc;
			}
			//temp = (int) Math.abs((Math.atan(this.energy) / this.energy)*100*multiplier);// THIS HAD TO BE MADE AN INT
			temp = (int) (growthLim - this.diam)/5 + (this.energy/100) * multiplier;
			if(temp < 0) {
				temp = 0;
			}
			total += temp;
			values[ctr] = temp;
			ctr++;
			multiplier = 0;

			if (this.choice != 2 && this.prevchoice != 2) {
				multiplier++;
			}
			if(this.prevdirection == -1) {
				multiplier = multiplier + 5;
			}
			if(this.closestFood[0] == null) {
				multiplier = multiplier + 3;
			}
			if(this.closestFood[2] == null) {
				multiplier++;
			}
			temp = Math.abs(multiplier * multiplier * curious);
			total += temp;
			values[ctr] = temp;
			ctr++;
			if (this.diam < growthLim)// !< is not a command so i changed it to >=
			{
				multiplier = 0;
			}else{
				multiplier = maternalInc;
			}
			//temp = (int) ((Math.atan(this.energy - maternalM)*100) + 1.5)* multiplier;// I had to make this an int
			temp = (this.energy - maternalM) * multiplier;
			if(temp < 0) {
				temp = 0;
			}
			total += temp;
			values[ctr] = temp;
			ctr++;

			long combined = total;
			int mindist = 0x7fffffff;// I DECREASED THIS NUMBER BECAUSE IT WAS TO LARGE TO BE AN INT
			if (this.closestOrganism[2] != null) {
				total += combined;
				for (int i = 0; i < 3; i++) {
					Cell tempOrganism = closestOrganism[i];
					int tempx = (int) Math.abs(tempOrganism.x - this.x);// these were also made ints
					int tempy = (int) Math.abs(tempOrganism.y - this.y);// ''
					int tempdist = (int) Math.sqrt((tempx * tempx) + (tempy * tempy));// ''
					if (tempdist < mindist) {
						mindist = tempdist;
					}
				}
				temp = ((this.energy * 5) - mindist) * act;
				if (temp > combined) {
					temp = combined;
				}
				values[ctr] = temp;
				ctr++;
				temp = combined - temp;
				values[ctr] = temp;
				ctr = 0;
			} else {
				temp = act;
				total += values[1] + 5;
				values[1] = (values[1] * 2) + 5;
				values[ctr] = 0;
				ctr++;
				values[ctr] = 0;
			}
			Random rand = new Random();
			if(total == 0) {
				choice = 5;
			}else {
				long choiceNum = (rand.nextLong()%total) + 1;
				ctr = 0;
				while (ctr < 5 && choiceNum > 0) {
					choiceNum -= values[ctr];
					ctr++;
				}
				this.choice = ctr;
			}
		} while (first);
		this.prevchoice = result;
		return result;
	}

	/********************************************
	 * returns the direction that the cell wishes to move
	 *
	 *
	 *********************************************/
	public int chooseDirection() {	
		int result = -1;
		if(this.prevdirection == -1 || this.movectr > 15) {
			double[] distanceToFood = new double[3];
			distanceToFood[0] = -1;
			distanceToFood[1] = -1;
			distanceToFood[2] = -1;
			int i = 0;
			int j = 0;
			while(i < 3) {
				if(this.closestFood[i] != null) {
					Cell tempFood = closestFood[i];
					double tempx = (tempFood.x - this.x);// these were also made ints
					double tempy = (tempFood.y - this.y);// ''
					double tempdist = Math.sqrt((tempx * tempx) + (tempy * tempy));// ''
					while( j < 3 && this.closestOrganism[j] != null) {
						Cell tempOrganism = closestOrganism[j];
						double temp2x = (tempOrganism.x - this.x);
						double temp2y = (tempOrganism.x - this.x);
						if(((tempx < 0 && temp2x < 0) && (tempy < 0 && temp2y < 0)) || 
								((tempx >= 0 && temp2x >= 0) && (tempy >= 0 && temp2y >= 0))){
							temp2x = tempOrganism.x - tempFood.x;
							temp2y = tempOrganism.y - tempFood.y;
							double tempdist2 = Math.sqrt((temp2x*temp2x) + (temp2y*temp2y));
							tempdist = tempdist + ((tempdist-tempdist2)* (this.temperament/ 10.5));
						}
						if(distanceToFood[i] == -1 || distanceToFood[i] > tempdist) {
							distanceToFood[i] = tempdist;
						}
						j++;
					}
					distanceToFood[i] = tempdist;
				}
				i++;
			}
			if(distanceToFood[0] >= 0) {
				double mindist = distanceToFood[0];
				j = 0;
				for(i = 1; i < 3; i++) {
					if(distanceToFood[i] < mindist && distanceToFood[i] >= 0) {
						mindist = distanceToFood[i];
						j = i;
					}
				}
			
				double tempx = (closestFood[j].x - this.x);// these were also made ints
				double tempy = (closestFood[j].y - this.y);
				double tan = Math.atan2(tempy, tempx);
				if(tan < 0) {
					tan = tan+(Math.PI*2);
					//tan = Math.PI-tan;
				}
				result = (int) ((tan*180)/Math.PI);
			}
			movectr = 0;
		}else {
			result = this.prevdirection;
			this.movectr++;
		}
		this.prevdirection = result;
		return result;
	}
		
	public void grow() {
		double r = this.diam / 2;
		double area = 3.14159*(r*r);
		area += 25;			//CHECK
		double newdiam = area/3.14159;
		newdiam = Math.sqrt(newdiam) * 2;
		newdiam += 1;
		this.diam = (int) newdiam;
		reduceEnergy(10);		//CHECK
	}
	/********************************************
	 * updates the closest food coordinates
	 *
	 *
	 *********************************************/
	public void examine(LinkedList Organisms, LinkedList foodList, int numOrganisms, int numFood) {
	    int mindist = 0x7fffffff;
	    int index1 = -1;
	    int index2 = -1;
	    for(int i = 0; i < 3; i++) {
	    	Cell closeFood = null;
		    for (int j = 0; j < numFood; j++) {
		    	if(j != index1 && j != index2) {
					Cell tempFood = (Cell) foodList.get(j);
					int tempx = (int) (tempFood.x - this.x);// these were also made ints
					int tempy = (int) (tempFood.y - this.y);// ''
					int tempdist = (int) Math.sqrt((tempx * tempx) + (tempy * tempy));// ''
					if (tempdist < mindist) {
						mindist = tempdist;
						closeFood = tempFood;
						
					}
		    	}
		    }
		    
	    	this.closestFood[i] = new Food(closeFood.getX(), closeFood.getY(), closeFood.getDiam(), closeFood.getEnergy());
	    	mindist = 0x7fffffff;
	    }
	    for(int i = 0; i < 3; i++) {
		    Cell closeOrganism = null;
			for (int j = 0; j < numOrganisms; j++) {
			    if(j != index1 || j != index2) {
					Cell tempOrganism = (Cell) Organisms.get(j);
					int tempx = (int) (tempOrganism.x - this.x);// these were also made ints
					int tempy = (int) (tempOrganism.y - this.y);// ''
					int tempdist = (int) Math.sqrt((tempx * tempx) + (tempy * tempy));// ''
					if (tempdist < mindist) {
						mindist = tempdist;
						closeOrganism = tempOrganism;
						
					}
			    }
			}
		    this.closestOrganism[i] = new Organism(closeOrganism.getX(), closeOrganism.getY(), closeOrganism.getDiam(), 
		    		closeOrganism.getEnergy(), closeOrganism.getColor(), closeOrganism.garbageTendencies());
		    mindist = 999999;
	    }
	    this.prevdirection = -1;
		reduceEnergy(2);		//CHECK
	}
	
	public Organism split(){
		int[] newTendencies = getTendencies();
		Random rand = new Random();
		int randNum;
		int pos;
		int multiplier;
		for(int i = 0; i < 15; i++){
			randNum = rand.nextInt(15);
			pos = rand.nextInt(2);
			multiplier = 1;
			if(pos == 0){
				multiplier = -1;
			}
			if(randNum >= 14){
				newTendencies[i] += 3*multiplier;
			}else if(randNum>= 12){
				newTendencies[i] += 2*multiplier;
			}else if(randNum >= 7){
				newTendencies[i] += multiplier;
			}
		}
		this.diam = this.diam/2;
		this.energy = this.energy/2;
		Color newColor = new Color(Math.abs((newTendencies[2]*5)%255), Math.abs((newTendencies[5]*5)%255), Math.abs((newTendencies[6]*5)%255));
		Organism child = new Organism(this.x-1, this.y-1, this.diam, this.energy, newColor, newTendencies);
		reduceEnergy(4);		//CHECK
		return child;
	}
	
	/********************************************
	 * moves towards the closest food
	 *
	 *
	 *********************************************/
	public void move() {
		int angle = chooseDirection();
		if(angle != -1) {
		
			int count = 0;
			int xmul = 1;
			int ymul = 1;
			int flip = 1;
			while(angle > 90) {
				flip = flip*-1;
				count++;
				if(count == 1 || count == 3) {
					xmul = xmul*-1;
				}else {
					ymul = ymul*-1;
				}
				angle -= 90;
			}
			/*while(angle < 0) {
				flip = flip*-1;
				count++;
				if(count == 2 || count == 4) {
					xmul = xmul*-1;
				}else {
					ymul = ymul*-1;
				}
				angle += 90;
			}*/
			if(flip == -1) {
				if(angle >= 0 && angle <= 15) {
					this.y += 10 * ymul;
				}else if(angle > 15 && angle < 37) {
					this.y += 8 * ymul;
					this.x += 5 * xmul;
				}else if(angle >= 37 && angle <= 53) {
					this.y += 7 * ymul;
					this.x += 7 * xmul;
				}else if(angle > 53 && angle < 75) {
					this.y += 5 * ymul;
					this.x += 8 * xmul;
				}else if(angle >= 75 && angle <= 90) {
					this.x += 10 * xmul;
				}
			}else {
				if(angle >= 0 && angle <= 15) {
					this.x += 10 * xmul;
				}else if(angle > 15 && angle < 37) {
					this.x += 8 * xmul;
					this.y += 5 * ymul;
				}else if(angle >= 37 && angle <= 53) {
					this.x += 7 * xmul;
					this.y += 7 * ymul;
				}else if(angle > 53 && angle < 75) {
					this.x += 5 * xmul;
					this.y += 8 * ymul;
				}else if(angle >= 75 && angle <= 90) {
					this.y += 10 * ymul;
				}
			}
			reduceEnergy(2);
		
			/*if((angle >= 0 && angle <= 15) || (angle <= 360 && angle >= 345)) {
				this.x += 10;
			}else if(angle > 15 && angle < 37) {
				this.x += 8;
				this.y += 5;
			}else if(angle >= 37 && angle <= 53) {
				this.x += 7;
				this.y += 7;
			}else if(angle > 53 && angle < 75) {
				this.x += 5;
				this.y += 8;
			}else if(angle >= 75 && angle <= 105) {
				this.y += 10;
			}else if(angle > 105 && angle < 127) {
				this.x -= 5;
				this.y += 8;
			}else if(angle >= 127 && angle <= 143) {
				this.x -= 7;
				this.y += 7;
			}else if(angle > 143 && angle < 165) {
				this.x -= 8;
				this.y += 5;
			}else if(angle >= 165 && angle <= 195) {
				this.x -= 10;
			}else if(angle > 195 && angle < 217) {
				this.x -= 8;
				this.y -= 5;
			}else if(angle >= 217 && angle <= 233) {
				this.x -= 7;
				this.y -= 7;
			}else if(angle > 233 && angle < 255) {
				this.x -= 5;
				this.y -= 8;
			}else if(angle >= 255 && angle <= 285) {
				this.y -= 10;
			}else if(angle > 285 && angle < 307) {
				this.x += 5;
				this.y -= 8;
			}else if(angle >= 307 && angle <= 323) {
				this.x += 7;
				this.y -= 7;
			}else if(angle > 323 && angle < 345) {
				this.x += 8;
				this.y -= 5;
			}
			*/
		}else {
			idle();
		}
		/*
		if(closestFood[0] != null) {
			if (energy > 0) {
		
			// Checks X
			if (closestFood[0].x > (x + 5)) {
				x += 5;
				energy -= 1;
			} else if (closestFood[0].x < (x - 5)) {
				x -= 5;
				energy -= 1;
			}
		}
			if (energy > 0) {
				// Checks y
				if (closestFood[0].y > (y + 5)) {
					y += 5;
					energy -= 1;
				} else if (closestFood[0].y < (y - 5)) {
					y -= 5;
					energy -= 1;
				}
			}
		}
		*/
	}
	
	public void idle(){
		reduceEnergy(1);
	}
	
	public void reduceEnergy(int x){
		this.energy -= x;
	}
	/*
	 * THIS SHOULD BE DONE IN THE PANNEL public void die(){ Food food = new
	 * Food(this.x, this.y, this.diam, int(this.diam));
	 * 
	 * }
	 */

	/********************************************
	 * draws the organism on the pannel
	 *
	 *
	 *********************************************/

	public void draw(Graphics myBuffer) {
		myBuffer.setColor(color.BLACK);
		myBuffer.fillOval((int) x - 5, (int) y - 5, (int) diam + 10, (int) diam + 10);
		myBuffer.setColor(color);
		myBuffer.fillOval((int) x, (int) y, (int) diam, (int) diam);
	}

	/********************************************
	 * checks to see if the organism collects the food
	 *
	 *
	 *********************************************/
	public boolean collision(double foodX, double foodY, int foodEnergy) {
		if (Math.abs(foodX - x) < (diam/2) && Math.abs(foodY - y) < (diam/2)) {
			energy += foodEnergy;
			this.prevdirection = -1;
			for(int i = 0; i < 3; i++) {
				Cell tempfood = this.closestFood[i];
				if(tempfood != null) {
					if(tempfood.x == foodX && tempfood.y == foodY) {
						this.closestFood[i] = null;
					}
				}
			}
			return true;
		}
		return false;
	}

	public int getGrowthLimit() {
		return growthLimit;
	}

	public void setGrowthLimit(int growthLimit) {
		this.growthLimit = growthLimit;
	}

	public int getGrowthInclination() {
		return growthInclination;
	}

	public void setGrowthInclination(int growthInclination) {
		this.growthInclination = growthInclination;
	}

	public int getCuriosity() {
		return curiosity;
	}

	public void setCuriosity(int curiosity) {
		this.curiosity = curiosity;
	}

	public int getMaternalMin() {
		return maternalMin;
	}

	public void setMaternalMin(int maternalMin) {
		this.maternalMin = maternalMin;
	}

	public int getMaternalInclination() {
		return maternalInclination;
	}

	public void setMaternalInclination(int maternalInclination) {
		this.maternalInclination = maternalInclination;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getTemperament() {
		return temperament;
	}

	public void setTemperament(int temperament) {
		this.temperament = temperament;
	}

	public int[] getPreservation() {
		return preservation;
	}

	public void setPreservation(int[] preservation) {
		this.preservation = preservation;
	}

	public int getChoice() {
		return choice;
	}

	public void setChoice(int choice) {
		this.choice = choice;
	}
}
