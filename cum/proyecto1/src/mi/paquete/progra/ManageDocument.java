package mi.paquete.progra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageDocument {
	
	public List<String> getContent(String file) throws FileNotFoundException, IOException {
		List<String> list = new ArrayList<>();
		
		try {
			String content;
			FileReader fileRead = new FileReader(file);
			BufferedReader buffer = new BufferedReader(fileRead);

			while ((content = buffer.readLine()) != null) {
				list.add(content);
			}

			buffer.close();
			return list;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Frames.ta.setText(e.getMessage());
		}
		return null;
	}

	public void writeContent(String path, String content) throws FileNotFoundException, IOException {

		try {
			File file = new File(path);
			FileWriter write = new FileWriter(file, true);
			write.write(content + "\r\n");
			write.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			Frames.ta.setText(e.getMessage());

		}
	}
	
	public void deleteContent(String path) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(path));
		bw.write("");
		bw.close();
	}

}
