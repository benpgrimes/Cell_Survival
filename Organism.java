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

	// minimum for splitting
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

	private Food[] closestFood = new Food[3];

	private Organism[] closestOrganism = new Organism[3];

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
	 * [0]; energy level required to activate 
	 * [1]; new growth limit 
	 * [2]; new growth inclination 
	 * [3]; new curiosity 
	 * [4]; new maternal minimum 
	 * [5]; new maternal inclination 
	 * [6]; new active level 
	 * [7]; new temperament
	 * [8-14] preservation in same order
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
			int[] values = new int[5];
			int temp;
			int total = 0;
			first = this.choice == -1;
			result = this.choice;
			int multiplier = 0;
			if (this.diam < growthLim)// !< is not a command so i changed it to >=
			{
				multiplier = growthInc;
			}
			temp = (int) (Math.atan(this.energy) / this.energy);// THIS HAD TO BE MADE AN INT
			temp = temp * multiplier;
			total += temp;
			values[ctr] = temp;
			ctr++;
			multiplier = 0;

			if (this.choice != 2) {
				multiplier++;
			}
			if (this.prevchoice != 2) {
				multiplier++;
			}
			temp = multiplier * multiplier * curious;
			total += temp;
			values[ctr] = temp;
			ctr++;
			if (this.diam < growthLim)// !< is not a command so i changed it to >=
			{
				multiplier = 0;
			}else{
				multiplier = maternalInc;
			temp = (int) (Math.atan(this.energy - maternalM) + 1.5) * multiplier;// I had to make this an int
			total += temp;
			values[ctr] = temp;
			ctr++;

			int combined = -(this.energy * this.energy) + total + 5;
			total += combined;
			int mindist = 999999999;// I DECREASED THIS NUMBER BECAUSE IT WAS TO LARGE TO BE AN INT
			if (closestFood[0] != null) {
				for (int i = 0; i < 3; i++) {
					Food tempFood = closestFood[i];
					int tempx = (int) Math.abs(tempFood.x - this.x);// these were also made ints
					int tempy = (int) Math.abs(tempFood.y - this.y);// ''
					int tempdist = (int) Math.sqrt((tempx * tempx) + (tempy * tempy));// ''
					if (tempdist < mindist) {
						mindist = tempdist;
					}
				}
				temp = ((this.energy * 5) - mindist) * act;
				if (temp > combined) {
					temp = combined;
				}
			} else {
				temp = act;
				total += values[1] + 5;
				values[1] = values[1] * 2 + 5;
				if (temp > combined) {
					temp = combined;
				}

			}
			values[ctr] = temp;
			ctr++;
			temp = combined - temp;
			values[ctr] = temp;
			ctr = 0;

			Random rand = new Random();
			int choiceNum = rand.nextInt(total) + 1;
			while (ctr < 5 && choiceNum >= 0) {
				choiceNum -= values[ctr];
				ctr++;
			}
			if (ctr <= 5 && choiceNum <= 0) {
				this.choice = ctr;
			} else {
				this.choice = -1;
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
		double distanceToFood0 = -1;
		double distanceToFood1 = -1;
		double distanceToFood2 = -1;

		if (this.closestFood[0] == null) {
			Food tempFood = closestFood[0];
			int tempx = (int) Math.abs(tempFood.x - this.x);// these were also made ints
			int tempy = (int) Math.abs(tempFood.y - this.y);// ''
			int tempdist = (int) Math.sqrt((tempx * tempx) + (tempy * tempy));// ''

		}
		return 0;
	}
		
		/********************************************
	 * moves towards the closest food
	 *
	 *
	 *********************************************/
	public void grow() {
		
		
	}
	public void move() {

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
		myBuffer.setColor(color);
		myBuffer.fillOval((int) x, (int) y, (int) diam, (int) diam);
	}

	/********************************************
	 * updates the closest food coordinates
	 *
	 *
	 *********************************************/
	public void examine(Organism[] x, Food[] y) {
		for (int i = 0; i < 3; i++) {
			this.closestOrganism[i] = x[i];
			this.closestFood[i] = y[i];
		}
	}

	/********************************************
	 * checks to see if the organism collects the food
	 *
	 *
	 *********************************************/
	public boolean collision(double foodX, double foodY, int foodEnergy) {
		if (Math.abs(foodX - x) <= diam && Math.abs(foodY - y) <= diam) {
			energy += foodEnergy;
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
