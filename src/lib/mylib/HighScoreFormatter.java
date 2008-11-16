/*
 * @version 0.0 26.01.2008
 * @author 	Tobse F
 */
package lib.mylib;

import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

public class HighScoreFormatter{

	private final String valueSeperator="_";
	private final String newLine=";";
	private int maximumElements=12;
	EasyCrypter easyCrypter;
	
	public HighScoreFormatter() {
		easyCrypter=new EasyCrypter();
	}
	
	public String shrincScoreInOneLine(String[][] highScore){
		String higscoreInOneLine=new String();
		for (String[] highScoreValue:highScore){
			higscoreInOneLine+=highScoreValue[0]+valueSeperator+highScoreValue[1]+newLine;
		}
		return higscoreInOneLine;
	} 
	
	public String[][] deShrincHighScoreFromOneLine(String higscoreInOneLine){
		String[] highScoreLines=higscoreInOneLine.split(newLine);
		List <String[]>highScoreInTable=new ArrayList<String[]>();	
		for (String scoreLine:highScoreLines){
			highScoreInTable.add(scoreLine.split(valueSeperator));
		}
		return highScoreInTable.toArray(new String[0][0]);
	} 
	
	public String tableToString(String[][] tabelle){
		String t=new String();
		for(String[] zeile:tabelle){
			for(String d:zeile){
				t+="["+d+"]";
			}
			t+="\n";
		}
		return t;
	}
	
	/** Gibt eine Tabelle vollständig in der Konsole aus
	 * @param tabelle
	 * @return
	 */
	public String printTable(String[][] tabelle){
		System.out.println(tableToString(tabelle));
		return tableToString(tabelle);
	}
	
	/** Sortiert ein zwidemensionales String Array. Die Richtung wird mit der zweiten Variable angegeben.<br>
	 * Um dies sicherzustellen kann die Methode {@link #addZeros(String[][])} benutzt werden
	 * @param daten
	 * @return
	 */
	public String[][] sortScore(String[][] score, boolean descending){
		score=addZeros(score);
		JTable tabelle = new JTable(score,new String[]{"Score","name"});
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabelle.getModel());
		List <RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
		if(descending){
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
		}
		else{
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		}
		sorter.setSortKeys(sortKeys); 
		tabelle.setRowSorter(sorter);
		String[][] sortedScore=new String[tabelle.getRowCount()][2];
		for(int i=0;i<tabelle.getRowCount();i++){
			sortedScore[i][0]=(String) tabelle.getValueAt(i,0);
			sortedScore[i][1]=(String) tabelle.getValueAt(i,1);
		}
		sortedScore=removeZeros(sortedScore);
		return sortedScore;
	}
	
	/** Sortiert ein zwidemensionales String Array nach der ersten Spalte absteigend.<br>
	 * Punkte müssen stehts die selbe Anzahl von Ziffern haben (0123;1344;0002 etc.)<br>
	 * Um dies sicherzustellen kann die Methode {@link #addZeros(String[][])} benutzt werden
	 * @param daten
	 * @return
	 */
	public String[][] sortScore(String[][] score){
		return sortScore(score,true);
	}
	
	public String[][] cryptScore(String[][] score){
		//copy to let a given score unchanged
		String[][] cryptedScore=new String[score.length][2];
		for(int i=0;i<score.length;i++){
			cryptedScore[i][0]=easyCrypter.enCrypt(score[i][0]);
			cryptedScore[i][1]=easyCrypter.enCrypt(score[i][1]);
		}
		return cryptedScore;
	}

	public String[][] decryptScore(String[][] score){
		if(score.length==0){
			System.out.println("asdjkasldjaslkd");
			return score;
		}
		
		String[][] cryptedScore=new String[score.length][2];
		for(int i=0;i<score.length;i++){
			cryptedScore[i][0]=easyCrypter.deCrypt(score[i][0]);
			//Names have to reconvertetd vom ASCII
			cryptedScore[i][1]=easyCrypter.deCrypt(score[i][1]);
		}
		return cryptedScore;
	}

	/** Füllt die Punkte in der ersten Stelle auf Stellen auf. </br>
	 * z.B. l=6; nr=123 ==> 000123
	 * @param score
	 * @return
	 */
	private String[][] addZeros(String[][] score){
		int maxLenght=0;
		for (String[] scoreValue:score){
			maxLenght=Math.max(maxLenght,scoreValue[0].length());
		}
		for (String[] sc:score){
			String z="";
			for(int i=maxLenght-sc[0].length();i>0;i--){
				z+="0";
			}
			sc[0]=z+sc[0];
		}
		return score;
	}
	
	/** Löscht eventuell anführende Nullen </br>
	 * z.B. 000123 ==> 123
	 * @param score
	 * @return
	 */
	private String[][] removeZeros(String[][] score){
		for (String[] sc:score){
			sc[0]=""+Integer.parseInt(sc[0]);
		}
		return score;
	}

	public String[][] readScore(String dataFromFile){
		String[] daten=dataFromFile.split(newLine);
		String[][] score=new String[daten.length][2];
		int i=0;
		for(String d:daten){
			score[i]=d.split(valueSeperator);
			i++;
		}
		return score;
	}
	
	/** Neue HighScore eintragen */
	public String[][] addScore(String[][] scoreTable,String name, int score){
		String[][] temp=new String[scoreTable.length+1][2];
		System.arraycopy(scoreTable,0,temp,0,scoreTable.length);
		temp[temp.length-1][0]=String.valueOf(score);
		//if(!name.isEmpty())
		temp[temp.length-1][1]=name;
		temp=sortScore(temp);
		//printTable(temp);
	
		//Maximal 10 Elemente speichern
		if(scoreTable.length>maximumElements){
			String[][] temp2=new String[maximumElements][2];
			System.arraycopy(temp,0,temp2,0,maximumElements);
			temp=temp2;
		}
		return temp;
	}

	public static void main(String[] args) {
		new HighScoreFormatter();
	}
}
