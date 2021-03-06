package controller.lucene.business;

public class Team {

    public Team() {
    }

    public Team(String filename, 
                 String description, 
                 String filePath) {
        this.filename = filename;     
        this.description = description;     
        this.filePath = filePath;       
    }
    
    private String filename;

    public String getFileName() {
        return this.filename;
    }

    public void setFileName(String filename) {
        this.filename = filename;
    }
    private String description;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    private String filePath;

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
