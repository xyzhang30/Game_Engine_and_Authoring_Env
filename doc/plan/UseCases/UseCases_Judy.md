# Use Cases - Judy

```java

// Given a JSON file, configure game
GameObjectRecord parse(String filePath) throws InvalidFileException {

    // parsing file
    Object obj = new JSONParser().parse(new FileReader(filePath));

    // typecasting obj to JSONObject 
    JSONObject jo = (JSONObject) obj;

    // getting single entries
    String firstName = (String) jo.get("");
    long age = (long) jo.get("");
    
    // getting single nested entries 
    Map address = ((Map)jo.get(""));

    // iterating map
    Iterator<Map.Entry> itr1 = address.entrySet().iterator();
    while (itr1.hasNext()) {
        Map.Entry pair = itr1.next();
        System.out.println(pair.getKey() + " : " + pair.getValue());
    }

    // getting list of nested entries 
    JSONArray ja = (JSONArray) jo.get("");

    // iterating phoneNumbers 
    Iterator itr2 = ja.iterator();

    while (itr2.hasNext()) {
        itr1 = ((Map) itr2.next()).entrySet().iterator();
        while (itr1.hasNext()) {
            Map.Entry pair = itr1.next();
            System.out.println(pair.getKey() + " : " + pair.getValue());
      }
    }

}

// 

```