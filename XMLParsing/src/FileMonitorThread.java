import java.io.File;

public class FileMonitorThread extends Thread{
	static FileValidation filevalid = new FileValidation("Format.xml");
	
	@Override
	public void run(){
		while(true) {
//			File[] files = new File("C://Users//Pradnya//input").listFiles();
			File[] files = new File("input").listFiles();
			System.out.println("In FileMonitorThread");
			for(File file:files) {
				if(file == null) {
					//throw new InputFileIsNotPresentException();
					break;
				}
//				System.out.println("File: "+file);
			boolean isValid = isValid(file);
			boolean isInTime = isInTime(file);
			boolean isDuplicate = isDuplicate(file);
			if(isValid && isInTime && isDuplicate) {
			    System.out.println("Going to MyFileProcessThread!!!");
			    MyFileProcessorThread myFileProcessThread = new MyFileProcessorThread(file) ;
			    myFileProcessThread.start();
			}
//			else {
////				file.delete();
//			}
			}
			try {
				Thread.sleep(30000);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isDuplicate(File file) {
		return filevalid.isDuplicate(file);
	}

	private boolean isInTime(File file) {
		return filevalid.isInTime(file);
	}

	private boolean isValid(File file) {
		return filevalid.isValid(file);
	}
	
}
