package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.*;

public class Main {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            // ToDo: Read text file - ImageCacheIO
            int cache_size_in_bytes = 0;
            int number_of_urls = 0;
            int lineNumber = 0;
            List<String> urlList = new ArrayList<>();
            File imageCache = new File(
                    "/Users/pagupta/Repos/TestI/FileIO/src/main/resources/ImageCacheInput");
            Scanner imageCacheSc = new Scanner(imageCache);

            while (imageCacheSc.hasNextLine()) {
                String currentlineContent = imageCacheSc.nextLine();
                if (lineNumber == 0) {
                    cache_size_in_bytes = Integer.valueOf(currentlineContent);
                } else if (lineNumber == 1) {
                    number_of_urls = Integer.valueOf(currentlineContent);
                } else {
                    urlList.add(currentlineContent);
                }
                lineNumber++;
            }

            // ToDo: Read text file - WordCountIO and write to output (0verwrite)
            lineNumber = 0;
            Map<String, Integer> wordCountMap = new HashMap<>();
            File wordCount = new File(
                    "/Users/pagupta/Repos/TestI/FileIO/src/main/resources/WordCountInput");
            Scanner wordCountSc = new Scanner(wordCount);

            while (wordCountSc.hasNextLine()) {
                String currentlineContent = wordCountSc.nextLine();
                String[] strArr = currentlineContent.split("\\s+");
                for(String word : strArr) {
                    wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                }
                lineNumber++;
            }

            File myObj = new File("/Users/pagupta/Repos/TestI/FileIO/src/main/resources/WordCountOutput");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            try {
                // Set append to false
                BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/pagupta/Repos/TestI/FileIO/src/main/resources/WordCountOutput", false));
                for(Map.Entry<String, Integer> me : wordCountMap.entrySet()) {
                    writer.write(me.getKey() + " " + me.getValue());
                    writer.newLine();
                }
                writer.close();
            } catch(IOException ex){
                ex.printStackTrace();
            }

            // ToDo: Read JsonFileList1.json json file and extract fields
            Object obj = parser.parse(new FileReader("/Users/pagupta/Repos/TestI/FileIO/src/main/resources/JsonFileList1"));
            // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
            JSONObject jsonObject = (JSONObject) obj;
            // A JSON array. JSONObject supports java.util.List interface.
            JSONArray companyList = (JSONArray) jsonObject.get("Company List");
            // An iterator over a collection. Iterator takes the place of Enumeration in the Java Collections Framework.
            // Iterators differ from enumerations in two ways:
            // 1. Iterators allow the caller to remove elements from the underlying collection during the iteration with well-defined semantics.
            // 2. Method names have been improved.
            Iterator<JSONObject> iterator = companyList.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }



            //ToDo; Read Json file to java object using jackson -> parse it to java object (Error in parsing the file to object)
            File jsonFile1 = new File(
                    "/Users/pagupta/Repos/TestI/FileIO/src/main/resources/JsonFile1");
            StringBuilder sbJson = new StringBuilder();
            Scanner scJson1 = new Scanner(jsonFile1);

            while (scJson1.hasNextLine())
//                System.out.println(sc1.nextLine());
                sbJson.append(scJson1.nextLine());
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = sbJson.toString();        //"{/"name":"John Doe","email":"john.doe@example.com"}";
            User user = objectMapper.readValue(jsonString, User.class);
            System.out.println("User:" + user.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}