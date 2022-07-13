package it.polito.tdp.PremierLeague.model;

public class GiocatoreMigliore {

	private Player p;
	private double efficienza;
	public GiocatoreMigliore(Player p, double efficienza) {
		super();
		this.p = p;
		this.efficienza = efficienza;
	}
	public Player getP() {
		return p;
	}
	public void setP(Player p) {
		this.p = p;
	}
	public double getEfficienza() {
		return efficienza;
	}
	public void setEfficienza(double efficienza) {
		this.efficienza = efficienza;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(efficienza);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((p == null) ? 0 : p.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GiocatoreMigliore other = (GiocatoreMigliore) obj;
		if (Double.doubleToLongBits(efficienza) != Double.doubleToLongBits(other.efficienza))
			return false;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "GiocatoreMigliore [p=" + p + ", efficienza=" + efficienza + "]";
	}
	
	
}
