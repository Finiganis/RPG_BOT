package mainBot.utils;

public class JSONLoad {
	private static String f = "Save.txt";
/*
	public static List<Character> load() throws IOException{
		try{
			FileReader fr = new FileReader(new File(f));
			char[] line = new char[140];
			fr.read(line);
			fr.close();
			JSONArray ja = new JSONArray();
			JSONObject jo =  new JSONObject();
			for(int i = 0; line[i] != ']'; i++){
				if(line[i] == '{'){
					jo = new JSONObject();				
				}
				if(line[i] == '"'){
					i++;
					String key = "";
					while(line[i] != '"'){
						key += line[i];
						i++;
					}
					String value = "";
					if(key == "name"){
						i += 3;
						while(line[i] != '"'){
							value += line[i];
							i++;
						}
						i++;
					} else {
						i += 2;
						while(line[i] != ','){
							value += line[i];
							i++;
						}
					}
					jo.put(key, value);
				}
				if(line[i] == '}'){
					ja.put(jo);
				}
				if(line[i] == ']'){
					return JArrayToCharactersList(ja);
				}
			}
		} catch (JSONException e){
			return null;
		}
		return null;
	}

	private static List<Character> JArrayToCharactersList(JSONArray ja) throws JSONException {
		ArrayList<Character> lc = new ArrayList<Character>();
		for(Object o : ja){
			JSONObject jo = (JSONObject) o;
			lc.add(new Character(jo.getString("name"), jo.getString("HP"), jo.getString("mana")));
		}
		return lc;
	}*/
}
