package local_database;

public class Player {
	
	private String name;
	private int highscore;
	private double meanScore;
	private long gamesPlayed;
	private double meanScore_5;
	private int[] s2_4 = new int[4];
	private int s5;
	private long bulletsHit;
	private long bulletsShot;
	private double bulletAccuracy;
	private byte carLevel;

	public Player() {
		
	}
	
	public void update(int score, long bulletsHit, long bulletsShot, byte carLevel) {
		this.s5 = score;
		
		if (score > this.highscore) {
			this.highscore = s5;
		}
		
		gamesPlayed++;
		this.meanScore += (s5 / (gamesPlayed));
		
		this.bulletsHit += bulletsHit;
		this.bulletsShot += bulletsShot;
		this.bulletAccuracy = Math.round(bulletsHit / bulletsShot * 10000) / 100; 
		
		this.carLevel = carLevel;
		
		
		
		this.meanScore_5 = (sumArray(s2_4) + s5) / 5;
		
	}
	
	public void initialize (String name, int highscore, double meanScore, long gamesPlayed, int s1, int s2, int s3, int s4, int s5, long bulletsHit, long bulletsShot, byte carLevel) {
		this.name = name;
		this.highscore = highscore;
		this.meanScore = meanScore;
		this.gamesPlayed = gamesPlayed;
		this.s2_4[0] = s2;
		this.s2_4[1] = s3;
		this.s2_4[2] = s4;
		this.s2_4[3] = s5;
		this.meanScore_5 = (s1 + sumArray(s2_4)) / 5;
		this.bulletsHit = bulletsHit;
		this.bulletsShot = bulletsShot;
		this.carLevel = carLevel;
	}
	
	private int sumArray(int[] arr) {
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		
		return sum;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getHighscore() {
		return this.highscore;
	}
	
	public double getMeanScore() {
		return this.meanScore;
	}
	
	public long getGamesPlayed() {
		return this.gamesPlayed;
	}
	
	public int getS1() {
		return this.s2_4[0];
	}
	
	public int getS2() {
		return this.s2_4[1];
	}
	
	public int getS3() {
		return this.s2_4[2];
	}
	
	public int getS4() {
		return this.s2_4[3];
	}
	
	public int getS5() {
		return this.s5;
	}
	
	public double getMeanScore_5() {
		return this.meanScore_5;
	}
	
	public long getBulletsHit() {
		return this.bulletsHit;
	}
	
	public long getBulletsShot() {
		return this.bulletsShot;
	}
	
	public byte getCarLevel() {
		return this.carLevel;
	}
	
	public double getBulletAccuracy() {
		return this.bulletAccuracy;
	}
	
	public String toString() {
		return name + " has a highscore of " + highscore;
	}
	
}
