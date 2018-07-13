package imnet.ft.sid.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultatFT {

	
	private double scoreMax;
	private int nombre_hits;
	private long duree_recherche;
	private String requete;
	private Map<Float,Document> hits_with_score = new HashMap<Float,Document>();
	
	
	
	
	
	public double getScoreMax() {
		return scoreMax;
	}
	public int getNombre_hits() {
		return nombre_hits;
	}
	public long getDuree_recherche() {
		return duree_recherche;
	}
	public String getRequete() {
		return requete;
	}
	
	
	
	public ResultatFT setScoreMax(double scoreMax) {
		this.scoreMax = scoreMax;
		return this;
	}
	public ResultatFT setNombre_hits(int nombre_hits) {
		this.nombre_hits = nombre_hits;
		return this;
	}
	public ResultatFT setDuree_recherche(long duree_recherche) {
		this.duree_recherche = duree_recherche;
		return this;
	}
	public ResultatFT setRequete(String requete) {
		this.requete = requete;
		return this;
	}
	public Map<Float, Document> getHits_with_score() {
		return hits_with_score;
	}
	public void setHits_with_score(Map<Float, Document> hits_with_score) {
		this.hits_with_score = hits_with_score;
	}
	@Override
	public String toString() {
		return "ResultatFT [scoreMax=" + scoreMax + ", nombre_hits=" + nombre_hits + ", duree_recherche="
				+ duree_recherche + ", requete=" + requete + ", hits_with_score=" + hits_with_score + "]";
	}
	
	
	

	
	
	
	
	
}
