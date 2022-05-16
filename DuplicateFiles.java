import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DuplicateFiles {
	List<String> lis = new ArrayList<String>();
	
	// Returns a list of lists containing duplicate files (each sublist in the list contains the same duplicate content)
    public List<List<String>> findDuplicate(List<String> paths) {
		Map<String, List<String>> result = new HashMap<>();
    	for (String path:paths) {
    		String[] arr = path.split(" ");

    		String directory = arr[0];
    		
    		// Separate content from filename
    		String fileName = arr[1].substring(0, arr[1].indexOf("("));
    		String content = arr[1].substring(arr[1].indexOf("(")+1, arr[1].length()-1);
    		List<String> filepaths = result.getOrDefault(content, new ArrayList<>());
    		filepaths.add(directory + "\\" + fileName);
    		result.put(content, filepaths);
    	}
    	result.entrySet().removeIf(entry -> entry.getValue().size() <= 1);
    	
    	return new ArrayList<>(result.values());
    	
    }
    
    // Returns all the files, given an existing folder
    public List<String> listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                lis.add(fileEntry.getAbsolutePath());
            }
        }
		return lis;
    }
 
public static void main(String[] args)  
{ 
    DuplicateFiles df = new DuplicateFiles();
    
    File folder = new File(args[0]);
    List<String> file_names = df.listFilesForFolder(folder);
    List<String> file_name_content_pair_array = new ArrayList<String>();
    for (int i = 0; i < file_names.size(); i++) {
    	
		  Path path = Paths.get(file_names.get(i));
		  int index = -1;
		  String current_name = new String();
		  current_name = file_names.get(i);
		  
		  index = current_name.length()-1;
		  
		  // Identify the name of the current file in the directory 
		  while (current_name.charAt(index) != '\\') {
			  index = index - 1;
		  }
		  
		  String path_no_file = new String();
		  path_no_file = current_name.substring(0, index);
		  String name = new String();
		  name = current_name.substring(index+1, current_name.length());
		  
		  String content = new String();
		  try {
	      content = Files.readString(path);
		  
		  } catch (IOException e) { e.printStackTrace(); }
		 //System.out.println();
		  
		 // Format the path, file name, and its content
		 file_name_content_pair_array.add(path_no_file + " " + name + "(" + content + ")");
		 	
    }
    // Display content of all parsed files in the directory
    //System.out.println(file_name_content_pair_array);
    //System.out.println("\n");
    
    List<List<String>> result = df.findDuplicate(file_name_content_pair_array);
    if (result.size() == 0) {
    	System.out.println("No duplicate files were detected.");
    }
    else {
    	for (int i = 0; i < result.size(); i++) {
    		System.out.println((i+1) + ". " + result.get(i));
    	}
    }
} 
}