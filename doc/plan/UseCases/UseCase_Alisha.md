# Alisha Use Cases

### User runs a game with invalid color rgb values in the data file/json

```java
GameParser parser = new GameParser(filePath); //filepath is the file corresponding to the game user chose to run
parser.parseViewData();

//parseViewData calls css file writer
private void generateStyleSheet(){
  
  try (PrintWriter writer = new PrintWriter(new FileWriter(defaultFolderPath + gameName))) {
    for (int i = 0; i < collidables.length(); i++) {
      JSONObject collidable = collidables.getJSONObject(i);
      String id = collidable.getString("gameobject_id");
      JSONArray colorArray = collidable.getJSONArray("color");

      int red = validateColorComponent(colorArray.getInt(0));
      int green = validateColorComponent(colorArray.getInt(1));
      int blue = validateColorComponent(colorArray.getInt(2));

      String rgb = red + "," + green + "," + blue;      writer.println("#collidable_" + id + " {");
      writer.println("    -fx-background-color: rgb(" + rgb + ");"); 
      writer.println("}");
    }
  } catch (IOException e){
    System.out.println("Error generating CSS file: " + e.getMessage());
  }
  }

private int validateColorComponent(int value) {
  if (value < 0) {
    return 0; // Set to 0 if value is negative
  } else if (value > 255) {
    return 255; // Set to 255 if value is greater than 255
  } else {
    return value; // Return original value if within valid range
  }
}

```