import java.io.Serializable;

public class AppContext implements Serializable{
    long noOfBytes;
    String fileName;
    int ind=0;
    public long getNoOfBytes() {
        return noOfBytes;
    }

    public void setNoOfBytes(long noOfBytes) {
        this.noOfBytes = noOfBytes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
