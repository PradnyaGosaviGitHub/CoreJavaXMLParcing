import java.io.*;

public class FileProcessorSerialization {
    public static void main(String[] args) throws IOException, InterruptedException {
        AppContext ctx = readSavedState();
        
        File[] files = new File("input").listFiles();
		System.out.println("In FileMonitorThread");
		String fileName = null;
		for(File file1:files) {
			if(file1 == null) {
				//throw new InputFileIsNotPresentException();
				 fileName = file1.getName();
				break;
			}
        
//        String fileName = "A.txt";
        long pos =0;
        if(ctx != null) {
            fileName = ctx.getFileName();
            pos = ctx.getNoOfBytes();
        }else {
            ctx= new AppContext();
            ctx.setFileName(fileName);
            ctx.setNoOfBytes(pos);
        }
        RandomAccessFile file = new RandomAccessFile(file1,"r");
        file.seek(pos);
        FileReader reader = new FileReader(fileName);
        BufferedReader br = new BufferedReader(reader);
        String line = null;
        while ((line = file.readLine())!=null){
            System.out.println(" "+line);
            Thread.sleep(200);
            long noOfBytesRead = file.getFilePointer();
            ctx.setNoOfBytes(noOfBytesRead);
            saveState(ctx);
//            if (line == null){
//                System.out.println("End of the file");
//            }
        }
        reader.close();
        br.close();
		}
    }

    private static void saveState(AppContext ctx) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("appcontext.ser"));
            oos.writeObject(ctx);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static AppContext readSavedState(){
        //Check if appcontext is saved
        //Step 1 ObjectInputStream class
        try {
            ObjectInputStream ois =  new ObjectInputStream(new FileInputStream("appcontext.ser"));
            AppContext ctx = (AppContext) ois.readObject();
            ois.close();
            if (ctx == null){
                System.out.println("End of the file");
            }
            return ctx;
        } catch (FileNotFoundException fnf) {
            System.out.println("No data to save so far");
            fnf.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
