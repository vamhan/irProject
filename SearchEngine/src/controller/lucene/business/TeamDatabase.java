package controller.lucene.business;

import java.util.ArrayList;
import java.io.*;

public class TeamDatabase {
	private static ArrayList<File> queue;
	private static Team[] TEAMS;

//	public static void addTeam(String folderPath){
//		addTeamFiles(new File(folderPath));
//		for (File f : queue) {
//		      FileReader fr = null;
//		      try {
//		    	  fr = new FileReader(f);
//			      Team team = new Team(f.getName(),f,f.getPath());
//			      System.out.println("Added: " + f);
//		      } catch (Exception e) {
//		        System.out.println("Could not add: " + f);
//		      } finally {
//		        fr.close();
//		      }
//		    }
//	}
	
	public static void addTeamFiles(File file) {
		queue = new ArrayList<File>();
		
	    if (!file.exists()) {
	      System.out.println(file + " does not exist.");
	    }
	    if (file.isDirectory()) {
	      for (File f : file.listFiles()) {
	    	  queue.add(f);
	      }
	    } else {
	      String filename = file.getName().toLowerCase();
	      //===================================================
	      // Only index text files
	      //===================================================
	      if (filename.endsWith(".htm") || filename.endsWith(".html") || 
	              filename.endsWith(".xml") || filename.endsWith(".txt")) {
	    	  queue.add(file);
	      } else {
	        System.out.println("Skipped " + filename);
	      }
	    }
	  }
	
	   public static Team getTeam(String fileName) {
	        for(Team team : TEAMS) {
	            if (fileName.equals(team.getFileName())) {
	                return team;
	            }
	        }
	        return null;
	    }
}
